package io.horizon.ftdc.adaptor;

import static io.mercury.common.thread.Threads.sleep;
import static io.mercury.common.thread.Threads.startNewThread;

import java.io.IOException;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.set.MutableSet;
import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcInputOrderActionField;
import ctp.thostapi.CThostFtdcInputOrderField;
import io.horizon.ftdc.adaptor.converter.FromFtdcDepthMarketData;
import io.horizon.ftdc.adaptor.converter.FromFtdcOrder;
import io.horizon.ftdc.adaptor.converter.FromFtdcTrade;
import io.horizon.ftdc.adaptor.converter.ToCThostFtdcInputOrder;
import io.horizon.ftdc.adaptor.converter.ToCThostFtdcInputOrderAction;
import io.horizon.ftdc.exception.OrderRefNotFoundException;
import io.horizon.ftdc.gateway.FtdcConfig;
import io.horizon.ftdc.gateway.FtdcGateway;
import io.horizon.ftdc.gateway.msg.rsp.FtdcInputOrder;
import io.horizon.ftdc.gateway.msg.rsp.FtdcInputOrderAction;
import io.horizon.ftdc.gateway.msg.rsp.FtdcMdConnect;
import io.horizon.ftdc.gateway.msg.rsp.FtdcOrder;
import io.horizon.ftdc.gateway.msg.rsp.FtdcOrderAction;
import io.horizon.ftdc.gateway.msg.rsp.FtdcTrade;
import io.horizon.ftdc.gateway.msg.rsp.FtdcTraderConnect;
import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.handler.MarketDataHandler;
import io.horizon.market.instrument.Instrument;
import io.horizon.transaction.account.Account;
import io.horizon.transaction.adaptor.AbstractAdaptor;
import io.horizon.transaction.adaptor.AdaptorEvent;
import io.horizon.transaction.adaptor.AdaptorEvent.AdaptorStatus;
import io.horizon.transaction.event.InboundScheduler;
import io.horizon.transaction.event.handler.AdaptorEventHandler;
import io.horizon.transaction.event.handler.OrderReportHandler;
import io.horizon.transaction.order.ChildOrder;
import io.horizon.transaction.order.OrderReport;
import io.mercury.common.collections.MutableSets;
import io.mercury.common.concurrent.queue.jct.JctSingleConsumerQueue;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.param.Params;
import io.mercury.common.util.ArrayUtil;
import io.mercury.serialization.json.JsonWrapper;

public class FtdcAdaptor extends AbstractAdaptor<BasicMarketData> {

	private static final Logger log = CommonLoggerFactory.getLogger(FtdcAdaptor.class);

	// 转换行情
	private final FromFtdcDepthMarketData fromFtdcDepthMarketData = new FromFtdcDepthMarketData();

	// 转换报单回报
	private final FromFtdcOrder fromFtdcOrder = new FromFtdcOrder();

	// 转换成交回报
	private final FromFtdcTrade fromFtdcTrade = new FromFtdcTrade();

	// FtdcConfig
	private final FtdcConfig ftdcConfig;
	// FtdcGateway
	private final FtdcGateway ftdcGateway;

	// TODO 两个INT类型可以合并
	private volatile int frontId;
	private volatile int sessionId;

	private volatile boolean isMdAvailable;
	private volatile boolean isTraderAvailable;

	// 订单转换为FTDC报单录入请求
	private final ToCThostFtdcInputOrder toCThostFtdcInputOrder;

	// 订单转换为FTDC报单操作请求
	private final ToCThostFtdcInputOrderAction toCThostFtdcInputOrderAction;

	public FtdcAdaptor(@Nonnull Account account, @Nonnull final Params<FtdcAdaptorParamKey> params,
			@Nonnull MarketDataHandler<BasicMarketData> marketDataHandler,
			@Nonnull OrderReportHandler orderReportHandler, @Nonnull AdaptorEventHandler adaptorEventHandler) {
		super("FTDC", marketDataHandler, orderReportHandler, adaptorEventHandler, account);
		// 创建配置信息
		this.ftdcConfig = createFtdcConfig(params);
		// 创建Gateway
		this.ftdcGateway = createFtdcGateway();
		this.toCThostFtdcInputOrder = new ToCThostFtdcInputOrder(params);
		this.toCThostFtdcInputOrderAction = new ToCThostFtdcInputOrderAction(params);
	}

	public FtdcAdaptor(@Nonnull final Account account, @Nonnull final Params<FtdcAdaptorParamKey> params,
			@Nonnull final InboundScheduler<BasicMarketData> scheduler) {
		this(account, params, scheduler, scheduler, scheduler);
	}

