package io.gemini.ftdc.adaptor;

import static io.mercury.common.concurrent.queue.MpscArrayBlockingQueue.autoStartQueue;
import static io.mercury.common.thread.Threads.sleep;
import static io.mercury.common.thread.Threads.startNewThread;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcInputOrderActionField;
import ctp.thostapi.CThostFtdcInputOrderField;
import io.gemini.definition.account.Account;
import io.gemini.definition.adaptor.AdaptorBaseImpl;
import io.gemini.definition.adaptor.AdaptorEvent;
import io.gemini.definition.adaptor.AdaptorEvent.AdaptorStatus;
import io.gemini.definition.adaptor.Command;
import io.gemini.definition.event.InboundScheduler;
import io.gemini.definition.market.data.impl.BasicMarketData;
import io.gemini.definition.market.instrument.Instrument;
import io.gemini.definition.order.actual.ChildOrder;
import io.gemini.definition.order.structure.OrdReport;
import io.gemini.ftdc.adaptor.converter.FromFtdcDepthMarketData;
import io.gemini.ftdc.adaptor.converter.FromFtdcOrder;
import io.gemini.ftdc.adaptor.converter.FromFtdcTrade;
import io.gemini.ftdc.adaptor.converter.ToFtdcInputOrder;
import io.gemini.ftdc.adaptor.converter.ToFtdcInputOrderAction;
import io.gemini.ftdc.adaptor.exception.OrderRefNotFoundException;
import io.gemini.ftdc.gateway.FtdcConfig;
import io.gemini.ftdc.gateway.FtdcGateway;
import io.gemini.ftdc.gateway.bean.FtdcInputOrder;
import io.gemini.ftdc.gateway.bean.FtdcInputOrderAction;
import io.gemini.ftdc.gateway.bean.FtdcMdConnect;
import io.gemini.ftdc.gateway.bean.FtdcOrder;
import io.gemini.ftdc.gateway.bean.FtdcOrderAction;
import io.gemini.ftdc.gateway.bean.FtdcTrade;
import io.gemini.ftdc.gateway.bean.FtdcTraderConnect;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.param.Params;
import io.mercury.serialization.json.JsonUtil;

public class FtdcAdaptor extends AdaptorBaseImpl<BasicMarketData> {

	private static final Logger log = CommonLoggerFactory.getLogger(FtdcAdaptor.class);

	/**
	 * 转换行情
	 */
	private final FromFtdcDepthMarketData fromFtdcDepthMarketData = new FromFtdcDepthMarketData();

	/**
	 * 转换报单回报
	 */
	private final FromFtdcOrder fromFtdcOrder = new FromFtdcOrder();

	/**
	 * 转换成交回报
	 */
	private final FromFtdcTrade fromFtdcTrade = new FromFtdcTrade();

	/**
	 * FTDC Gateway
	 */
	private final FtdcGateway ftdcGateway;

	// TODO 两个INT类型可以合并
	private volatile int frontId;
	private volatile int sessionId;

	private volatile boolean isMdAvailable;
	private volatile boolean isTraderAvailable;

