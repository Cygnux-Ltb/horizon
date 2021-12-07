package io.horizon.ctp.adaptor;

import static io.mercury.common.thread.SleepSupport.sleep;
import static io.mercury.common.thread.Threads.startNewThread;

import java.io.IOException;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.set.MutableSet;
import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcInputOrderActionField;
import ctp.thostapi.CThostFtdcInputOrderField;
import io.horizon.ctp.adaptor.converter.FromFtdcDepthMarketData;
import io.horizon.ctp.adaptor.converter.FromFtdcOrder;
import io.horizon.ctp.adaptor.converter.FromFtdcTrade;
import io.horizon.ctp.adaptor.converter.ToCThostFtdcInputOrder;
import io.horizon.ctp.adaptor.converter.ToCThostFtdcInputOrderAction;
import io.horizon.ctp.exception.OrderRefNotFoundException;
import io.horizon.ctp.gateway.CtpGateway;
import io.horizon.ctp.gateway.rsp.FtdcDepthMarketData;
import io.horizon.ctp.gateway.rsp.FtdcInputOrder;
import io.horizon.ctp.gateway.rsp.FtdcInputOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcMdConnect;
import io.horizon.ctp.gateway.rsp.FtdcOrder;
import io.horizon.ctp.gateway.rsp.FtdcOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcTrade;
import io.horizon.ctp.gateway.rsp.FtdcTraderConnect;
import io.horizon.market.data.FastMarketDataBridge;
import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.handler.MarketDataHandler;
import io.horizon.market.handler.MarketDataMulticaster;
import io.horizon.market.instrument.Instrument;
import io.horizon.trader.account.Account;
import io.horizon.trader.adaptor.AbstractAdaptor;
import io.horizon.trader.adaptor.AdaptorEvent;
import io.horizon.trader.adaptor.AdaptorEvent.AdaptorStatus;
import io.horizon.trader.handler.AdaptorEventHandler;
import io.horizon.trader.handler.InboundScheduler;
import io.horizon.trader.handler.OrderReportHandler;
import io.horizon.trader.order.ChildOrder;
import io.horizon.trader.order.OrderReport;
import io.mercury.common.collections.MutableSets;
import io.mercury.common.concurrent.queue.jct.JctSingleConsumerQueue;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.param.Params;
import io.mercury.common.util.ArrayUtil;
import io.mercury.serialization.json.JsonWrapper;

public class CtpAdaptor extends AbstractAdaptor<BasicMarketData> {

	private static final Logger log = CommonLoggerFactory.getLogger(CtpAdaptor.class);

	// 转换行情
	private final FromFtdcDepthMarketData fromFtdcDepthMarketData = new FromFtdcDepthMarketData();

	// 转换报单回报
	private final FromFtdcOrder fromFtdcOrder = new FromFtdcOrder();

	// 转换成交回报
	private final FromFtdcTrade fromFtdcTrade = new FromFtdcTrade();

	// FtdcConfig
	private final CtpConfig ftdcConfig;
	// FtdcGateway
	private final CtpGateway ftdcGateway;

	// TODO 两个INT类型可以合并
	private volatile int frontId;
	private volatile int sessionId;

	private volatile boolean isMdAvailable;
	private volatile boolean isTraderAvailable;

	// 订单转换为FTDC报单录入请求
	private final ToCThostFtdcInputOrder toCThostFtdcInputOrder;

	// 订单转换为FTDC报单操作请求
	private final ToCThostFtdcInputOrderAction toCThostFtdcInputOrderAction;

	private final MarketDataMulticaster<FtdcDepthMarketData, FastMarketDataBridge> multicaster = new MarketDataMulticaster<>(
			getAdaptorId(), FastMarketDataBridge.FACTORY, (marketData, sequence, ftdcMarketData) -> {
				marketData.setInstrumentCode(ftdcMarketData.getInstrumentID());
				var multiplier = marketData.getInstrument().getSymbol().getPriceMultiplier();
				var fastMarketData = marketData.getFastMarketData();
				fastMarketData.setLastPrice(multiplier.toLong(ftdcMarketData.getLastPrice()));

				marketData.updated();
			});

