package io.horizon.ftdc.gateway;

import static io.mercury.common.thread.Threads.sleep;
import static io.mercury.common.thread.Threads.startNewMaxPriorityThread;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Native;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.collections.api.set.MutableSet;
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
import io.horizon.ftdc.gateway.bean.FtdcMdConnect;
import io.horizon.ftdc.gateway.bean.FtdcTraderConnect;
import io.horizon.ftdc.gateway.converter.FromCThostFtdcDepthMarketData;
import io.horizon.ftdc.gateway.converter.FromCThostFtdcInputOrder;
import io.horizon.ftdc.gateway.converter.FromCThostFtdcInputOrderAction;
import io.horizon.ftdc.gateway.converter.FromCThostFtdcInvestorPosition;
import io.horizon.ftdc.gateway.converter.FromCThostFtdcOrder;
import io.horizon.ftdc.gateway.converter.FromCThostFtdcOrderAction;
import io.horizon.ftdc.gateway.converter.FromCThostFtdcTrade;
import io.mercury.common.collections.MutableSets;
import io.mercury.common.concurrent.queue.Queue;
import io.mercury.common.datetime.DateTimeUtil;
import io.mercury.common.file.Files;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.thread.Threads;
import io.mercury.common.util.Assertor;
import io.mercury.common.util.StringUtil;

@NotThreadSafe
public final class FtdcGateway implements Closeable {

	private static final Logger log = CommonLoggerFactory.getLogger(FtdcGateway.class);

