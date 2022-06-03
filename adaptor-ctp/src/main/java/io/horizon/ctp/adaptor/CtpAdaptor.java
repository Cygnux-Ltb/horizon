package io.horizon.ctp.adaptor;

import static io.mercury.common.concurrent.queue.jct.JctSingleConsumerQueue.mpscQueue;
import static io.mercury.common.datetime.EpochTime.getEpochMillis;
import static io.mercury.common.thread.SleepSupport.sleep;
import static io.mercury.common.thread.ThreadSupport.startNewThread;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.set.MutableSet;
import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcInputOrderActionField;
import ctp.thostapi.CThostFtdcInputOrderField;
import io.horizon.ctp.adaptor.converter.FtdcOrderConverter;
import io.horizon.ctp.adaptor.converter.MarketDataConverter;
import io.horizon.ctp.adaptor.converter.OrderReportConverter;
import io.horizon.ctp.gateway.CtpGateway;
import io.horizon.ctp.gateway.CtpGateway.CtpRunMode;
import io.horizon.ctp.gateway.msg.FtdcRspMsg;
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
import io.horizon.trader.adaptor.AdaptorRunMode;
import io.horizon.trader.adaptor.AdaptorType;
import io.horizon.trader.handler.AdaptorReportHandler;
import io.horizon.trader.handler.InboundHandler;
import io.horizon.trader.handler.InboundHandler.InboundSchedulerWrapper;
import io.horizon.trader.handler.OrderReportHandler;
import io.horizon.trader.transport.enums.TAdaptorStatus;
import io.horizon.trader.transport.inbound.CancelOrder;
import io.horizon.trader.transport.inbound.NewOrder;
import io.horizon.trader.transport.inbound.QueryBalance;
import io.horizon.trader.transport.inbound.QueryOrder;
import io.horizon.trader.transport.inbound.QueryPositions;
import io.horizon.trader.transport.outbound.AdaptorReport;
import io.horizon.trader.transport.outbound.OrderReport;
import io.mercury.common.collections.MutableSets;
import io.mercury.common.collections.queue.Queue;
import io.mercury.common.functional.Handler;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.common.util.ArrayUtil;
import io.mercury.serialization.json.JsonWrapper;
import jakarta.annotation.PostConstruct;

/**
 * 
 * CTP Adaptor, 用于连接上期CTP柜台
 * 
 * @author yellow013
 *
 */
public class CtpAdaptor extends AbstractAdaptor {

	private static final Logger log = Log4j2LoggerFactory.getLogger(CtpAdaptor.class);

	// 行情转换器
	private final MarketDataConverter marketDataConverter = new MarketDataConverter();

	// 转换订单回报
	private final OrderReportConverter orderReportConverter = new OrderReportConverter();

	// Ftdc Config
	private final CtpConfig config;

	// TODO 两个INT类型可以合并
	private volatile int frontId;
	private volatile int sessionId;

	private volatile boolean mdAvailable;
	private volatile boolean isTraderAvailable;

	// FTDC RSP 消息处理器
	private final Handler<FtdcRspMsg> handler;

	// 队列缓冲区
	private Queue<FtdcRspMsg> queue;

	/**
	 * 传入MarketDataHandler, OrderReportHandler, AdaptorReportHandler实现,
	 * 由构造函数内部转换为MPSC队列缓冲区
	 * 
	 * @param account
	 * @param config
	 * @param marketDataHandler
	 * @param orderReportHandler
	 * @param adaptorReportHandler
	 */
	public CtpAdaptor(@Nonnull Account account, @Nonnull CtpConfig config,
			@Nonnull MarketDataHandler<BasicMarketData> marketDataHandler,
			@Nonnull OrderReportHandler orderReportHandler, @Nonnull AdaptorReportHandler adaptorReportHandler) {
		this(account, config, AdaptorRunMode.Normal, marketDataHandler, orderReportHandler, adaptorReportHandler);
	}

	/**
	 * 传入MarketDataHandler, OrderReportHandler, AdaptorReportHandler实现,
	 * 由构造函数内部转换为MPSC队列缓冲区
	 * 
	 * @param account
	 * @param config
	 * @param mode
	 * @param marketDataHandler
	 * @param orderReportHandler
	 * @param adaptorReportHandler
	 */
	public CtpAdaptor(@Nonnull Account account, @Nonnull CtpConfig config, AdaptorRunMode mode,
			@Nonnull MarketDataHandler<BasicMarketData> marketDataHandler,
			@Nonnull OrderReportHandler orderReportHandler, @Nonnull AdaptorReportHandler adaptorReportHandler) {
		this(account, config, mode,
				new InboundSchedulerWrapper<>(marketDataHandler, orderReportHandler, adaptorReportHandler, log));
	}

	/**
	 * 传入InboundScheduler实现, 由构造函数在内部转换为MPSC队列缓冲区
	 * 
	 * @param account
	 * @param config
	 * @param scheduler
	 */
	public CtpAdaptor(@Nonnull Account account, @Nonnull CtpConfig config,
			@Nonnull InboundHandler<BasicMarketData> scheduler) {
		this(account, config, AdaptorRunMode.Normal, scheduler);
	}