	public CtpAdaptor(@Nonnull Account account, @Nonnull Params<CtpAdaptorParamKey> params,
			@Nonnull MarketDataHandler<BasicMarketData> marketDataHandler,
			@Nonnull OrderReportHandler orderReportHandler, @Nonnull AdaptorEventHandler adaptorEventHandler) {
		super("ctp", account, marketDataHandler, orderReportHandler, adaptorEventHandler);
		// 创建配置信息
		this.ftdcConfig = createFtdcConfig(params);
		// 创建Gateway
		this.ftdcGateway = createFtdcGateway();
		this.toCThostFtdcInputOrder = new ToCThostFtdcInputOrder(params);
		this.toCThostFtdcInputOrderAction = new ToCThostFtdcInputOrderAction(params);
	}

	public CtpAdaptor(@Nonnull final Account account, @Nonnull final Params<CtpAdaptorParamKey> params,
			@Nonnull final InboundScheduler<BasicMarketData> scheduler) {
		this(account, params, scheduler, scheduler, scheduler);
	}

	/**
	 * 
	 * @param params
	 * @return
	 */
	private CtpConfig createFtdcConfig(Params<CtpAdaptorParamKey> params) {
		return new CtpConfig()
				// 交易服务器地址
				.setTraderAddr(params.getString(CtpAdaptorParamKey.TraderAddr))
				// 行情服务器地址
				.setMdAddr(params.getString(CtpAdaptorParamKey.MdAddr))
				// 应用ID
				.setAppId(params.getString(CtpAdaptorParamKey.AppId))
				// 经纪商ID
				.setBrokerId(params.getString(CtpAdaptorParamKey.BrokerId))
				// 投资者ID
				.setInvestorId(params.getString(CtpAdaptorParamKey.InvestorId))
				// 账号ID
				.setAccountId(params.getString(CtpAdaptorParamKey.AccountId))
				// 用户ID
				.setUserId(params.getString(CtpAdaptorParamKey.UserId))
				// 密码
				.setPassword(params.getString(CtpAdaptorParamKey.Password))
				// 认证码
				.setAuthCode(params.getString(CtpAdaptorParamKey.AuthCode))
				// 客户端IP地址
				.setIpAddr(params.getString(CtpAdaptorParamKey.IpAddr))
				// 客户端MAC地址
				.setMacAddr(params.getString(CtpAdaptorParamKey.MacAddr))
				// 结算货币
				.setCurrencyId(params.getString(CtpAdaptorParamKey.CurrencyId));
	}

	private String gatewayId;

