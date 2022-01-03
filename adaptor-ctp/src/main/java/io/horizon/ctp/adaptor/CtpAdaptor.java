package io.horizon.ctp.adaptor;

import static io.mercury.common.thread.SleepSupport.sleep;
import static io.mercury.common.thread.Threads.startNewThread;

import java.io.IOException;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.set.MutableSet;
import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcInputOrderActionField;
import ctp.thostapi.CThostFtdcInputOrderField;
import io.horizon.ctp.adaptor.converter.FtdcOrderConverter;
import io.horizon.ctp.adaptor.converter.MarketDataConverter;
import io.horizon.ctp.adaptor.converter.OrderReportConverter;
import io.horizon.ctp.gateway.CtpGateway;
import io.horizon.ctp.gateway.FtdcRspMsg;
import io.horizon.ctp.gateway.rsp.FtdcInputOrder;
import io.horizon.ctp.gateway.rsp.FtdcInputOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcMdConnect;
import io.horizon.ctp.gateway.rsp.FtdcOrder;
import io.horizon.ctp.gateway.rsp.FtdcOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcTrade;
import io.horizon.ctp.gateway.rsp.FtdcTraderConnect;
import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.handler.MarketDataHandler;
import io.horizon.market.instrument.Instrument;
import io.horizon.trader.account.Account;
import io.horizon.trader.adaptor.AbstractAdaptor;
import io.horizon.trader.adaptor.AdaptorStatus;
import io.horizon.trader.handler.AdaptorReportHandler;
import io.horizon.trader.handler.InboundHandler;
import io.horizon.trader.handler.InboundHandler.InboundSchedulerWrapper;
import io.horizon.trader.handler.OrderReportHandler;
import io.horizon.trader.order.ChildOrder;
import io.horizon.trader.report.AdaptorReport;
import io.horizon.trader.report.OrderReport;
import io.mercury.common.collections.MutableSets;
import io.mercury.common.concurrent.queue.Queue;
import io.mercury.common.concurrent.queue.jct.JctSingleConsumerQueue;
import io.mercury.common.datetime.EpochUtil;
import io.mercury.common.functional.Handler;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.common.util.ArrayUtil;
import io.mercury.serialization.json.JsonWrapper;

/**
 * 
 * CTP Adaptor, 用于连接上期CTP柜台
 * 
 * @author yellow013
 *
 */
public final class CtpAdaptor extends AbstractAdaptor {

	private static final Logger log = Log4j2LoggerFactory.getLogger(CtpAdaptor.class);

	// 行情转换器
	private final MarketDataConverter marketDataConverter = new MarketDataConverter();

	// 转换订单回报
	private final OrderReportConverter orderReportConverter = new OrderReportConverter();

	// Ftdc Config
	private final CtpConfig config;

	// FTDC报单请求转换器
	private final FtdcOrderConverter ftdcOrderConverter;

	// TODO 两个INT类型可以合并
	private volatile int frontId;
	private volatile int sessionId;

	private volatile boolean isMdAvailable;
	private volatile boolean isTraderAvailable;

	// FTDC 消息处理器
	private final Handler<FtdcRspMsg> handler;

//	private final MarketDataMulticaster<FtdcDepthMarketData, FastMarketDataBridge> multicaster = new MarketDataMulticaster<>(
//			getAdaptorId(), FastMarketDataBridge::newInstance, (marketData, sequence, ftdcMarketData) -> {
//				Instrument instrument = InstrumentKeeper.getInstrument(ftdcMarketData.getInstrumentID());
//				marketData.setInstrument(instrument);
//				var multiplier = instrument.getSymbol().getMultiplier();
//				var fastMarketData = marketData.getFastMarketData();
//				// TODO
//				fastMarketData.setLastPrice(multiplier.toLong(ftdcMarketData.getLastPrice()));
//				marketData.updated();
//			});