	public FtdcAdaptor(int adaptorId, @Nonnull Account account, @Nonnull Params<FtdcAdaptorParamKey> params,
			@Nonnull InboundScheduler<BasicMarketData> scheduler) {
		super(adaptorId, "FtdcAdaptor-Broker[" + account.brokerName() + "]-InvestorId[" + account.investorId() + "]",
				scheduler, account);
		// 创建配置信息
		FtdcConfig ftdcConfig = createFtdcConfig(params);
		// 创建Gateway
		this.ftdcGateway = createFtdcGateway(ftdcConfig);
		this.toFtdcInputOrder = new ToFtdcInputOrder();
		this.toFtdcInputOrderAction = new ToFtdcInputOrderAction();
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
	private FtdcGateway createFtdcGateway(final FtdcConfig config) {
		String gatewayId = "FTDC-" + config.getBrokerId() + "-" + config.getUserId();
		log.info("Create Ftdc Gateway, gatewayId -> {}", gatewayId);
		return new FtdcGateway(gatewayId, config,
				// 创建队列缓冲区
				autoStartQueue(gatewayId + "-Buffer", 64, ftdcRspMsg -> {
					switch (ftdcRspMsg.getRspType()) {
					case FtdcMdConnect:
						FtdcMdConnect mdConnect = ftdcRspMsg.getFtdcMdConnect();
						this.isMdAvailable = mdConnect.isAvailable();
						log.info("Swap Queue processed FtdcMdConnect, isMdAvailable==[{}]", isMdAvailable);
						final AdaptorEvent mdEvent;
						if (mdConnect.isAvailable()) {
							mdEvent = new AdaptorEvent(adaptorId(), AdaptorStatus.MdEnable);
						} else {
							mdEvent = new AdaptorEvent(adaptorId(), AdaptorStatus.MdDisable);
						}
						scheduler.onAdaptorEvent(mdEvent);
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
						if (traderConnect.isAvailable()) {
							traderEvent = new AdaptorEvent(adaptorId(), AdaptorStatus.TraderEnable);
						} else {
							traderEvent = new AdaptorEvent(adaptorId(), AdaptorStatus.TraderDisable);
						}
						scheduler.onAdaptorEvent(traderEvent);
						break;
					case FtdcDepthMarketData:
						// 行情处理
						BasicMarketData marketData = fromFtdcDepthMarketData.apply(ftdcRspMsg.getFtdcDepthMarketData());
						scheduler.onMarketData(marketData);
						break;
					case FtdcOrder:
						// 报单回报处理
						FtdcOrder ftdcOrder = ftdcRspMsg.getFtdcOrder();
						log.info("Buffer Queue in FtdcOrder, InstrumentID==[{}], InvestorID==[{}], "
								+ "OrderRef==[{}], LimitPrice==[{}], VolumeTotalOriginal==[{}], OrderStatus==[{}]",
								ftdcOrder.getInstrumentID(), ftdcOrder.getInvestorID(), ftdcOrder.getOrderRef(),
								ftdcOrder.getLimitPrice(), ftdcOrder.getVolumeTotalOriginal(),
								ftdcOrder.getOrderStatus());
						OrdReport ordReport = fromFtdcOrder.apply(ftdcOrder);
						scheduler.onOrdReport(ordReport);
						break;
					case FtdcTrade:
						// 成交回报处理
						FtdcTrade ftdcTrade = ftdcRspMsg.getFtdcTrade();
						log.info("Buffer Queue in FtdcTrade, InstrumentID==[{}], InvestorID==[{}], OrderRef==[{}]",
								ftdcTrade.getInstrumentID(), ftdcTrade.getInvestorID(), ftdcTrade.getOrderRef());
						OrdReport trdReport = fromFtdcTrade.apply(ftdcTrade);
						scheduler.onOrdReport(trdReport);
						break;
					case FtdcInputOrder:
						// TODO 报单错误处理
						FtdcInputOrder ftdcInputOrder = ftdcRspMsg.getFtdcInputOrder();
						log.info("Buffer Queue in [FtdcInputOrder] -> {}", JsonUtil.toJson(ftdcInputOrder));
						break;
					case FtdcInputOrderAction:
						// TODO 撤单错误处理1
						FtdcInputOrderAction ftdcInputOrderAction = ftdcRspMsg.getFtdcInputOrderAction();
						log.info("Buffer Queue in [FtdcInputOrderAction] -> {}", JsonUtil.toJson(ftdcInputOrderAction));
						break;
					case FtdcOrderAction:
						// TODO 撤单错误处理2
						FtdcOrderAction ftdcOrderAction = ftdcRspMsg.getFtdcOrderAction();
						log.info("Buffer Queue in [FtdcOrderAction] -> {}", JsonUtil.toJson(ftdcOrderAction));
						break;
					default:
						log.warn("Buffer Queue unprocessed [FtdcRspMsg] -> {}", JsonUtil.toJson(ftdcRspMsg));
						break;
					}
				}));
	}

	@Override
	protected boolean startup0() {
		try {
			ftdcGateway.initAndJoin();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 订阅行情实现
	 */
	@Override
	public boolean subscribeMarketData(Instrument... instruments) {
		try {
			if (isMdAvailable) {
				ftdcGateway
						.SubscribeMarketData(Stream.of(instruments).map(Instrument::code).collect(Collectors.toSet()));
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("ftdcGateway#SubscribeMarketData exception -> {}", e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 订单转换为FTDC新订单
	 */
	private final ToFtdcInputOrder toFtdcInputOrder;

	@Override
	public boolean newOredr(Account account, ChildOrder order) {
		try {
			CThostFtdcInputOrderField ftdcInputOrder = toFtdcInputOrder.apply(order);
			String orderRef = Integer.toString(OrderRefGenerator.next(order.strategyId()));
			/**
			 * 设置OrderRef
			 */
			ftdcInputOrder.setOrderRef(orderRef);
			OrderRefKeeper.put(orderRef, order.uniqueId());
			ftdcGateway.ReqOrderInsert(ftdcInputOrder);
			return true;
		} catch (Exception e) {
			log.error("#############################################################");
			log.error("ftdcGateway.ReqOrderInsert exception -> {}", e.getMessage(), e);
			log.error("#############################################################");
			return false;
		}
	}

	/**
	 * 订单转换为FTDC撤单
	 */
	private final ToFtdcInputOrderAction toFtdcInputOrderAction;

	@Override
	public boolean cancelOrder(Account account, ChildOrder order) {
		try {
			CThostFtdcInputOrderActionField ftdcInputOrderAction = toFtdcInputOrderAction.apply(order);
			String orderRef = OrderRefKeeper.getOrderRef(order.uniqueId());

			ftdcInputOrderAction.setOrderRef(orderRef);
			ftdcInputOrderAction.setOrderActionRef(OrderRefGenerator.next(order.strategyId()));
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
				startNewThread(() -> {
					synchronized (mutex) {
						log.info("FtdcAdaptor :: Ready to sent ReqQryInvestorPosition, Waiting...");
						sleep(1250);
						ftdcGateway.ReqQryOrder(instrument.symbol().exchange().code());
						log.info("FtdcAdaptor :: Has been sent ReqQryInvestorPosition");
					}
				}, "QueryOrder-SubThread");
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
				startNewThread(() -> {
					synchronized (mutex) {
						log.info("FtdcAdaptor :: Ready to sent ReqQryInvestorPosition, Waiting...");
						sleep(1250);
						ftdcGateway.ReqQryInvestorPosition(instrument.symbol().exchange().code(), instrument.code());
						log.info("FtdcAdaptor :: Has been sent ReqQryInvestorPosition");
					}
				}, "QueryPositions-SubThread");
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
	public boolean queryBalance(Account account) {
		try {
			if (isTraderAvailable) {
				startNewThread(() -> {
					synchronized (mutex) {
						log.info("FtdcAdaptor :: Ready to sent ReqQryTradingAccount, Waiting...");
						sleep(1250);
						ftdcGateway.ReqQryTradingAccount();
						log.info("FtdcAdaptor :: Has been sent ReqQryTradingAccount");
					}
				}, "QueryBalance-SubThread");
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
		// TODO close adaptor
	}

	@Override
	public boolean sendCommand(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

}