	/**
	 * 传入InboundScheduler实现, 由构造函数在内部转换为MPSC队列缓冲区
	 * 
	 * @param account
	 * @param config
	 * @param mode
	 * @param scheduler
	 */
	public CtpAdaptor(@Nonnull Account account, @Nonnull CtpConfig config, AdaptorRunMode mode,
			@Nonnull InboundHandler<BasicMarketData> scheduler) {
		super(CtpAdaptor.class.getSimpleName(), account);
		// 创建队列缓冲区
		this.queue = mpscQueue(CtpAdaptor.class.getSimpleName() + "-Buf").setCapacity(32).process(msg -> {
			switch (msg.getType()) {
			case MdConnect:
				FtdcMdConnect mdConnect = msg.getMdConnect();
				this.mdAvailable = mdConnect.available();
				log.info("Adaptor buf processed FtdcMdConnect, isMdAvailable==[{}]", mdAvailable);
				final AdaptorReport mdReport;
				if (mdAvailable)
					mdReport = AdaptorReport.newBuilder().setEpochMillis(getEpochMillis()).setAdaptorId(getAdaptorId())
							.setStatus(TAdaptorStatus.MD_ENABLE).build();
				else
					mdReport = AdaptorReport.newBuilder().setEpochMillis(getEpochMillis()).setAdaptorId(getAdaptorId())
							.setStatus(TAdaptorStatus.MD_DISABLE).build();
				scheduler.onAdaptorReport(mdReport);
				break;
			case TraderConnect:
				FtdcTraderConnect traderConnect = msg.getTraderConnect();
				this.isTraderAvailable = traderConnect.available();
				this.frontId = traderConnect.frontId();
				this.sessionId = traderConnect.sessionId();
				log.info(
						"Adaptor buf processed FtdcTraderConnect, isTraderAvailable==[{}], frontId==[{}], sessionId==[{}]",
						isTraderAvailable, frontId, sessionId);
				final AdaptorReport traderReport;
				if (isTraderAvailable)
					traderReport = AdaptorReport.newBuilder().setEpochMillis(getEpochMillis())
							.setAdaptorId(getAdaptorId()).setStatus(TAdaptorStatus.TRADER_ENABLE).build();
				else
					traderReport = AdaptorReport.newBuilder().setEpochMillis(getEpochMillis())
							.setAdaptorId(getAdaptorId()).setStatus(TAdaptorStatus.TRADER_DISABLE).build();
				scheduler.onAdaptorReport(traderReport);
				break;
			case DepthMarketData:
				// 行情处理
				// TODO
				// multicaster.publish(rspMsg.getDepthMarketData());
				BasicMarketData marketData = marketDataConverter.withFtdcDepthMarketData(msg.getDepthMarketData());
				scheduler.onMarketData(marketData);
				break;
			case Order:
				// 报单回报处理
				FtdcOrder ftdcOrder = msg.getOrder();
				log.info(
						"Adaptor buf in FtdcOrder, InstrumentID==[{}], InvestorID==[{}], "
								+ "OrderRef==[{}], LimitPrice==[{}], VolumeTotalOriginal==[{}], OrderStatus==[{}]",
						ftdcOrder.getInstrumentID(), ftdcOrder.getInvestorID(), ftdcOrder.getOrderRef(),
						ftdcOrder.getLimitPrice(), ftdcOrder.getVolumeTotalOriginal(), ftdcOrder.getOrderStatus());
				OrderReport report0 = orderReportConverter.withFtdcOrder(ftdcOrder);
				scheduler.onOrderReport(report0);
				break;
			case Trade:
				// 成交回报处理
				FtdcTrade ftdcTrade = msg.getTrade();
				log.info("Adaptor buf in FtdcTrade, InstrumentID==[{}], InvestorID==[{}], OrderRef==[{}]",
						ftdcTrade.getInstrumentID(), ftdcTrade.getInvestorID(), ftdcTrade.getOrderRef());
				OrderReport report1 = orderReportConverter.withFtdcTrade(ftdcTrade);
				scheduler.onOrderReport(report1);
				break;
			case InputOrder:
				// TODO 报单错误处理
				FtdcInputOrder ftdcInputOrder = msg.getInputOrder();
				log.info("Adaptor buf in [FtdcInputOrder] -> {}", JsonWrapper.toJson(ftdcInputOrder));
				break;
			case InputOrderAction:
				// TODO 撤单错误处理1
				FtdcInputOrderAction ftdcInputOrderAction = msg.getInputOrderAction();
				log.info("Adaptor buf in [FtdcInputOrderAction] -> {}", JsonWrapper.toJson(ftdcInputOrderAction));
				break;
			case OrderAction:
				// TODO 撤单错误处理2
				FtdcOrderAction ftdcOrderAction = msg.getOrderAction();
				log.info("Adaptor buf in [FtdcOrderAction] -> {}", JsonWrapper.toJson(ftdcOrderAction));
				break;
			default:
				log.warn("Adaptor buf unprocessed [FtdcRspMsg] -> {}", JsonWrapper.toJson(msg));
				break;
			}
		});
		this.handler = queue::enqueue;
		this.config = config;
		this.mode = mode;
		initializer();
	}