	/**
	 * 传入MarketDataHandler, OrderReportHandler, AdaptorReportHandler实现,
	 * 由构造函数内部转换为MPSC队列缓冲区
	 * 
	 * @param account
	 * @param cinfig
	 * @param marketDataHandler
	 * @param orderReportHandler
	 * @param adaptorReportHandler
	 */
	public CtpAdaptor(@Nonnull Account account, @Nonnull CtpConfig cinfig,
			@Nonnull MarketDataHandler<BasicMarketData> marketDataHandler,
			@Nonnull OrderReportHandler orderReportHandler, @Nonnull AdaptorReportHandler adaptorReportHandler) {
		this(account, cinfig,
				new InboundSchedulerWrapper<>(marketDataHandler, orderReportHandler, adaptorReportHandler, log));
	}

	private Queue<FtdcRspMsg> queue;

	/**
	 * 传入InboundScheduler实现, 由构造函数在内部转换为MPSC队列缓冲区
	 * 
	 * @param account
	 * @param cinfig
	 * @param scheduler
	 */
	public CtpAdaptor(@Nonnull Account account, @Nonnull CtpConfig cinfig,
			@Nonnull InboundHandler<BasicMarketData> scheduler) {
		super("CTP-Adaptor", account);
		// 创建队列缓冲区
		this.queue = JctSingleConsumerQueue.multiProducer(adaptorId + "-buffer").setCapacity(32).build(rspMsg -> {
			switch (rspMsg.getType()) {
			case MdConnect:
				FtdcMdConnect mdConnect = rspMsg.getMdConnect();
				this.isMdAvailable = mdConnect.isAvailable();
				log.info("Swap Queue processed FtdcMdConnect, isMdAvailable==[{}]", isMdAvailable);
				final AdaptorReport mdReport;
				if (isMdAvailable)
					mdReport = AdaptorReport.newBuilder().setEpochMillis(EpochUtil.getEpochMillis())
							.setAdaptorId(getAdaptorId()).setStatus(AdaptorStatus.MdEnable.getCode()).build();
				else
					mdReport = AdaptorReport.newBuilder().setEpochMillis(EpochUtil.getEpochMillis())
							.setAdaptorId(getAdaptorId()).setStatus(AdaptorStatus.MdDisable.getCode()).build();
				scheduler.onAdaptorReport(mdReport);
				break;
			case TraderConnect:
				FtdcTraderConnect traderConnect = rspMsg.getTraderConnect();
				this.isTraderAvailable = traderConnect.isAvailable();
				this.frontId = traderConnect.getFrontID();
				this.sessionId = traderConnect.getSessionID();
				log.info(
						"Swap Queue processed FtdcTraderConnect, "
								+ "isTraderAvailable==[{}], frontId==[{}], sessionId==[{}]",
						isTraderAvailable, frontId, sessionId);
				final AdaptorReport traderReport;
				if (isTraderAvailable)
					traderReport = AdaptorReport.newBuilder().setEpochMillis(EpochUtil.getEpochMillis())
							.setAdaptorId(getAdaptorId()).setStatus(AdaptorStatus.TraderEnable.getCode()).build();
				else
					traderReport = AdaptorReport.newBuilder().setEpochMillis(EpochUtil.getEpochMillis())
							.setAdaptorId(getAdaptorId()).setStatus(AdaptorStatus.TraderEnable.getCode()).build();
				scheduler.onAdaptorReport(traderReport);
				break;
			case DepthMarketData:
				// 行情处理
				// TODO
				// multicaster.publish(rspMsg.getDepthMarketData());
				BasicMarketData marketData = marketDataConverter.fromFtdcDepthMarketData(rspMsg.getDepthMarketData());
				scheduler.onMarketData(marketData);
				break;
			case Order:
				// 报单回报处理
				FtdcOrder ftdcOrder = rspMsg.getOrder();
				log.info(
						"Buffer Queue in FtdcOrder, InstrumentID==[{}], InvestorID==[{}], "
								+ "OrderRef==[{}], LimitPrice==[{}], VolumeTotalOriginal==[{}], OrderStatus==[{}]",
						ftdcOrder.getInstrumentID(), ftdcOrder.getInvestorID(), ftdcOrder.getOrderRef(),
						ftdcOrder.getLimitPrice(), ftdcOrder.getVolumeTotalOriginal(), ftdcOrder.getOrderStatus());
				OrderReport report0 = orderReportConverter.fromFtdcOrder(ftdcOrder);
				scheduler.onOrderReport(report0);
				break;
			case Trade:
				// 成交回报处理
				FtdcTrade ftdcTrade = rspMsg.getTrade();
				log.info("Buffer Queue in FtdcTrade, InstrumentID==[{}], InvestorID==[{}], OrderRef==[{}]",
						ftdcTrade.getInstrumentID(), ftdcTrade.getInvestorID(), ftdcTrade.getOrderRef());
				OrderReport report1 = orderReportConverter.fromFtdcTrade(ftdcTrade);
				scheduler.onOrderReport(report1);
				break;
			case InputOrder:
				// TODO 报单错误处理
				FtdcInputOrder ftdcInputOrder = rspMsg.getInputOrder();
				log.info("Buffer Queue in [FtdcInputOrder] -> {}", JsonWrapper.toJson(ftdcInputOrder));
				break;
			case InputOrderAction:
				// TODO 撤单错误处理1
				FtdcInputOrderAction ftdcInputOrderAction = rspMsg.getInputOrderAction();
				log.info("Buffer Queue in [FtdcInputOrderAction] -> {}", JsonWrapper.toJson(ftdcInputOrderAction));
				break;
			case OrderAction:
				// TODO 撤单错误处理2
				FtdcOrderAction ftdcOrderAction = rspMsg.getOrderAction();
				log.info("Buffer Queue in [FtdcOrderAction] -> {}", JsonWrapper.toJson(ftdcOrderAction));
				break;
			default:
				log.warn("Buffer Queue unprocessed [FtdcRspMsg] -> {}", JsonWrapper.toJson(rspMsg));
				break;
			}
		});
		this.handler = queue::enqueue;
		this.config = cinfig;
		// 创建OrderConverter
		this.ftdcOrderConverter = new FtdcOrderConverter(config);
		init();
	}

