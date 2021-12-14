package io.horizon.ctp.gateway;

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
import io.horizon.ctp.adaptor.CtpConfig;
import io.horizon.ctp.gateway.converter.CThostFtdcDepthMarketDataConverter;
import io.horizon.ctp.gateway.converter.CThostFtdcInputOrderActionConverter;
import io.horizon.ctp.gateway.converter.CThostFtdcInputOrderConverter;
import io.horizon.ctp.gateway.converter.CThostFtdcInvestorPositionConverter;
import io.horizon.ctp.gateway.converter.CThostFtdcOrderActionConverter;
import io.horizon.ctp.gateway.converter.CThostFtdcOrderConverter;
import io.horizon.ctp.gateway.converter.CThostFtdcTradeConverter;
import io.horizon.ctp.gateway.rsp.FtdcMdConnect;
import io.horizon.ctp.gateway.rsp.FtdcRspInfo;
import io.horizon.ctp.gateway.rsp.FtdcTraderConnect;
import io.mercury.common.annotation.thread.MustBeThreadSafe;
import io.mercury.common.datetime.DateTimeUtil;
import io.mercury.common.file.FileUtil;
import io.mercury.common.functional.Handler;
import io.mercury.common.lang.Assertor;
import io.mercury.common.lang.exception.NativeLibraryLoadException;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.thread.SleepSupport;
import io.mercury.common.thread.Threads;
import io.mercury.common.util.StringSupport;

@NotThreadSafe
public final class CtpGateway implements Closeable {

	private static final Logger log = CommonLoggerFactory.getLogger(CtpGateway.class);

	// 静态加载FtdcLibrary
	static {
		try {
			CtpLibraryLoader.loadLibrary();
		} catch (NativeLibraryLoadException e) {
			log.error(e.getMessage(), e);
			log.error("CTP native library file loading error, System must exit. status -1");
			System.exit(-1);
		}
	}

	// gatewayId
	private final String gatewayId;

	// 基础配置信息
	private final CtpConfig config;

	@Native
	private CThostFtdcMdApi mdApi;
	@Native
	private CThostFtdcTraderApi traderApi;

	// 是否已初始化
	private AtomicBoolean isInitialize = new AtomicBoolean(false);

	// 是否登陆行情接口
	private volatile boolean isMdLogin;
	// 是否登陆交易接口
	private volatile boolean isTraderLogin;
	// 是否已认证
	private volatile boolean isAuthenticate;

	// 交易前置号
	private volatile int frontID;
	// 交易会话号
	private volatile int sessionID;

	// 行情请求ID
	private volatile int mdRequestId = -1;
	// 交易请求ID
	private volatile int traderRequestId = -1;

	// RSP消息处理器
	private final Handler<FtdcRspMsg> handler;

	public CtpGateway(@Nonnull String gatewayId, @Nonnull CtpConfig config,
			@MustBeThreadSafe @Nonnull Handler<FtdcRspMsg> handler) {
		Assertor.nonEmpty(gatewayId, "gatewayId");
		Assertor.nonNull(config, "config");
		Assertor.nonNull(handler, "handler");
		this.gatewayId = gatewayId;
		this.config = config;
		this.handler = handler;
	}