	// 静态加载FtdcLibrary
	static {
		try {
			FtdcLibraryFileLoader.loadLibrary();
		} catch (NativeLibraryLoadException e) {
			log.error(e.getMessage(), e);
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
	private volatile boolean isInitialize = false;

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
	private final Queue<FtdcRspMsg> rspQueue;

	public FtdcGateway(@Nonnull String gatewayId, @Nonnull FtdcConfig config, @Nonnull Queue<FtdcRspMsg> rspQueue) {
		Assertor.nonEmpty(gatewayId, "gatewayId");
		Assertor.nonNull(config, "config");
		Assertor.nonNull(rspQueue, "rspQueue");
		this.gatewayId = gatewayId;
		this.config = config;
		this.rspQueue = rspQueue;
	}

	/**
	 * 创建
	 * 
	 * @return
	 */
	private File generateTempDir() {
		// 创建临时文件存储目录
		File tempDir = Files.mkdirInTmp(gatewayId + "-" + DateTimeUtil.date());
		log.info("Temp file dir is -> {}", tempDir.getAbsolutePath());
		return tempDir;
	}

	/**
	 * 启动并挂起线程
	 */
	public void bootstrap() {
		if (!isInitialize) {
			// 获取临时文件目录
			File tempDir = generateTempDir();
			log.info("TraderApi version -> {}", CThostFtdcTraderApi.GetApiVersion());
			log.info("MdApi version -> {}", CThostFtdcMdApi.GetApiVersion());
			try {
				startNewMaxPriorityThread("Trader-Api-Thread", () -> traderInitAndJoin(tempDir));
				sleep(2000);
				startNewMaxPriorityThread("Md-Api-Thread", () -> mdInitAndJoin(tempDir));
				this.isInitialize = true;
			} catch (Exception e) {
				log.error("Method initAndJoin throw Exception -> {}", e.getMessage(), e);
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
		CThostFtdcMdSpi ftdcMdSpi = new FtdcMdSpiImpl(this);
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
		CThostFtdcTraderSpi ftdcTraderSpi = new FtdcTraderSpiImpl(this);
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
	 ****************************************************************
	 * 以下是行情相关接口与回调
	 */

	/**
	 * 行情前置连接回调
	 */
	void onMdFrontConnected() {
		log.info("Callback onMdFrontConnected");
		// this.isMdConnect = true;
		CThostFtdcReqUserLoginField reqUserLoginField = new CThostFtdcReqUserLoginField();
		reqUserLoginField.setBrokerID(config.getBrokerId());
		reqUserLoginField.setUserID(config.getUserId());
		reqUserLoginField.setPassword(config.getPassword());
		reqUserLoginField.setClientIPAddress(config.getIpAddr());
		reqUserLoginField.setMacAddress(config.getMacAddr());
		int nRequestID = ++mdRequestId;
		ftdcMdApi.ReqUserLogin(reqUserLoginField, nRequestID);
		log.info("Send Md ReqUserLogin OK -> nRequestID==[{}]", nRequestID);
	}

	/**
	 * 行情前置断开回调
	 */
	void onMdFrontDisconnected() {
		log.warn("Callback onMdFrontDisconnected");
		// 行情断开处理逻辑
		this.isMdLogin = false;
		rspQueue.enqueue(new FtdcRspMsg(new FtdcMdConnect(isMdLogin)));
	}

	/**
	 * 行情登录回调
	 * 
	 * @param rspUserLogin
	 */
	void onMdRspUserLogin(CThostFtdcRspUserLoginField rspUserLoginField) {
		log.info("Callback onMdRspUserLogin -> FrontID==[{}], SessionID==[{}], TradingDay==[{}]",
				rspUserLoginField.getFrontID(), rspUserLoginField.getSessionID(), rspUserLoginField.getTradingDay());
		this.isMdLogin = true;
		rspQueue.enqueue(new FtdcRspMsg(new FtdcMdConnect(isMdLogin)));
	}

	/**
	 * 存储订阅合约
	 */
	private MutableSet<String> subscribedInstruementSet = MutableSets.newUnifiedSet();

	/**
	 * 行情订阅接口
	 * 
	 * @param inputInstruementSet
	 */
	public void SubscribeMarketData(Set<String> instruementSet) {
		subscribedInstruementSet.addAll(instruementSet);
		log.info("Add Subscribe Instruement set -> Count==[{}]", instruementSet.size());
		if (isMdLogin) {
			if (!subscribedInstruementSet.isEmpty()) {
				subscribeMarketData();
			} else {
				log.warn("Cannot SubscribeMarketData -> subscribedInstruementSet.isEmpty() == [true]");
			}
		} else {
			log.warn("Cannot SubscribeMarketData -> isMdLogin == [false]");
		}
	}

	private void subscribeMarketData() {
		String[] instruements = new String[subscribedInstruementSet.size()];
		Iterator<String> iterator = subscribedInstruementSet.iterator();
		for (int i = 0; i < instruements.length; i++) {
			instruements[i] = iterator.next();
			log.info("Add Subscribe Instruement -> instruementCode==[{}]", instruements[i]);
		}
		ftdcMdApi.SubscribeMarketData(instruements, instruements.length);
		subscribedInstruementSet.clear();
		log.info("Send SubscribeMarketData -> count==[{}]", instruements.length);
	}

	/**
	 * 订阅行情回调
	 * 
	 * @param specificInstrument
	 */
	void onRspSubMarketData(CThostFtdcSpecificInstrumentField specificInstrumentField) {
		log.info("Callback onRspSubMarketData -> InstrumentCode==[{}]", specificInstrumentField.getInstrumentID());
	}

	private FromCThostFtdcDepthMarketData fromCThostFtdcDepthMarketData = new FromCThostFtdcDepthMarketData();

	/**
	 * 行情推送回调
	 * 
	 * @param depthMarketData
	 */
	void onRtnDepthMarketData(CThostFtdcDepthMarketDataField depthMarketDataField) {
		log.debug("Gateway onRtnDepthMarketData -> InstrumentID == [{}], UpdateTime==[{}], UpdateMillisec==[{}]",
				depthMarketDataField.getInstrumentID(), depthMarketDataField.getUpdateTime(),
				depthMarketDataField.getUpdateMillisec());
		rspQueue.enqueue(new FtdcRspMsg(fromCThostFtdcDepthMarketData.apply(depthMarketDataField)));
	}

	/**
	 ****************************************************************
	 * 以下是交易相关接口与回调
	 */

	/**
	 * 交易前置机连接回调
	 */
	void onTraderFrontConnected() {
		log.info("Callback onTraderFrontConnected");
		if (StringUtil.nonEmpty(config.getAuthCode()) && !isAuthenticate) {
			// 发送认证请求
			CThostFtdcReqAuthenticateField reqAuthenticateField = new CThostFtdcReqAuthenticateField();
			reqAuthenticateField.setAppID(config.getAppId());
			reqAuthenticateField.setUserID(config.getUserId());
			reqAuthenticateField.setBrokerID(config.getBrokerId());
			reqAuthenticateField.setAuthCode(config.getAuthCode());
			int nRequestID = ++traderRequestId;
			ftdcTraderApi.ReqAuthenticate(reqAuthenticateField, nRequestID);
			log.info(
					"Send ReqAuthenticate OK -> nRequestID==[{}], BrokerID==[{}], UserID==[{}], AppID==[{}], AuthCode==[{}]",
					nRequestID, reqAuthenticateField.getBrokerID(), reqAuthenticateField.getUserID(),
					reqAuthenticateField.getAppID(), reqAuthenticateField.getAuthCode());
		} else {
			log.error("Unable to send ReqAuthenticate, authCode==[{}], isAuthenticate==[{}]", config.getAuthCode(),
					isAuthenticate);
		}
	}

	/**
	 * 交易前置断开回调
	 */
	void onTraderFrontDisconnected() {
		log.warn("Callback onTraderFrontDisconnected");
		this.isTraderLogin = false;
		this.isAuthenticate = false;
		// 交易前置断开处理
		rspQueue.enqueue(new FtdcRspMsg(new FtdcTraderConnect(isTraderLogin).setFrontID(frontID).setSessionID(sessionID)));
	}

	/**
	 * 认证回调
	 * 
	 * @param rspAuthenticateField
	 */
	void onRspAuthenticate(CThostFtdcRspAuthenticateField rspAuthenticateField) {
		this.isAuthenticate = true;
		CThostFtdcReqUserLoginField reqUserLoginField = new CThostFtdcReqUserLoginField();
		reqUserLoginField.setBrokerID(config.getBrokerId());
		reqUserLoginField.setUserID(config.getUserId());
		reqUserLoginField.setPassword(config.getPassword());
//		reqUserLoginField.setClientIPAddress(ftdcConfig.getIpAddr());
//		reqUserLoginField.setMacAddress(ftdcConfig.getMacAddr());
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
		log.info("Callback onTraderRspUserLogin -> Brokerid==[{}], UserID==[{}], LoginTime==[{}], MaxOrderRef==[{}]",
				rspUserLoginField.getBrokerID(), rspUserLoginField.getUserID(), rspUserLoginField.getLoginTime(),
				rspUserLoginField.getMaxOrderRef());
		this.frontID = rspUserLoginField.getFrontID();
		this.sessionID = rspUserLoginField.getSessionID();
		this.isTraderLogin = true;
		rspQueue.enqueue(new FtdcRspMsg(new FtdcTraderConnect(isTraderLogin).setFrontID(frontID).setSessionID(sessionID)));
	}

	/**
	 * 报单接口
	 * 
	 * @param inputOrderField
	 */
	public void ReqOrderInsert(CThostFtdcInputOrderField inputOrderField) {
		if (isTraderLogin) {
			// 设置账号信息
			inputOrderField.setBrokerID(config.getBrokerId());
			inputOrderField.setInvestorID(config.getInvestorId());
			inputOrderField.setAccountID(config.getAccountId());
			inputOrderField.setUserID(config.getUserId());
			inputOrderField.setIPAddress(config.getIpAddr());
			inputOrderField.setMacAddress(config.getMacAddr());
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

	private FromCThostFtdcInputOrder fromCThostFtdcInputOrder = new FromCThostFtdcInputOrder();

	/**
	 * 报单回调
	 * 
	 * @param inputOrderField
	 */
	void onRspOrderInsert(CThostFtdcInputOrderField inputOrderField) {
		log.info("Callback onRspOrderInsert -> OrderRef==[{}]", inputOrderField.getOrderRef());
		rspQueue.enqueue(new FtdcRspMsg(fromCThostFtdcInputOrder.apply(inputOrderField)));
	}

	/**
	 * 报单错误回调
	 * 
	 * @param inputOrderField
	 */
	void onErrRtnOrderInsert(CThostFtdcInputOrderField inputOrderField) {
		log.info("Callback onErrRtnOrderInsert -> OrderRef==[{}]", inputOrderField.getOrderRef());
		rspQueue.enqueue(new FtdcRspMsg(fromCThostFtdcInputOrder.apply(inputOrderField)));
	}

	private FromCThostFtdcOrder fromCThostFtdcOrder = new FromCThostFtdcOrder();

	/**
	 * 报单推送
	 * 
	 * @param orderField
	 */
	void onRtnOrder(CThostFtdcOrderField orderField) {
		log.info(
				"Callback onRtnOrder -> AccountID==[{}], OrderRef==[{}], OrderSysID==[{}], InstrumentID==[{}], "
						+ "OrderStatus==[{}], Direction==[{}], VolumeTotalOriginal==[{}], LimitPrice==[{}]",
				orderField.getAccountID(), orderField.getOrderRef(), orderField.getOrderSysID(),
				orderField.getInstrumentID(), orderField.getOrderStatus(), orderField.getDirection(),
				orderField.getVolumeTotalOriginal(), orderField.getLimitPrice());
		rspQueue.enqueue(new FtdcRspMsg(fromCThostFtdcOrder.apply(orderField), true));
	}

	private FromCThostFtdcTrade fromCThostFtdcTrade = new FromCThostFtdcTrade();

	/**
	 * 成交推送
	 * 
	 * @param tradeField
	 */
	void onRtnTrade(CThostFtdcTradeField tradeField) {
		log.info(
				"Callback onRtnTrade -> OrderRef==[{}], OrderSysID==[{}], InstrumentID==[{}], "
						+ "Direction==[{}], Price==[{}], Volume==[{}]",
				tradeField.getOrderRef(), tradeField.getOrderSysID(), tradeField.getInstrumentID(),
				tradeField.getDirection(), tradeField.getPrice(), tradeField.getVolume());
		rspQueue.enqueue(new FtdcRspMsg(fromCThostFtdcTrade.apply(tradeField)));
	}

	/******************
	 * 撤单请求
	 * 
	 * @param inputOrderActionField
	 */
	public void ReqOrderAction(CThostFtdcInputOrderActionField inputOrderActionField) {
		if (isTraderLogin) {
			// 设置账号信息
			inputOrderActionField.setBrokerID(config.getBrokerId());
			inputOrderActionField.setInvestorID(config.getInvestorId());
			inputOrderActionField.setUserID(config.getUserId());
			inputOrderActionField.setIPAddress(config.getIpAddr());
			inputOrderActionField.setMacAddress(config.getMacAddr());
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

	private FromCThostFtdcInputOrderAction fromCThostFtdcInputOrderAction = new FromCThostFtdcInputOrderAction();

	/**
	 * 撤单错误回调: 1
	 * 
	 * @param inputOrderActionField
	 */
	void onRspOrderAction(CThostFtdcInputOrderActionField inputOrderActionField) {
		log.info(
				"Callback onRspOrderAction -> OrderRef==[{}], OrderSysID==[{}], OrderActionRef==[{}], InstrumentID==[{}]",
				inputOrderActionField.getOrderRef(), inputOrderActionField.getOrderSysID(),
				inputOrderActionField.getOrderActionRef(), inputOrderActionField.getInstrumentID());
		rspQueue.enqueue(new FtdcRspMsg(fromCThostFtdcInputOrderAction.apply(inputOrderActionField)));
	}

	private FromCThostFtdcOrderAction fromCThostFtdcOrderAction = new FromCThostFtdcOrderAction();

	/**
	 * 撤单错误回调: 2
	 * 
	 * @param orderActionField
	 */
	void onErrRtnOrderAction(CThostFtdcOrderActionField orderActionField) {
		log.info(
				"Callback onErrRtnOrderAction -> OrderRef==[{}], OrderSysID==[{}], OrderActionRef==[{}], InstrumentID==[{}]",
				orderActionField.getOrderRef(), orderActionField.getOrderSysID(), orderActionField.getOrderActionRef(),
				orderActionField.getInstrumentID());
		rspQueue.enqueue(new FtdcRspMsg(fromCThostFtdcOrderAction.apply(orderActionField)));
	}

	/**
	 * 查询订单
	 * 
	 * @param exchangeId
	 */
	public void ReqQryOrder(String exchangeId) {
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
	 * 
	 * @param orderField
	 * @param isLast
	 */
	void onRspQryOrder(CThostFtdcOrderField orderField, boolean isLast) {
		log.info("Callback onRspQryOrder -> AccountID==[{}], OrderRef==[{}], isLast==[{}]", orderField.getAccountID(),
				orderField.getOrderRef(), isLast);
		rspQueue.enqueue(new FtdcRspMsg(fromCThostFtdcOrder.apply(orderField), isLast));
	}

	/**
	 * 查询账户
	 */
	public void ReqQryTradingAccount() {
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
	 * @param tradingAccountField
	 * @param isLast
	 */
	void onQryTradingAccount(CThostFtdcTradingAccountField tradingAccountField, boolean isLast) {
		log.info(
				"Callback onQryTradingAccount -> AccountID==[{}], Balance==[{}], "
						+ "Available==[{}], Credit==[{}], WithdrawQuota==[{}], isLast==[{}]",
				tradingAccountField.getAccountID(), tradingAccountField.getBalance(),
				tradingAccountField.getAvailable(), tradingAccountField.getCredit(),
				tradingAccountField.getWithdrawQuota(), isLast);
		// TODO Inbound

	}

	/**
	 * 
	 * @param exchangeId
	 * @param instrumentId
	 */
	public void ReqQryInvestorPosition(String exchangeId, String instrumentId) {
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

	private FromCThostFtdcInvestorPosition fromCThostFtdcInvestorPosition = new FromCThostFtdcInvestorPosition();

	/**
	 * 
	 * @param investorPositionField
	 * @param isLast
	 */
	void onRspQryInvestorPosition(CThostFtdcInvestorPositionField investorPositionField, boolean isLast) {
		log.info(
				"Callback onRspQryInvestorPosition -> InvestorID==[{}], ExchangeID==[{}], "
						+ "InstrumentID==[{}], Position==[{}], isLast==[{}]",
				investorPositionField.getInvestorID(), investorPositionField.getExchangeID(),
				investorPositionField.getInstrumentID(), investorPositionField.getPosition(), isLast);
		rspQueue.enqueue(new FtdcRspMsg(fromCThostFtdcInvestorPosition.apply(investorPositionField), isLast));
	}

	/**
	 * 查询结算信息
	 */
	public void ReqQrySettlementInfo() {
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
	public void ReqQryInstrument(String exchangeId, String instrumentId) {
		CThostFtdcQryInstrumentField qryInstrument = new CThostFtdcQryInstrumentField();
		int nRequestID = ++traderRequestId;
		qryInstrument.setExchangeID(exchangeId);
		qryInstrument.setInstrumentID(instrumentId);
		ftdcTraderApi.ReqQryInstrument(qryInstrument, nRequestID);
		log.info("Send ReqQryInstrument OK -> nRequestID==[{}], ExchangeID==[{}], InstrumentID==[{}]", nRequestID,
				qryInstrument.getExchangeID(), qryInstrument.getInstrumentID());
	}

	/**
	 * 错误推送
	 * 
	 * @param rspInfoField
	 */
	void onRspError(CThostFtdcRspInfoField rspInfoField) {
		log.error("FtdcGateway onRspError -> ErrorID==[{}], ErrorMsg==[{}]", rspInfoField.getErrorID(),
				rspInfoField.getErrorMsg());
	}

	@Override
	public void close() throws IOException {
		Threads.startNewThread("FtdcTraderApi-Release", ftdcTraderApi::Release);
		Threads.sleep(500);
		Threads.startNewThread("FtdcTraderApi-Release", ftdcMdApi::Release);
		Threads.sleep(500);
	}

}