	public CtpAdaptor(@Nonnull Account account, @Nonnull CtpConfig cinfig, @Nonnull Queue<FtdcRspMsg> queue) {
		super("CTP-Adaptor", account);
		// 创建队列缓冲区
		this.queue = queue;
		this.handler = queue::enqueue;
		this.config = cinfig;
		// 创建OrderConverter
		this.ftdcOrderConverter = new FtdcOrderConverter(config);
		init();
	}

	/**
	 * 
	 * @param account
	 * @param cinfig
	 * @param handler
	 */
	public CtpAdaptor(@Nonnull Account account, @Nonnull CtpConfig cinfig, @Nonnull Handler<FtdcRspMsg> handler) {
		super("CTP-Adaptor", account);
		this.handler = handler;
		this.config = cinfig;
		// 创建OrderConverter
		this.ftdcOrderConverter = new FtdcOrderConverter(config);
		init();
	}

	// GatewayId
	private String gatewayId;
	// CtpGateway
	private CtpGateway gateway;

	private void init() {
		// 创建GatewayId
		this.gatewayId = "CtpGateway-" + config.getBrokerId() + "-" + config.getInvestorId();
		// 创建Gateway
		log.info("Try create gateway, gatewayId -> {}", gatewayId);
		this.gateway = new CtpGateway(gatewayId, config, handler);
		log.info("Create gateway success, gatewayId -> {}", gatewayId);

	}