	/**
	 * 创建CTP数据文件目录
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
		this.mdApi = CThostFtdcMdApi.CreateFtdcMdApi(mdTempFilePath);
		// 创建mdSpi
		CThostFtdcMdSpi mdSpi = new FtdcMdSpiImpl(new FtdcMdHook());
		// 将mdSpi注册到mdApi
		mdApi.RegisterSpi(mdSpi);
		// 注册到md前置机
		mdApi.RegisterFront(config.getMdAddr());
		// 初始化mdApi
		log.info("Call native function mdApi.Init()...");
		mdApi.Init();
		// 阻塞当前线程
		log.info("Call native function mdApi.Join()...");
		mdApi.Join();
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
		this.traderApi = CThostFtdcTraderApi.CreateFtdcTraderApi(traderTempFilePath);
		// 创建traderSpi
		CThostFtdcTraderSpi traderSpi = new FtdcTraderSpiImpl(new FtdcTraderHook());
		// 将traderSpi注册到traderApi
		traderApi.RegisterSpi(traderSpi);
		// 注册到trader前置机
		traderApi.RegisterFront(config.getTraderAddr());
		/// THOST_TERT_RESTART:从本交易日开始重传
		/// THOST_TERT_RESUME:从上次收到的续传
		/// THOST_TERT_QUICK:只传送登录后私有流的内容
		// 订阅公有流和私有流
		traderApi.SubscribePublicTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESUME);
		traderApi.SubscribePrivateTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESUME);
		// 初始化traderApi
		log.info("Call native function traderApi.Init()...");
		traderApi.Init();
		// 阻塞当前线程
		log.info("Call native function traderApi.Join()...");
		traderApi.Join();
	}

	/**
	 * 行情订阅接口
	 * 
	 * @param instruements
	 */
	public final void SubscribeMarketData(@Nonnull String[] instruements) {
		if (isMdLogin) {
			mdApi.SubscribeMarketData(instruements, instruements.length);
			log.info("Send SubscribeMarketData -> count==[{}]", instruements.length);
		} else
			log.warn("Cannot SubscribeMarketData -> isMdLogin == [false]");
	}

	/**
	 * 报单接口
	 * 
	 * @param field
	 */
	public final void ReqOrderInsert(CThostFtdcInputOrderField field) {
		if (isTraderLogin) {
			// 设置账号信息
			int nRequestID = ++traderRequestId;
			traderApi.ReqOrderInsert(field, nRequestID);
			log.info(
					"Send ReqOrderInsert OK ->  nRequestID==[{}], OrderRef==[{}], InstrumentID==[{}], "
							+ "CombOffsetFlag==[{}], Direction==[{}], VolumeTotalOriginal==[{}], LimitPrice==[{}]",
					nRequestID, field.getOrderRef(), field.getInstrumentID(), field.getCombOffsetFlag(),
					field.getDirection(), field.getVolumeTotalOriginal(), field.getLimitPrice());
		} else
			log.error("ReqOrderInsert error :: TraderApi is not login");
	}

	/**
	 * 撤单请求
	 * 
	 * @param field
	 */
	public final void ReqOrderAction(CThostFtdcInputOrderActionField field) {
		if (isTraderLogin) {
			int nRequestID = ++traderRequestId;
			traderApi.ReqOrderAction(field, nRequestID);
			log.info(
					"Send ReqOrderAction OK -> nRequestID==[{}], OrderRef==[{}], OrderActionRef==[{}], "
							+ "BrokerID==[{}], InvestorID==[{}], InstrumentID==[{}]",
					nRequestID, field.getOrderRef(), field.getOrderActionRef(), field.getBrokerID(),
					field.getInvestorID(), field.getInstrumentID());
		} else
			log.error("ReqOrderAction error :: TraderApi is not login");
	}

	/**
	 * 查询订单
	 * 
	 * @param exchangeId
	 */
	public final void ReqQryOrder(String exchangeId) {
		CThostFtdcQryOrderField field = new CThostFtdcQryOrderField();
		field.setBrokerID(config.getBrokerId());
		field.setInvestorID(config.getInvestorId());
		field.setExchangeID(exchangeId);
		int nRequestID = ++traderRequestId;
		traderApi.ReqQryOrder(field, nRequestID);
		log.info("Send ReqQryOrder OK -> nRequestID==[{}], BrokerID==[{}], InvestorID==[{}], ExchangeID==[{}]",
				nRequestID, field.getBrokerID(), field.getInvestorID(), field.getExchangeID());
	}

	/**
	 * 查询账户
	 */
	public final void ReqQryTradingAccount() {
		CThostFtdcQryTradingAccountField field = new CThostFtdcQryTradingAccountField();
		field.setBrokerID(config.getBrokerId());
		field.setAccountID(config.getAccountId());
		field.setInvestorID(config.getInvestorId());
		field.setCurrencyID(config.getCurrencyId());
		int nRequestID = ++traderRequestId;
		traderApi.ReqQryTradingAccount(field, nRequestID);
		log.info(
				"Send ReqQryTradingAccount OK -> nRequestID==[{}], BrokerID==[{}], "
						+ "AccountID==[{}], InvestorID==[{}], CurrencyID==[{}]",
				nRequestID, field.getBrokerID(), field.getAccountID(), field.getInvestorID(), field.getCurrencyID());
	}