	/**
	 * 
	 * @param params
	 * @return
	 */
	private FtdcConfig createFtdcConfig(Params<FtdcAdaptorParamKey> params) {
		return new FtdcConfig()
				// 交易服务器地址
				.setTraderAddr(params.getString(FtdcAdaptorParamKey.TraderAddr))
				// 行情服务器地址
				.setMdAddr(params.getString(FtdcAdaptorParamKey.MdAddr))
				// 应用ID
				.setAppId(params.getString(FtdcAdaptorParamKey.AppId))
				// 经纪商ID
				.setBrokerId(params.getString(FtdcAdaptorParamKey.BrokerId))
				// 投资者ID
				.setInvestorId(params.getString(FtdcAdaptorParamKey.InvestorId))
				// 账号ID
				.setAccountId(params.getString(FtdcAdaptorParamKey.AccountId))
				// 用户ID
				.setUserId(params.getString(FtdcAdaptorParamKey.UserId))
				// 密码
				.setPassword(params.getString(FtdcAdaptorParamKey.Password))
				// 认证码
				.setAuthCode(params.getString(FtdcAdaptorParamKey.AuthCode))
				// 客户端IP地址
				.setIpAddr(params.getString(FtdcAdaptorParamKey.IpAddr))
				// 客户端MAC地址
				.setMacAddr(params.getString(FtdcAdaptorParamKey.MacAddr))
				// 结算货币
				.setCurrencyId(params.getString(FtdcAdaptorParamKey.CurrencyId));
	}

	/**
	 * 
	 * @param ftdcConfig
	 * @return
	 */
	private FtdcGateway createFtdcGateway() {
		final String gatewayId = "ftdc-" + ftdcConfig.getBrokerId() + "-" + ftdcConfig.getUserId();
		log.info("Create ftdc gateway, gatewayId -> {}", gatewayId);
		final String queueName = gatewayId + "-queue";
		return new FtdcGateway(gatewayId, ftdcConfig,
				// 创建队列缓冲区
				JctSingleConsumerQueue.multiProducer(queueName).setCapacity(64).buildWithProcessor(ftdcRspMsg -> {
					switch (ftdcRspMsg.getRspType()) {
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
		// JctMpscQueue.autoStartQueue(gatewayId + "-Buffer", 64,
		// WaitingStrategy.SpinWaiting, ));
	}

	@Override
	protected boolean startup0() {
		try {
			ftdcGateway.bootstrap();
			log.info("");
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
	public boolean subscribeMarketData(@Nonnull Instrument... instruments) {
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
				return false;
			}
		} catch (Exception e) {
			log.error("ftdcGateway#SubscribeMarketData exception -> {}", e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean newOredr(Account account, ChildOrder order) {
		try {
			CThostFtdcInputOrderField ftdcInputOrder = toCThostFtdcInputOrder.apply(order);
			String orderRef = Integer.toString(OrderRefGenerator.next(order.getStrategyId()));
			// 设置OrderRef
			ftdcInputOrder.setOrderRef(orderRef);
			OrderRefKeeper.put(orderRef, order.getOrdSysId());
			ftdcGateway.ReqOrderInsert(ftdcInputOrder);
			return true;
		} catch (Exception e) {
			log.error("#############################################################");
			log.error("ftdcGateway.ReqOrderInsert exception -> {}", e.getMessage(), e);
			log.error("#############################################################");
			return false;
		}
	}

	@Override
	public boolean cancelOrder(Account account, ChildOrder order) {
		try {
			CThostFtdcInputOrderActionField ftdcInputOrderAction = toCThostFtdcInputOrderAction.apply(order);
			String orderRef = OrderRefKeeper.getOrderRef(order.getOrdSysId());
			ftdcInputOrderAction.setOrderRef(orderRef);
			ftdcInputOrderAction.setOrderActionRef(OrderRefGenerator.next(order.getStrategyId()));
			ftdcGateway.ReqOrderAction(ftdcInputOrderAction);
			return true;
		} catch (OrderRefNotFoundException e) {
			log.error(e.getMessage(), e);
			return false;
		} catch (Exception e) {
			log.error("ftdcGateway.ReqOrderAction exception -> {}", e.getMessage(), e);
			return false;
		}
	}

	private final Object mutex = new Object();

	@Override
	public boolean queryOrder(Account account, @Nonnull Instrument instrument) {
		try {
			if (isTraderAvailable) {
				startNewThread("QueryOrder-SubThread", () -> {
					synchronized (mutex) {
						log.info("FtdcAdaptor :: Ready to sent ReqQryInvestorPosition, Waiting...");
						sleep(1500);
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
	public boolean queryPositions(Account account, @Nonnull Instrument instrument) {
		try {
			if (isTraderAvailable) {
				startNewThread("QueryPositions-SubThread", () -> {
					synchronized (mutex) {
						log.info("FtdcAdaptor :: Ready to sent ReqQryInvestorPosition, Waiting...");
						sleep(1500);
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
	public boolean queryBalance(final Account account) {
		try {
			if (isTraderAvailable) {
				startNewThread("QueryBalance-SubThread", () -> {
					synchronized (mutex) {
						log.info("FtdcAdaptor :: Ready to sent ReqQryTradingAccount, Waiting...");
						sleep(1250);
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