	/**
	 * 
	 * @param ftdcConfig
	 * @return
	 */
	private CtpGateway createFtdcGateway() {
		this.gatewayId = "ftdc-" + ftdcConfig.getBrokerId() + "-" + ftdcConfig.getUserId();
		log.info("Create ftdc gateway, gatewayId -> {}", gatewayId);
		final String queueName = gatewayId + "-queue";
		return new CtpGateway(gatewayId, ftdcConfig,
				// 创建队列缓冲区
				JctSingleConsumerQueue.multiProducer(queueName).setCapacity(64).buildWithProcessor(ftdcRspMsg -> {
					switch (ftdcRspMsg.getType()) {
					case FtdcMdConnect:
						FtdcMdConnect mdConnect = ftdcRspMsg.getFtdcMdConnect();
						this.isMdAvailable = mdConnect.isAvailable();
						log.info("Swap Queue processed FtdcMdConnect, isMdAvailable==[{}]", isMdAvailable);
						final AdaptorEvent mdEvent;
						if (isMdAvailable)
							mdEvent = new AdaptorEvent(getAdaptorId(), AdaptorStatus.MdEnable);
						else
							mdEvent = new AdaptorEvent(getAdaptorId(), AdaptorStatus.MdDisable);
						adaptorEventHandler.onAdaptorEvent(mdEvent);
						break;
					case FtdcTraderConnect:
						FtdcTraderConnect traderConnect = ftdcRspMsg.getFtdcTraderConnect();
						this.isTraderAvailable = traderConnect.isAvailable();
						this.frontId = traderConnect.getFrontID();
						this.sessionId = traderConnect.getSessionID();
						log.info(
								"Swap Queue processed FtdcTraderConnect, "
										+ "isTraderAvailable==[{}], frontId==[{}], sessionId==[{}]",
								isTraderAvailable, frontId, sessionId);
						final AdaptorEvent traderEvent;
						if (isTraderAvailable) {
							traderEvent = new AdaptorEvent(getAdaptorId(), AdaptorStatus.TraderEnable);
						} else {
							traderEvent = new AdaptorEvent(getAdaptorId(), AdaptorStatus.TraderDisable);
						}
						adaptorEventHandler.onAdaptorEvent(traderEvent);
						break;
					case FtdcDepthMarketData:
						// 行情处理
						// TODO
						multicaster.publish(ftdcRspMsg.getFtdcDepthMarketData());
						BasicMarketData marketData = fromFtdcDepthMarketData.apply(ftdcRspMsg.getFtdcDepthMarketData());
						marketDataHandler.onMarketData(marketData);
						break;
					case FtdcOrder:
						// 报单回报处理
						FtdcOrder ftdcOrder = ftdcRspMsg.getFtdcOrder();
						log.info("Buffer Queue in FtdcOrder, InstrumentID==[{}], InvestorID==[{}], "
								+ "OrderRef==[{}], LimitPrice==[{}], VolumeTotalOriginal==[{}], OrderStatus==[{}]",
								ftdcOrder.getInstrumentID(), ftdcOrder.getInvestorID(), ftdcOrder.getOrderRef(),
								ftdcOrder.getLimitPrice(), ftdcOrder.getVolumeTotalOriginal(),
								ftdcOrder.getOrderStatus());
						OrderReport report0 = fromFtdcOrder.apply(ftdcOrder);
						orderReportHandler.onOrderReport(report0);
						break;
					case FtdcTrade:
						// 成交回报处理
						FtdcTrade ftdcTrade = ftdcRspMsg.getFtdcTrade();
						log.info("Buffer Queue in FtdcTrade, InstrumentID==[{}], InvestorID==[{}], OrderRef==[{}]",
								ftdcTrade.getInstrumentID(), ftdcTrade.getInvestorID(), ftdcTrade.getOrderRef());
						OrderReport report1 = fromFtdcTrade.apply(ftdcTrade);
						orderReportHandler.onOrderReport(report1);
						break;
					case FtdcInputOrder:
						// TODO 报单错误处理
						FtdcInputOrder ftdcInputOrder = ftdcRspMsg.getFtdcInputOrder();
						log.info("Buffer Queue in [FtdcInputOrder] -> {}", JsonWrapper.toJson(ftdcInputOrder));
						break;
					case FtdcInputOrderAction:
						// TODO 撤单错误处理1
						FtdcInputOrderAction ftdcInputOrderAction = ftdcRspMsg.getFtdcInputOrderAction();
						log.info("Buffer Queue in [FtdcInputOrderAction] -> {}",
								JsonWrapper.toJson(ftdcInputOrderAction));
						break;
					case FtdcOrderAction:
						// TODO 撤单错误处理2
						FtdcOrderAction ftdcOrderAction = ftdcRspMsg.getFtdcOrderAction();
						log.info("Buffer Queue in [FtdcOrderAction] -> {}", JsonWrapper.toJson(ftdcOrderAction));
						break;
					default:
						log.warn("Buffer Queue unprocessed [FtdcRspMsg] -> {}", JsonWrapper.toJson(ftdcRspMsg));
						break;
					}
				}));
	}

	@Override
	protected boolean startup0() {
		try {
			ftdcGateway.bootstrap();
			log.info("gateway -> {} bootstrap finish", gatewayId);
			return true;
		} catch (Exception e) {
			log.error("Gateway exception -> {}", e.getMessage(), e);
			return false;
		}
	}

	// 存储已订阅合约
	private MutableSet<String> subscribedInstrumentCodes = MutableSets.newUnifiedSet();