	@Override
	protected boolean startup0() {
		try {
			gateway.bootstrap();
			log.info("{} -> bootstrap finish", gatewayId);
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
					log.warn("{} -> Input instruments is null or empty, Use subscribed instruments", adaptorId);
					if (subscribedInstrumentCodes.isEmpty()) {
						// 已记录的订阅Instrument为空
						log.warn("{} -> Subscribed instruments is empty", adaptorId);
						return false;
					} else {
						// 使用已经订阅过的Instrument
						String[] instrumentCodes = new String[subscribedInstrumentCodes.size()];
						log.info("Add subscribe instrument code -> Count==[{}]", subscribedInstrumentCodes.size());
						subscribedInstrumentCodes.toArray(instrumentCodes);
						gateway.SubscribeMarketData(instrumentCodes);
						return true;
					}
				} else {
					String[] instrumentCodes = new String[instruments.length];
					for (int i = 0; i < instruments.length; i++) {
						instrumentCodes[i] = instruments[i].getInstrumentCode();
						log.info("Add subscribe instrument -> instruementCode==[{}]", instrumentCodes[i]);
						subscribedInstrumentCodes.add(instrumentCodes[i]);
					}
					gateway.SubscribeMarketData(instrumentCodes);
					return true;
				}
			} else {
				log.warn("{} -> market not available", gatewayId);
				return false;
			}
		} catch (Exception e) {
			log.error("{} -> exec SubscribeMarketData has exception -> {}", gatewayId, e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean newOredr(ChildOrder order) {
		try {
			CThostFtdcInputOrderField field = ftdcOrderConverter.toInputOrder(order);
			String orderRef = Integer.toString(OrderRefKeeper.nextOrderRef());
			// 设置OrderRef
			field.setOrderRef(orderRef);
			OrderRefKeeper.put(orderRef, order.getOrdSysId());
			gateway.ReqOrderInsert(field);
			return true;
		} catch (Exception e) {
			log.error("{} -> exec ReqOrderInsert has exception -> {}", gatewayId, e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean cancelOrder(ChildOrder order) {
		try {
			CThostFtdcInputOrderActionField field = ftdcOrderConverter.toInputOrderAction(order);
			String orderRef = OrderRefKeeper.getOrderRef(order.getOrdSysId());
			field.setOrderRef(orderRef);
			field.setOrderActionRef(OrderRefKeeper.nextOrderRef());
			gateway.ReqOrderAction(field);
			return true;
		} catch (OrderRefNotFoundException e) {
			log.error(e.getMessage(), e);
			return false;
		} catch (Exception e) {
			log.error("{} -> exec ReqOrderAction has exception -> {}", gatewayId, e.getMessage(), e);
			return false;
		}
	}

	// 查询互斥锁, 保证同时只进行一次查询, 满足监管要求
	private final Object mutex = new Object();

	// 查询间隔, 依据CTP规定限制
	private final long queryInterval = 1100L;

	@Override
	public boolean queryOrder(@Nonnull Instrument instrument) {
		try {
			if (isTraderAvailable) {
				startNewThread("QueryOrder-Worker", () -> {
					synchronized (mutex) {
						log.info("{} -> Ready to sent ReqQryInvestorPosition, Waiting...", adaptorId);
						sleep(queryInterval);
						gateway.ReqQryOrder(instrument.getExchangeCode());
						log.info("{} -> Has been sent ReqQryInvestorPosition", adaptorId);
					}
				});
				return true;
			} else
				return false;
		} catch (Exception e) {
			log.error("{} -> exec ReqQryOrder has exception -> {}", gatewayId, e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean queryPositions(@Nonnull Instrument instrument) {
		try {
			if (isTraderAvailable) {
				startNewThread("QueryPositions-Worker", () -> {
					synchronized (mutex) {
						log.info("{} -> Ready to sent ReqQryInvestorPosition, Waiting...", adaptorId);
						sleep(queryInterval);
						gateway.ReqQryInvestorPosition(instrument.getExchangeCode(), instrument.getInstrumentCode());
						log.info("{} -> Has been sent ReqQryInvestorPosition", adaptorId);
					}
				});
				return true;
			} else
				return false;
		} catch (Exception e) {
			log.error("{} -> exec ReqQryInvestorPosition has exception -> {}", gatewayId, e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean queryBalance() {
		try {
			if (isTraderAvailable) {
				startNewThread("QueryBalance-Worker", () -> {
					synchronized (mutex) {
						log.info("{} -> Ready to sent ReqQryTradingAccount, Waiting...", adaptorId);
						sleep(queryInterval);
						gateway.ReqQryTradingAccount();
						log.info("{} -> Has been sent ReqQryTradingAccount", adaptorId);
					}
				});
				return true;
			} else
				return false;
		} catch (Exception e) {
			log.error("{} -> exec ReqQryTradingAccount has exception -> {}", gatewayId, e.getMessage(), e);
			return false;
		}
	}

	@Override
	public void close() throws IOException {
		try {
			gateway.close();
			if (queue != null) {
				while (!queue.isEmpty())
					;
			}
			log.info("{} -> already closed", adaptorId);
		} catch (Exception e) {
			log.error("{} -> exec close has exception -> {}", gatewayId, e.getMessage(), e);
			throw new IOException(e);
		}
	}

}