	/**
	 * 
	 * @param exchangeId
	 * @param instrumentId
	 */
	public final void ReqQryInvestorPosition(String exchangeId, String instrumentId) {
		CThostFtdcQryInvestorPositionField field = new CThostFtdcQryInvestorPositionField();
		field.setBrokerID(config.getBrokerId());
		field.setInvestorID(config.getInvestorId());
		field.setExchangeID(exchangeId);
		field.setInstrumentID(instrumentId);
		int nRequestID = ++traderRequestId;
		traderApi.ReqQryInvestorPosition(field, nRequestID);
		log.info(
				"Send ReqQryInvestorPosition OK -> nRequestID==[{}], BrokerID==[{}], "
						+ "InvestorID==[{}], ExchangeID==[{}], InstrumentID==[{}]",
				nRequestID, field.getBrokerID(), field.getInvestorID(), field.getExchangeID(), field.getInstrumentID());
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
		traderApi.ReqQrySettlementInfo(qrySettlementInfoField, nRequestID);
		log.info("Send ReqQrySettlementInfo OK -> nRequestID==[{}]", nRequestID);
	}

	/**
	 * 查询交易标的
	 * 
	 * @param exchangeId
	 * @param instrumentId
	 */
	public final void ReqQryInstrument(String exchangeId, String instrumentId) {
		CThostFtdcQryInstrumentField field = new CThostFtdcQryInstrumentField();
		int nRequestID = ++traderRequestId;
		field.setExchangeID(exchangeId);
		field.setInstrumentID(instrumentId);
		traderApi.ReqQryInstrument(field, nRequestID);
		log.info("Send ReqQryInstrument OK -> nRequestID==[{}], ExchangeID==[{}], InstrumentID==[{}]", nRequestID,
				field.getExchangeID(), field.getInstrumentID());
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
		 * @param field
		 */
		void onRspError(CThostFtdcRspInfoField field) {
			log.error("CtpGateway onRspError -> ErrorID==[{}], ErrorMsg==[{}]", field.getErrorID(),
					field.getErrorMsg());
			handler.handle(
					new FtdcRspMsg(new FtdcRspInfo().setErrorID(field.getErrorID()).setErrorMsg(field.getErrorMsg())));
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
			handler.handle(new FtdcRspMsg(new FtdcMdConnect(isMdLogin)));
		}

		/**
		 * 行情前置连接回调
		 */
		void onMdFrontConnected() {
			log.info("FtdcMdHook onMdFrontConnected");
			// this.isMdConnect = true;
			CThostFtdcReqUserLoginField field = new CThostFtdcReqUserLoginField();
			field.setBrokerID(config.getBrokerId());
			field.setUserID(config.getUserId());
			field.setPassword(config.getPassword());
			field.setClientIPAddress(config.getIpAddr());
			field.setMacAddress(config.getMacAddr());
			int nRequestID = ++mdRequestId;
			mdApi.ReqUserLogin(field, nRequestID);
			log.info("Send Md ReqUserLogin OK -> nRequestID==[{}]", nRequestID);
		}

		/**
		 * 行情登录回调
		 * 
		 * @param rspUserLogin
		 */
		void onMdRspUserLogin(CThostFtdcRspUserLoginField field) {
			log.info("FtdcMdHook onMdRspUserLogin -> FrontID==[{}], SessionID==[{}], TradingDay==[{}]",
					field.getFrontID(), field.getSessionID(), field.getTradingDay());
			isMdLogin = true;
			handler.handle(new FtdcRspMsg(new FtdcMdConnect(isMdLogin)));
		}

		/**
		 * 订阅行情回调
		 * 
		 * @param specificInstrument
		 */
		void onRspSubMarketData(CThostFtdcSpecificInstrumentField field) {
			log.info("FtdcMdHook onRspSubMarketData -> InstrumentCode==[{}]", field.getInstrumentID());
		}

		private CThostFtdcDepthMarketDataConverter depthMarketDataConverter = new CThostFtdcDepthMarketDataConverter();

		/**
		 * 行情推送回调
		 * 
		 * @param depthMarketData
		 */
		void onRtnDepthMarketData(CThostFtdcDepthMarketDataField field) {
			log.debug("Gateway onRtnDepthMarketData -> InstrumentID == [{}], UpdateTime==[{}], UpdateMillisec==[{}]",
					field.getInstrumentID(), field.getUpdateTime(), field.getUpdateMillisec());
			handler.handle(new FtdcRspMsg(depthMarketDataConverter.apply(field)));
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
			handler.handle(
					new FtdcRspMsg(new FtdcTraderConnect(isTraderLogin).setFrontID(frontID).setSessionID(sessionID)));
		}

		/**
		 * 交易前置机连接回调
		 */
		void onTraderFrontConnected() {
			log.info("FtdcTraderHook onTraderFrontConnected");
			if (StringSupport.nonEmpty(config.getAuthCode()) && !isAuthenticate) {
				// 发送认证请求
				CThostFtdcReqAuthenticateField field = new CThostFtdcReqAuthenticateField();
				field.setAppID(config.getAppId());
				field.setUserID(config.getUserId());
				field.setBrokerID(config.getBrokerId());
				field.setAuthCode(config.getAuthCode());
				int nRequestID = ++traderRequestId;
				traderApi.ReqAuthenticate(field, nRequestID);
				log.info(
						"Send Trader ReqAuthenticate OK -> nRequestID==[{}], BrokerID==[{}], UserID==[{}], AppID==[{}], AuthCode==[{}]",
						nRequestID, field.getBrokerID(), field.getUserID(), field.getAppID(), field.getAuthCode());
			} else
				log.error("Unable to send ReqAuthenticate, authCode==[{}], isAuthenticate==[{}]", config.getAuthCode(),
						isAuthenticate);
		}

		/**
		 * 认证回调
		 * 
		 * @param field
		 */
		void onRspAuthenticate(CThostFtdcRspAuthenticateField field) {
			isAuthenticate = true;
			CThostFtdcReqUserLoginField loginField = new CThostFtdcReqUserLoginField();
			loginField.setBrokerID(config.getBrokerId());
			loginField.setUserID(config.getUserId());
			loginField.setPassword(config.getPassword());
			loginField.setClientIPAddress(config.getIpAddr());
			loginField.setMacAddress(config.getMacAddr());
			int nRequestID = ++traderRequestId;
			traderApi.ReqUserLogin(loginField, nRequestID);
			log.info("Send Trader ReqUserLogin OK -> nRequestID==[{}]", nRequestID);
		}

		/**
		 * 交易登录回调
		 * 
		 * @param field
		 */
		void onTraderRspUserLogin(CThostFtdcRspUserLoginField field) {
			log.info(
					"FtdcTraderHook onTraderRspUserLogin -> Brokerid==[{}], UserID==[{}], LoginTime==[{}], MaxOrderRef==[{}]",
					field.getBrokerID(), field.getUserID(), field.getLoginTime(), field.getMaxOrderRef());
			frontID = field.getFrontID();
			sessionID = field.getSessionID();
			isTraderLogin = true;
			handler.handle(
					new FtdcRspMsg(new FtdcTraderConnect(isTraderLogin).setFrontID(frontID).setSessionID(sessionID)));
		}

		// 转换为FtdcInputOrder
		private CThostFtdcInputOrderConverter inputOrderConverter = new CThostFtdcInputOrderConverter();

		/**
		 * 报单回调
		 * 
		 * @param field
		 */
		void onRspOrderInsert(CThostFtdcInputOrderField field) {
			log.info("FtdcTraderHook onRspOrderInsert -> OrderRef==[{}]", field.getOrderRef());
			handler.handle(new FtdcRspMsg(inputOrderConverter.apply(field)));
		}

		/**
		 * 报单错误回调
		 * 
		 * @param field
		 */
		void onErrRtnOrderInsert(CThostFtdcInputOrderField field) {
			log.info("FtdcTraderHook onErrRtnOrderInsert -> OrderRef==[{}]", field.getOrderRef());
			
			handler.handle(new FtdcRspMsg(inputOrderConverter.apply(field)));
		}

		// 转换为FtdcOrder
		private CThostFtdcOrderConverter orderConverter = new CThostFtdcOrderConverter();

		/**
		 * 报单推送
		 * 
		 * @param field
		 */
		void onRtnOrder(CThostFtdcOrderField field) {
			log.info(
					"FtdcTraderHook onRtnOrder -> AccountID==[{}], OrderRef==[{}], OrderSysID==[{}], InstrumentID==[{}], "
							+ "OrderStatus==[{}], Direction==[{}], VolumeTotalOriginal==[{}], LimitPrice==[{}]",
					field.getAccountID(), field.getOrderRef(), field.getOrderSysID(), field.getInstrumentID(),
					field.getOrderStatus(), field.getDirection(), field.getVolumeTotalOriginal(),
					field.getLimitPrice());
			handler.handle(new FtdcRspMsg(orderConverter.apply(field), true));
		}

		/**
		 * 
		 * @param field
		 * @param isLast
		 */
		void onRspQryOrder(CThostFtdcOrderField field, boolean isLast) {
			log.info("FtdcTraderHook onRspQryOrder -> AccountID==[{}], OrderRef==[{}], isLast==[{}]",
					field.getAccountID(), field.getOrderRef(), isLast);
			handler.handle(new FtdcRspMsg(orderConverter.apply(field), isLast));
		}

		// 转换为FtdcTrade
		private CThostFtdcTradeConverter tradeConverter = new CThostFtdcTradeConverter();

		/**
		 * 成交推送
		 * 
		 * @param field
		 */
		void onRtnTrade(CThostFtdcTradeField field) {
			log.info(
					"FtdcTraderHook onRtnTrade -> OrderRef==[{}], OrderSysID==[{}], InstrumentID==[{}], "
							+ "Direction==[{}], Price==[{}], Volume==[{}]",
					field.getOrderRef(), field.getOrderSysID(), field.getInstrumentID(), field.getDirection(),
					field.getPrice(), field.getVolume());
			handler.handle(new FtdcRspMsg(tradeConverter.apply(field)));
		}

		private CThostFtdcInputOrderActionConverter inputOrderActionConverter = new CThostFtdcInputOrderActionConverter();

		/**
		 * 撤单错误回调: 1
		 * 
		 * @param field
		 */
		void onRspOrderAction(CThostFtdcInputOrderActionField field) {
			log.info(
					"FtdcTraderHook onRspOrderAction -> OrderRef==[{}], OrderSysID==[{}], OrderActionRef==[{}], InstrumentID==[{}]",
					field.getOrderRef(), field.getOrderSysID(), field.getOrderActionRef(), field.getInstrumentID());
			handler.handle(new FtdcRspMsg(inputOrderActionConverter.apply(field)));
		}

		private CThostFtdcOrderActionConverter orderActionConverter = new CThostFtdcOrderActionConverter();

		/**
		 * 撤单错误回调: 2
		 * 
		 * @param field
		 */
		void onErrRtnOrderAction(CThostFtdcOrderActionField field) {
			log.info(
					"FtdcTraderHook onErrRtnOrderAction -> OrderRef==[{}], OrderSysID==[{}], OrderActionRef==[{}], InstrumentID==[{}]",
					field.getOrderRef(), field.getOrderSysID(), field.getOrderActionRef(), field.getInstrumentID());
			handler.handle(new FtdcRspMsg(orderActionConverter.apply(field)));
		}

		/**
		 * 
		 * @param field
		 * @param isLast
		 */
		void onQryTradingAccount(CThostFtdcTradingAccountField field, boolean isLast) {
			log.info(
					"FtdcTraderHook onQryTradingAccount -> AccountID==[{}], Balance==[{}], "
							+ "Available==[{}], Credit==[{}], WithdrawQuota==[{}], isLast==[{}]",
					field.getAccountID(), field.getBalance(), field.getAvailable(), field.getCredit(),
					field.getWithdrawQuota(), isLast);
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
			handler.handle(new FtdcRspMsg(investorPositionConverter.apply(investorPositionField), isLast));
		}

	}

	@Override
	public void close() throws IOException {
		Threads.startNewThread("FtdcTraderApi-Release", traderApi::Release);
		SleepSupport.sleep(500);
		Threads.startNewThread("FtdcMdApi-Release", mdApi::Release);
		SleepSupport.sleep(500);
	}

}