	/**
	 * 订阅行情实现
	 */
	@Override
	public boolean subscribeMarketData(@Nonnull Instrument[] instruments) {
		try {
			if (isMdAvailable) {
				if (ArrayUtil.isNullOrEmpty(instruments)) {
					// 输入的Instrument数组为空或null
					log.warn("Input instruments is null or empty, use subscribed instruments");
					if (subscribedInstrumentCodes.isEmpty()) {
						// 已记录的订阅Instrument为空
						log.warn("Subscribed instruments is empty");
						return false;
					} else {
						// 使用已经订阅过的Instrument
						String[] instrumentCodes = new String[subscribedInstrumentCodes.size()];
						log.info("Add subscribe instrument code -> Count==[{}]", subscribedInstrumentCodes.size());
						subscribedInstrumentCodes.toArray(instrumentCodes);
						ftdcGateway.SubscribeMarketData(instrumentCodes);
						return true;
					}
				} else {
					String[] instrumentCodes = new String[instruments.length];
					for (int i = 0; i < instruments.length; i++) {
						instrumentCodes[i] = instruments[i].getInstrumentCode();
						log.info("Add subscribe instrument -> instruementCode==[{}]", instrumentCodes[i]);
						subscribedInstrumentCodes.add(instrumentCodes[i]);
					}
					ftdcGateway.SubscribeMarketData(instrumentCodes);
					return true;
				}
			} else {
				log.warn("gateway -> {} market not available", gatewayId);
				return false;
			}
		} catch (Exception e) {
			log.error("ftdcGateway#SubscribeMarketData exception -> {}", e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean newOredr(ChildOrder order) {
		try {
			CThostFtdcInputOrderField ftdcInputOrder = toCThostFtdcInputOrder.apply(order);
			String orderRef = Integer.toString(OrderRefGenerator.next(order.getStrategyId()));
			// 设置OrderRef
			ftdcInputOrder.setOrderRef(orderRef);
			OrderRefKeeper.put(orderRef, order.getOrdSysId());
			ftdcGateway.ReqOrderInsert(ftdcInputOrder);
			return true;
		} catch (Exception e) {
			log.error("ctp gateway -> {} new order func [ReqOrderInsert] exception -> {}", gatewayId, e.getMessage(),
					e);
			return false;
		}
	}

	@Override
	public boolean cancelOrder(ChildOrder order) {
		try {
			CThostFtdcInputOrderActionField inputOrderAction = toCThostFtdcInputOrderAction.apply(order);
			String orderRef = OrderRefKeeper.getOrderRef(order.getOrdSysId());
			inputOrderAction.setOrderRef(orderRef);
			inputOrderAction.setOrderActionRef(OrderRefGenerator.next(order.getStrategyId()));
			ftdcGateway.ReqOrderAction(inputOrderAction);
			return true;
		} catch (OrderRefNotFoundException e) {
			log.error(e.getMessage(), e);
			return false;
		} catch (Exception e) {
			log.error("ftdc gateway -> {} cancel order func [ReqOrderAction] exception -> {}", gatewayId,
					e.getMessage(), e);
			return false;
		}
	}

	// 查询互斥锁, 保证同时只进行一次查询, 满足监管要求
	private final Object mutex = new Object();

	// 查询间隔
	private final long queryInterval = 1150L;

	@Override
	public boolean queryOrder(@Nonnull Instrument instrument) {
		try {
			if (isTraderAvailable) {
				startNewThread("QueryOrder-SubThread", () -> {
					synchronized (mutex) {
						log.info("FtdcAdaptor :: Ready to sent ReqQryInvestorPosition, Waiting...");
						sleep(queryInterval);
						ftdcGateway.ReqQryOrder(instrument.getExchangeCode());
						log.info("FtdcAdaptor :: Has been sent ReqQryInvestorPosition");
					}
				});
				return true;
			} else {

				return false;
			}
		} catch (Exception e) {
			log.error("ftdcGateway.ReqQryOrder exception -> {}", e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean queryPositions(@Nonnull Instrument instrument) {
		try {
			if (isTraderAvailable) {
				startNewThread("QueryPositions-SubThread", () -> {
					synchronized (mutex) {
						log.info("FtdcAdaptor :: Ready to sent ReqQryInvestorPosition, Waiting...");
						sleep(queryInterval);
						ftdcGateway.ReqQryInvestorPosition(instrument.getExchangeCode(),
								instrument.getInstrumentCode());
						log.info("FtdcAdaptor :: Has been sent ReqQryInvestorPosition");
					}
				});
				return true;
			} else {

				return false;
			}
		} catch (Exception e) {
			log.error("ftdcGateway.ReqQryInvestorPosition exception -> {}", e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean queryBalance() {
		try {
			if (isTraderAvailable) {
				startNewThread("QueryBalance-SubThread", () -> {
					synchronized (mutex) {
						log.info("FtdcAdaptor :: Ready to sent ReqQryTradingAccount, Waiting...");
						sleep(queryInterval);
						ftdcGateway.ReqQryTradingAccount();
						log.info("FtdcAdaptor :: Has been sent ReqQryTradingAccount");
					}
				});
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("ftdcGateway.ReqQryTradingAccount exception -> {}", e.getMessage(), e);
			return false;
		}
	}

	@Override
	public void close() throws IOException {
		try {
			ftdcGateway.close();
		} catch (Exception e) {
			log.error("ftdcGateway.close() catch Exception, message -> {}", e.getMessage(), e);
			throw new IOException(e);
		}
	}

}