	/**
	 * 使用正常模式和指定的FTDC消息队列构建Adaptor
	 * 
	 * @param account
	 * @param config
	 * @param queue
	 */
	public CtpAdaptor(@Nonnull Account account, @Nonnull CtpConfig config, @Nonnull Queue<FtdcRspMsg> queue) {
		this(account, config, AdaptorRunMode.Normal, queue);
	}

	/**
	 * 使用指定的运行模式和FTDC消息队列构建Adaptor
	 * 
	 * @param account
	 * @param config
	 * @param mode
	 * @param queue
	 */
	public CtpAdaptor(@Nonnull Account account, @Nonnull CtpConfig config, AdaptorRunMode mode,
			@Nonnull Queue<FtdcRspMsg> queue) {
		this(account, config, mode,
				// 使用入队函数实现Handler<FtdcRspMsg>
				queue::enqueue);
		this.queue = queue;
	}

	/**
	 * 使用正常模式和指定的FTDC消息处理器构建Adaptor
	 * 
	 * @param account
	 * @param config
	 * @param handler
	 */
	public CtpAdaptor(@Nonnull Account account, @Nonnull CtpConfig config, @Nonnull Handler<FtdcRspMsg> handler) {
		this(account, config, AdaptorRunMode.Normal, handler);
	}

	/**
	 * 使用指定的运行模式和FTDC消息处理器构建Adaptor
	 * 
	 * @param account
	 * @param config
	 * @param mode
	 * @param handler
	 */
	public CtpAdaptor(@Nonnull Account account, @Nonnull CtpConfig config, AdaptorRunMode mode,
			@Nonnull Handler<FtdcRspMsg> handler) {
		super(CtpAdaptor.class.getSimpleName(), account);
		this.handler = handler;
		this.config = config;
		this.mode = mode;
		initializer();
	}

	// GatewayId
	private String gatewayId;
	// CtpGateway
	private CtpGateway gateway;
	// FTDC报单请求转换器
	private FtdcOrderConverter orderConverter;

	@PostConstruct
	private void initializer() {
		// 创建FtdcOrderConverter
		this.orderConverter = new FtdcOrderConverter(config);
		// 创建GatewayId
		this.gatewayId = config.getBrokerId() + "-" + config.getInvestorId();
		// 创建Gateway
		log.info("Try create gateway, gatewayId -> {}", gatewayId);
		this.gateway = new CtpGateway(gatewayId, config, handler, CtpRunMode.get(mode));
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

	@Override
	public AdaptorType getAdaptorType() {
		return CtpAdaptorType.INSTANCE;
	}

	/**
	 * 订阅行情实现
	 */
	@Override
	public boolean subscribeMarketData(@Nonnull Instrument[] instruments) {
		try {
			if (mdAvailable) {
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
				Arrays.stream(instruments)
						.forEach(instrument -> subscribedInstrumentCodes.add(instrument.getInstrumentCode()));
				log.info("{} -> market not available, already recorded instrument code", gatewayId);
				log.info("subscribed instrument codes -> {}", JsonWrapper.toJson(subscribedInstrumentCodes));
				return false;
			}
		} catch (Exception e) {
			log.error("{} -> exec SubscribeMarketData has exception -> {}", gatewayId, e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean newOredr(NewOrder order) {
		try {
			CThostFtdcInputOrderField field = orderConverter.convertToInputOrder(order);
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
	public boolean cancelOrder(CancelOrder order) {
		try {
			CThostFtdcInputOrderActionField field = orderConverter.convertToInputOrderAction(order);
			String orderRef = OrderRefKeeper.getOrderRef(order.getOrdSysId());
			// 目前使用orderRef进行撤单
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
	public boolean queryOrder(@Nonnull QueryOrder req) {
		try {
			if (isTraderAvailable) {
				startNewThread("QueryOrder-Worker", () -> {
					synchronized (mutex) {
						log.info("{} -> Ready to sent ReqQryInvestorPosition, Waiting...", adaptorId);
						sleep(queryInterval);
						gateway.ReqQryOrder(req.getExchangeCode(), req.getInstrumentCode());
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
	public boolean queryPositions(@Nonnull QueryPositions req) {
		try {
			if (isTraderAvailable) {
				startNewThread("QueryPositions-Worker", () -> {
					synchronized (mutex) {
						log.info("{} -> Ready to sent ReqQryInvestorPosition, Waiting...", adaptorId);
						sleep(queryInterval);
						gateway.ReqQryInvestorPosition(req.getExchangeCode(), req.getInstrumentCode());
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
	public boolean queryBalance(QueryBalance req) {
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
