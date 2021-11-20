package io.horizon.ftdc.gateway;

import static io.mercury.common.thread.SleepSupport.sleep;
import static io.mercury.common.thread.Threads.startNewMaxPriorityThread;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Native;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcDepthMarketDataField;
import ctp.thostapi.CThostFtdcInputOrderActionField;
import ctp.thostapi.CThostFtdcInputOrderField;
import ctp.thostapi.CThostFtdcInvestorPositionField;
import ctp.thostapi.CThostFtdcMdApi;
import ctp.thostapi.CThostFtdcMdSpi;
import ctp.thostapi.CThostFtdcOrderActionField;
import ctp.thostapi.CThostFtdcOrderField;
import ctp.thostapi.CThostFtdcQryInstrumentField;
import ctp.thostapi.CThostFtdcQryInvestorPositionField;
import ctp.thostapi.CThostFtdcQryOrderField;
import ctp.thostapi.CThostFtdcQrySettlementInfoField;
import ctp.thostapi.CThostFtdcQryTradingAccountField;
import ctp.thostapi.CThostFtdcReqAuthenticateField;
import ctp.thostapi.CThostFtdcReqUserLoginField;
import ctp.thostapi.CThostFtdcRspAuthenticateField;
import ctp.thostapi.CThostFtdcRspInfoField;
import ctp.thostapi.CThostFtdcRspUserLoginField;
import ctp.thostapi.CThostFtdcSpecificInstrumentField;
import ctp.thostapi.CThostFtdcTradeField;
import ctp.thostapi.CThostFtdcTraderApi;
import ctp.thostapi.CThostFtdcTraderSpi;
import ctp.thostapi.CThostFtdcTradingAccountField;
import ctp.thostapi.THOST_TE_RESUME_TYPE;
import io.horizon.ftdc.exception.NativeLibraryLoadException;
import io.horizon.ftdc.gateway.converter.CThostFtdcDepthMarketDataConverter;
import io.horizon.ftdc.gateway.converter.CThostFtdcInputOrderActionConverter;
import io.horizon.ftdc.gateway.converter.CThostFtdcInputOrderConverter;
import io.horizon.ftdc.gateway.converter.CThostFtdcInvestorPositionConverter;
import io.horizon.ftdc.gateway.converter.CThostFtdcOrderActionConverter;
import io.horizon.ftdc.gateway.converter.CThostFtdcOrderConverter;
import io.horizon.ftdc.gateway.converter.CThostFtdcTradeConverter;
import io.horizon.ftdc.gateway.rsp.FtdcMdConnect;
import io.horizon.ftdc.gateway.rsp.FtdcTraderConnect;
import io.mercury.common.concurrent.queue.Queue;
import io.mercury.common.datetime.DateTimeUtil;
import io.mercury.common.file.FileUtil;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.thread.SleepSupport;
import io.mercury.common.thread.Threads;
import io.mercury.common.util.Assertor;
import io.mercury.common.util.StringSupport;

@NotThreadSafe
public final class FtdcGateway implements Closeable {

	private static final Logger log = CommonLoggerFactory.getLogger(FtdcGateway.class);

	// 静态加载FtdcLibrary
	static {
		try {
			FtdcLibraryLoader.loadLibrary();
		} catch (NativeLibraryLoadException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	// gatewayId
	private final String gatewayId;

	// 基础配置信息
	private final FtdcConfig config;

	@Native
	private CThostFtdcMdApi ftdcMdApi;
	@Native
	private CThostFtdcTraderApi ftdcTraderApi;

	// 是否已初始化
	private AtomicBoolean isInitialize = new AtomicBoolean(false);

	// 是否登陆行情接口
	private volatile boolean isMdLogin;
	// 是否登陆交易接口
	private volatile boolean isTraderLogin;
	// 是否已认证
	private volatile boolean isAuthenticate;

	// 交易前置号
	private int frontID;
	// 交易会话号
	private int sessionID;

	// 行情请求ID
	private volatile int mdRequestId = -1;
	// 交易请求ID
	private volatile int traderRequestId = -1;

	// 回调消息队列
	private final Queue<RspMsg> queue;

	public FtdcGateway(@Nonnull String gatewayId, @Nonnull FtdcConfig config, @Nonnull Queue<RspMsg> queue) {
		Assertor.nonEmpty(gatewayId, "gatewayId");
		Assertor.nonNull(config, "config");
		Assertor.nonNull(queue, "queue");
		this.gatewayId = gatewayId;
		this.config = config;
		this.queue = queue;
	}

	/**
	 * 创建
	 * 
	 * @return
	 */
	private File generateTempDir() {
		// 创建临时文件存储目录
		File tempDir = FileUtil.mkdirInTmp(gatewayId + "-" + DateTimeUtil.date());
		log.info("Temp file dir is -> {}", tempDir.getAbsolutePath());
		return tempDir;
	}

	/**
	 * 启动并挂起线程
	 */
	public final void bootstrap() {
		if (isInitialize.compareAndSet(false, true)) {
			// 获取临时文件目录
			File tempDir = generateTempDir();
			log.info("CThostFtdcTraderApi.version() -> {}", CThostFtdcTraderApi.GetApiVersion());
			log.info("CThostFtdcMdApi.version() -> {}", CThostFtdcMdApi.GetApiVersion());
			try {
				startNewMaxPriorityThread("FtdcTrader-Thread", () -> traderInitAndJoin(tempDir));
				sleep(2000);
				startNewMaxPriorityThread("FtdcMd-Thread", () -> mdInitAndJoin(tempDir));
			} catch (Exception e) {
				log.error("Method initAndJoin throw Exception -> {}", e.getMessage(), e);
				isInitialize.set(false);
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 
	 * @param tempDir
	 */
	private void mdInitAndJoin(File tempDir) {
		// 指定md临时文件地址
		String mdTempFilePath = new File(tempDir, "md").getAbsolutePath();
		log.info("Gateway -> {} md api use temp file path : {}", gatewayId, mdTempFilePath);
		// 创建mdApi
		this.ftdcMdApi = CThostFtdcMdApi.CreateFtdcMdApi(mdTempFilePath);
		// 创建mdSpi
		CThostFtdcMdSpi ftdcMdSpi = new FtdcMdSpiImpl(new FtdcMdHook());
		// 将mdSpi注册到mdApi
		ftdcMdApi.RegisterSpi(ftdcMdSpi);
		// 注册到md前置机
		ftdcMdApi.RegisterFront(config.getMdAddr());
		// 初始化mdApi
		log.info("Call native function mdApi.Init()...");
		ftdcMdApi.Init();
		// 阻塞当前线程
		log.info("Call native function mdApi.Join()...");
		ftdcMdApi.Join();
	}

	/**
	 * 
	 * @param tempDir
	 */
	private void traderInitAndJoin(File tempDir) {
		// 指定trader临时文件地址
		String traderTempFilePath = new File(tempDir, "trader").getAbsolutePath();
		log.info("Gateway -> {} trader api use temp file path : {}", gatewayId, traderTempFilePath);
		// 创建traderApi
		this.ftdcTraderApi = CThostFtdcTraderApi.CreateFtdcTraderApi(traderTempFilePath);
		// 创建traderSpi
		CThostFtdcTraderSpi ftdcTraderSpi = new FtdcTraderSpiImpl(new FtdcTraderHook());
		// 将traderSpi注册到traderApi
		ftdcTraderApi.RegisterSpi(ftdcTraderSpi);
		// 注册到trader前置机
		ftdcTraderApi.RegisterFront(config.getTraderAddr());
		/// THOST_TERT_RESTART:从本交易日开始重传
		/// THOST_TERT_RESUME:从上次收到的续传
		/// THOST_TERT_QUICK:只传送登录后私有流的内容
		// 订阅公有流和私有流
		ftdcTraderApi.SubscribePublicTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESUME);
		ftdcTraderApi.SubscribePrivateTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESUME);
		// 初始化traderApi
		log.info("Call native function traderApi.Init()...");
		ftdcTraderApi.Init();
		// 阻塞当前线程
		log.info("Call native function traderApi.Join()...");
		ftdcTraderApi.Join();
	}

	/**
	 * 行情订阅接口
	 * 
	 * @param instruements
	 */
	public final void SubscribeMarketData(@Nonnull String... instruements) {
		if (isMdLogin) {
			ftdcMdApi.SubscribeMarketData(instruements, instruements.length);
			log.info("Send SubscribeMarketData -> count==[{}]", instruements.length);
		} else {
			log.warn("Cannot SubscribeMarketData -> isMdLogin == [false]");
		}
	}

	/**
	 * 报单接口
	 * 
	 * @param inputOrderField
	 */
	public final void ReqOrderInsert(CThostFtdcInputOrderField inputOrderField) {
		if (isTraderLogin) {
			// 设置账号信息

			int nRequestID = ++traderRequestId;
			ftdcTraderApi.ReqOrderInsert(inputOrderField, nRequestID);
			log.info(
					"Send ReqOrderInsert OK ->  nRequestID==[{}], OrderRef==[{}], InstrumentID==[{}], "
							+ "CombOffsetFlag==[{}], Direction==[{}], VolumeTotalOriginal==[{}], LimitPrice==[{}]",
					nRequestID, inputOrderField.getOrderRef(), inputOrderField.getInstrumentID(),
					inputOrderField.getCombOffsetFlag(), inputOrderField.getDirection(),
					inputOrderField.getVolumeTotalOriginal(), inputOrderField.getLimitPrice());
		} else {
			log.error("Trader error :: TraderApi is not login");
		}
	}

	/**
	 * 撤单请求
	 * 
	 * @param inputOrderActionField
	 */
	public final void ReqOrderAction(CThostFtdcInputOrderActionField inputOrderActionField) {
		if (isTraderLogin) {
			int nRequestID = ++traderRequestId;
			ftdcTraderApi.ReqOrderAction(inputOrderActionField, nRequestID);
			log.info(
					"Send ReqOrderAction OK -> nRequestID==[{}], OrderRef==[{}], OrderActionRef==[{}], "
							+ "BrokerID==[{}], InvestorID==[{}], InstrumentID==[{}]",
					nRequestID, inputOrderActionField.getOrderRef(), inputOrderActionField.getOrderActionRef(),
					inputOrderActionField.getBrokerID(), inputOrderActionField.getInvestorID(),
					inputOrderActionField.getInstrumentID());
		} else {
			log.error("Trader error :: TraderApi is not login");
		}
	}

	/**
	 * 查询订单
	 * 
	 * @param exchangeId
	 */
	public final void ReqQryOrder(String exchangeId) {
		CThostFtdcQryOrderField qryOrderField = new CThostFtdcQryOrderField();
		qryOrderField.setBrokerID(config.getBrokerId());
		qryOrderField.setInvestorID(config.getInvestorId());
		qryOrderField.setExchangeID(exchangeId);
		int nRequestID = ++traderRequestId;
		ftdcTraderApi.ReqQryOrder(qryOrderField, nRequestID);
		log.info("Send ReqQryOrder OK -> nRequestID==[{}], BrokerID==[{}], InvestorID==[{}], ExchangeID==[{}]",
				nRequestID, qryOrderField.getBrokerID(), qryOrderField.getInvestorID(), qryOrderField.getExchangeID());
	}

	/**
	 * 查询账户
	 */
	public final void ReqQryTradingAccount() {
		CThostFtdcQryTradingAccountField qryTradingAccountField = new CThostFtdcQryTradingAccountField();
		qryTradingAccountField.setBrokerID(config.getBrokerId());
		qryTradingAccountField.setAccountID(config.getAccountId());
		qryTradingAccountField.setInvestorID(config.getInvestorId());
		qryTradingAccountField.setCurrencyID(config.getCurrencyId());
		int nRequestID = ++traderRequestId;
		ftdcTraderApi.ReqQryTradingAccount(qryTradingAccountField, nRequestID);
		log.info(
				"Send ReqQryTradingAccount OK -> nRequestID==[{}], BrokerID==[{}], "
						+ "AccountID==[{}], InvestorID==[{}], CurrencyID==[{}]",
				nRequestID, qryTradingAccountField.getBrokerID(), qryTradingAccountField.getAccountID(),
				qryTradingAccountField.getInvestorID(), qryTradingAccountField.getCurrencyID());
	}

	/**
	 * 
	 * @param exchangeId
	 * @param instrumentId
	 */
	public final void ReqQryInvestorPosition(String exchangeId, String instrumentId) {
		CThostFtdcQryInvestorPositionField qryInvestorPositionField = new CThostFtdcQryInvestorPositionField();
		qryInvestorPositionField.setBrokerID(config.getBrokerId());
		qryInvestorPositionField.setInvestorID(config.getInvestorId());
		qryInvestorPositionField.setExchangeID(exchangeId);
		qryInvestorPositionField.setInstrumentID(instrumentId);
		int nRequestID = ++traderRequestId;
		ftdcTraderApi.ReqQryInvestorPosition(qryInvestorPositionField, nRequestID);
		log.info(
				"Send ReqQryInvestorPosition OK -> nRequestID==[{}], BrokerID==[{}], "
						+ "InvestorID==[{}], ExchangeID==[{}], InstrumentID==[{}]",
				nRequestID, qryInvestorPositionField.getBrokerID(), qryInvestorPositionField.getInvestorID(),
				qryInvestorPositionField.getExchangeID(), qryInvestorPositionField.getInstrumentID());
	}

	/**
	 * 查询结算信息
	 */
	public final void ReqQrySettlementInfo() {
		CThostFtdcQrySettlementInfoField qrySettlementInfoField = new CThostFtdcQrySettlementInfoField();
		qrySettlementInfoField.setBrokerID(config.getBrokerId());
		qrySettlementInfoField.setInvestorID(config.getInvestorId());
		qrySettlementInfoField.setTradingDay(config.getTradingDay());
		qrySettlementInfoField.setAccountID(config.getAccountId());
		qrySettlementInfoField.setCurrencyID(config.getCurrencyId());
		int nRequestID = ++traderRequestId;
		ftdcTraderApi.ReqQrySettlementInfo(qrySettlementInfoField, nRequestID);
		log.info("Send ReqQrySettlementInfo OK -> nRequestID==[{}]", nRequestID);
	}

	/**
	 * 查询交易标的
	 * 
	 * @param exchangeId
	 * @param instrumentId
	 */
	public final void ReqQryInstrument(String exchangeId, String instrumentId) {
		CThostFtdcQryInstrumentField qryInstrument = new CThostFtdcQryInstrumentField();
		int nRequestID = ++traderRequestId;
		qryInstrument.setExchangeID(exchangeId);
		qryInstrument.setInstrumentID(instrumentId);
		ftdcTraderApi.ReqQryInstrument(qryInstrument, nRequestID);
		log.info("Send ReqQryInstrument OK -> nRequestID==[{}], ExchangeID==[{}], InstrumentID==[{}]", nRequestID,
				qryInstrument.getExchangeID(), qryInstrument.getInstrumentID());
	}

	/**
	 * 
	 * FTDC错误消息处理, 行情接口与交易接口通用
	 * 
	 * @author yellow013
	 *
	 */
	class FtdcErrorHook {
		/**
		 * 错误推送回调
		 * 
		 * @param rspInfoField
		 */
		void onRspError(CThostFtdcRspInfoField rspInfoField) {
			log.error("FtdcGateway onRspError -> ErrorID==[{}], ErrorMsg==[{}]", rspInfoField.getErrorID(),
					rspInfoField.getErrorMsg());
		}
	}

	/**
	 * 行情相关回调
	 * 
	 * @author yellow013
	 *
	 */
	class FtdcMdHook extends FtdcErrorHook {

		/**
		 * 行情前置断开回调
		 */
		void onMdFrontDisconnected() {
			log.warn("FtdcMdHook onMdFrontDisconnected");
			// 行情断开处理逻辑
			isMdLogin = false;
			queue.enqueue(new RspMsg(new FtdcMdConnect(isMdLogin)));
		}

		/**
		 * 行情前置连接回调
		 */
		void onMdFrontConnected() {
			log.info("FtdcMdHook onMdFrontConnected");
			// this.isMdConnect = true;
			CThostFtdcReqUserLoginField userLoginField = new CThostFtdcReqUserLoginField();
			userLoginField.setBrokerID(config.getBrokerId());
			userLoginField.setUserID(config.getUserId());
			userLoginField.setPassword(config.getPassword());
			userLoginField.setClientIPAddress(config.getIpAddr());
			userLoginField.setMacAddress(config.getMacAddr());
			int nRequestID = ++mdRequestId;
			ftdcMdApi.ReqUserLogin(userLoginField, nRequestID);
			log.info("Send Md ReqUserLogin OK -> nRequestID==[{}]", nRequestID);
		}

		/**
		 * 行情登录回调
		 * 
		 * @param rspUserLogin
		 */
		void onMdRspUserLogin(CThostFtdcRspUserLoginField rspUserLoginField) {
			log.info("FtdcMdHook onMdRspUserLogin -> FrontID==[{}], SessionID==[{}], TradingDay==[{}]",
					rspUserLoginField.getFrontID(), rspUserLoginField.getSessionID(),
					rspUserLoginField.getTradingDay());
			isMdLogin = true;
			queue.enqueue(new RspMsg(new FtdcMdConnect(isMdLogin)));
		}

		/**
		 * 订阅行情回调
		 * 
		 * @param specificInstrument
		 */
		void onRspSubMarketData(CThostFtdcSpecificInstrumentField specificInstrumentField) {
			log.info("FtdcMdHook onRspSubMarketData -> InstrumentCode==[{}]",
					specificInstrumentField.getInstrumentID());
		}

		private CThostFtdcDepthMarketDataConverter depthMarketDataConverter = new CThostFtdcDepthMarketDataConverter();

		/**
		 * 行情推送回调
		 * 
		 * @param depthMarketData
		 */
		void onRtnDepthMarketData(CThostFtdcDepthMarketDataField depthMarketDataField) {
			log.debug("Gateway onRtnDepthMarketData -> InstrumentID == [{}], UpdateTime==[{}], UpdateMillisec==[{}]",
					depthMarketDataField.getInstrumentID(), depthMarketDataField.getUpdateTime(),
					depthMarketDataField.getUpdateMillisec());
			queue.enqueue(new RspMsg(depthMarketDataConverter.apply(depthMarketDataField)));
		}

	}

	/**
	 * 
	 * @author yellow013
	 *
	 */
	class FtdcTraderHook extends FtdcErrorHook {

		/**
		 * 交易前置断开回调
		 */
		void onTraderFrontDisconnected() {
			log.warn("FtdcTraderHook onTraderFrontDisconnected");
			isTraderLogin = false;
			isAuthenticate = false;
			// 交易前置断开处理
			queue.enqueue(
					new RspMsg(new FtdcTraderConnect(isTraderLogin).setFrontID(frontID).setSessionID(sessionID)));
		}

		/**
		 * 交易前置机连接回调
		 */
		void onTraderFrontConnected() {
			log.info("FtdcTraderHook onTraderFrontConnected");
			if (StringSupport.nonEmpty(config.getAuthCode()) && !isAuthenticate) {
				// 发送认证请求
				CThostFtdcReqAuthenticateField authenticateField = new CThostFtdcReqAuthenticateField();
				authenticateField.setAppID(config.getAppId());
				authenticateField.setUserID(config.getUserId());
				authenticateField.setBrokerID(config.getBrokerId());
				authenticateField.setAuthCode(config.getAuthCode());
				int nRequestID = ++traderRequestId;
				ftdcTraderApi.ReqAuthenticate(authenticateField, nRequestID);
				log.info(
						"Send ReqAuthenticate OK -> nRequestID==[{}], BrokerID==[{}], UserID==[{}], AppID==[{}], AuthCode==[{}]",
						nRequestID, authenticateField.getBrokerID(), authenticateField.getUserID(),
						authenticateField.getAppID(), authenticateField.getAuthCode());
			} else {
				log.error("Unable to send ReqAuthenticate, authCode==[{}], isAuthenticate==[{}]",
						config.getAuthCode(), isAuthenticate);
			}
		}

		/**
		 * 认证回调
		 * 
		 * @param rspAuthenticateField
		 */
		void onRspAuthenticate(CThostFtdcRspAuthenticateField rspAuthenticateField) {
			isAuthenticate = true;
			CThostFtdcReqUserLoginField reqUserLoginField = new CThostFtdcReqUserLoginField();
			reqUserLoginField.setBrokerID(config.getBrokerId());
			reqUserLoginField.setUserID(config.getUserId());
			reqUserLoginField.setPassword(config.getPassword());
//			reqUserLoginField.setClientIPAddress(ftdcConfig.getIpAddr());
//			reqUserLoginField.setMacAddress(ftdcConfig.getMacAddr());
			int nRequestID = ++traderRequestId;
			ftdcTraderApi.ReqUserLogin(reqUserLoginField, nRequestID);
			log.info("Send Trader ReqUserLogin OK -> nRequestID == {}", nRequestID);
		}

		/**
		 * 交易登录回调
		 * 
		 * @param rspUserLoginField
		 */
		void onTraderRspUserLogin(CThostFtdcRspUserLoginField rspUserLoginField) {
			log.info(
					"FtdcTraderHook onTraderRspUserLogin -> Brokerid==[{}], UserID==[{}], LoginTime==[{}], MaxOrderRef==[{}]",
					rspUserLoginField.getBrokerID(), rspUserLoginField.getUserID(), rspUserLoginField.getLoginTime(),
					rspUserLoginField.getMaxOrderRef());
			frontID = rspUserLoginField.getFrontID();
			sessionID = rspUserLoginField.getSessionID();
			isTraderLogin = true;
			queue.enqueue(
					new RspMsg(new FtdcTraderConnect(isTraderLogin).setFrontID(frontID).setSessionID(sessionID)));
		}

		// 转换为FtdcInputOrder
		private CThostFtdcInputOrderConverter ftdcInputOrderConverter = new CThostFtdcInputOrderConverter();

		/**
		 * 报单回调
		 * 
		 * @param inputOrderField
		 */
		void onRspOrderInsert(CThostFtdcInputOrderField inputOrderField) {
			log.info("FtdcTraderHook onRspOrderInsert -> OrderRef==[{}]", inputOrderField.getOrderRef());
			queue.enqueue(new RspMsg(ftdcInputOrderConverter.apply(inputOrderField)));
		}

		/**
		 * 报单错误回调
		 * 
		 * @param inputOrderField
		 */
		void onErrRtnOrderInsert(CThostFtdcInputOrderField inputOrderField) {
			log.info("FtdcTraderHook onErrRtnOrderInsert -> OrderRef==[{}]", inputOrderField.getOrderRef());
			queue.enqueue(new RspMsg(ftdcInputOrderConverter.apply(inputOrderField)));
		}

		// 转换为FtdcOrder
		private CThostFtdcOrderConverter ftdcOrderConverter = new CThostFtdcOrderConverter();

		/**
		 * 报单推送
		 * 
		 * @param orderField
		 */
		void onRtnOrder(CThostFtdcOrderField orderField) {
			log.info(
					"FtdcTraderHook onRtnOrder -> AccountID==[{}], OrderRef==[{}], OrderSysID==[{}], InstrumentID==[{}], "
							+ "OrderStatus==[{}], Direction==[{}], VolumeTotalOriginal==[{}], LimitPrice==[{}]",
					orderField.getAccountID(), orderField.getOrderRef(), orderField.getOrderSysID(),
					orderField.getInstrumentID(), orderField.getOrderStatus(), orderField.getDirection(),
					orderField.getVolumeTotalOriginal(), orderField.getLimitPrice());
			queue.enqueue(new RspMsg(ftdcOrderConverter.apply(orderField), true));
		}

		/**
		 * 
		 * @param orderField
		 * @param isLast
		 */
		void onRspQryOrder(CThostFtdcOrderField orderField, boolean isLast) {
			log.info("FtdcTraderHook onRspQryOrder -> AccountID==[{}], OrderRef==[{}], isLast==[{}]",
					orderField.getAccountID(), orderField.getOrderRef(), isLast);
			queue.enqueue(new RspMsg(ftdcOrderConverter.apply(orderField), isLast));
		}

		// 转换为FtdcTrade
		private CThostFtdcTradeConverter ftdcTradeConverter = new CThostFtdcTradeConverter();

		/**
		 * 成交推送
		 * 
		 * @param tradeField
		 */
		void onRtnTrade(CThostFtdcTradeField tradeField) {
			log.info(
					"FtdcTraderHook onRtnTrade -> OrderRef==[{}], OrderSysID==[{}], InstrumentID==[{}], "
							+ "Direction==[{}], Price==[{}], Volume==[{}]",
					tradeField.getOrderRef(), tradeField.getOrderSysID(), tradeField.getInstrumentID(),
					tradeField.getDirection(), tradeField.getPrice(), tradeField.getVolume());
			queue.enqueue(new RspMsg(ftdcTradeConverter.apply(tradeField)));
		}

		private CThostFtdcInputOrderActionConverter ftdcInputOrderActionConverter = new CThostFtdcInputOrderActionConverter();

		/**
		 * 撤单错误回调: 1
		 * 
		 * @param inputOrderActionField
		 */
		void onRspOrderAction(CThostFtdcInputOrderActionField inputOrderActionField) {
			log.info(
					"FtdcTraderHook onRspOrderAction -> OrderRef==[{}], OrderSysID==[{}], OrderActionRef==[{}], InstrumentID==[{}]",
					inputOrderActionField.getOrderRef(), inputOrderActionField.getOrderSysID(),
					inputOrderActionField.getOrderActionRef(), inputOrderActionField.getInstrumentID());
			queue.enqueue(new RspMsg(ftdcInputOrderActionConverter.apply(inputOrderActionField)));
		}

		private CThostFtdcOrderActionConverter ftdcOrderActionConverter = new CThostFtdcOrderActionConverter();

		/**
		 * 撤单错误回调: 2
		 * 
		 * @param orderActionField
		 */
		void onErrRtnOrderAction(CThostFtdcOrderActionField orderActionField) {
			log.info(
					"FtdcTraderHook onErrRtnOrderAction -> OrderRef==[{}], OrderSysID==[{}], OrderActionRef==[{}], InstrumentID==[{}]",
					orderActionField.getOrderRef(), orderActionField.getOrderSysID(),
					orderActionField.getOrderActionRef(), orderActionField.getInstrumentID());
			queue.enqueue(new RspMsg(ftdcOrderActionConverter.apply(orderActionField)));
		}

		/**
		 * 
		 * @param tradingAccountField
		 * @param isLast
		 */
		void onQryTradingAccount(CThostFtdcTradingAccountField tradingAccountField, boolean isLast) {
			log.info(
					"FtdcTraderHook onQryTradingAccount -> AccountID==[{}], Balance==[{}], "
							+ "Available==[{}], Credit==[{}], WithdrawQuota==[{}], isLast==[{}]",
					tradingAccountField.getAccountID(), tradingAccountField.getBalance(),
					tradingAccountField.getAvailable(), tradingAccountField.getCredit(),
					tradingAccountField.getWithdrawQuota(), isLast);
			// TODO Inbound

		}

		private CThostFtdcInvestorPositionConverter investorPositionConverter = new CThostFtdcInvestorPositionConverter();

		/**
		 * 
		 * @param investorPositionField
		 * @param isLast
		 */
		void onRspQryInvestorPosition(CThostFtdcInvestorPositionField investorPositionField, boolean isLast) {
			log.info(
					"FtdcTraderHook onRspQryInvestorPosition -> InvestorID==[{}], ExchangeID==[{}], "
							+ "InstrumentID==[{}], Position==[{}], isLast==[{}]",
					investorPositionField.getInvestorID(), investorPositionField.getExchangeID(),
					investorPositionField.getInstrumentID(), investorPositionField.getPosition(), isLast);
			queue.enqueue(new RspMsg(investorPositionConverter.apply(investorPositionField), isLast));
		}

	}

	@Override
	public void close() throws IOException {
		Threads.startNewThread("FtdcTraderApi-Release", ftdcTraderApi::Release);
		SleepSupport.sleep(500);
		Threads.startNewThread("FtdcMdApi-Release", ftdcMdApi::Release);
		SleepSupport.sleep(500);
	}

}