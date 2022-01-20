package io.horizon.ctp.gateway;

import static io.mercury.common.thread.SleepSupport.sleep;
import static io.mercury.common.thread.ThreadSupport.startNewMaxPriorityThread;
import static io.mercury.common.thread.ThreadSupport.startNewThread;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Native;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcDepthMarketDataField;
import ctp.thostapi.CThostFtdcMdApi;
import ctp.thostapi.CThostFtdcMdSpi;
import ctp.thostapi.CThostFtdcReqUserLoginField;
import ctp.thostapi.CThostFtdcRspUserLoginField;
import ctp.thostapi.CThostFtdcSpecificInstrumentField;
import io.horizon.ctp.adaptor.CtpConfig;
import io.horizon.ctp.gateway.converter.CThostFtdcDepthMarketDataConverter;
import io.horizon.ctp.gateway.rsp.FtdcMdConnect;
import io.mercury.common.annotation.thread.MustBeThreadSafe;
import io.mercury.common.datetime.DateTimeUtil;
import io.mercury.common.file.FileUtil;
import io.mercury.common.functional.Handler;
import io.mercury.common.lang.Assertor;
import io.mercury.common.lang.exception.NativeLibraryLoadException;
import io.mercury.common.log.Log4j2LoggerFactory;

public class CtpMdGateway implements Closeable {

	private static final Logger log = Log4j2LoggerFactory.getLogger(CtpGateway.class);

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

	// 是否已初始化
	private AtomicBoolean isInitialize = new AtomicBoolean(false);

	// 是否登陆行情接口
	private volatile boolean isMdLogin;

	// 行情请求ID
	private volatile int mdRequestId = -1;

	// RSP消息处理器
	private final Handler<FtdcRspMsg> handler;

	/**
	 * 
	 * @param gatewayId
	 * @param config
	 * @param handler
	 * @param mode      0:正常模式, 1:行情模式, 2:交易模式
	 */
	public CtpMdGateway(@Nonnull String gatewayId, @Nonnull CtpConfig config,
			@MustBeThreadSafe @Nonnull Handler<FtdcRspMsg> handler) {
		Assertor.nonEmpty(gatewayId, "gatewayId");
		Assertor.nonNull(config, "config");
		Assertor.nonNull(handler, "handler");
		this.gatewayId = gatewayId;
		this.config = config;
		this.handler = handler;
	}

	/**
	 * 启动并挂起线程
	 */
	public final void bootstrap() {
		if (isInitialize.compareAndSet(false, true)) {
			log.info("CThostFtdcMdApi.version() -> {}", CThostFtdcMdApi.GetApiVersion());
			try {
				startNewMaxPriorityThread("FtdcMd-Thread", () -> mdInitAndJoin());
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
	private void mdInitAndJoin() {
		// 创建CTP数据文件临时目录
		File tempDir = FileUtil.mkdirInTmp(gatewayId + "-" + DateTimeUtil.date());
		log.info("Gateway -> [{}] md temp file dir : {}", tempDir.getAbsolutePath());
		// 指定md临时文件地址
		String tempFilePath = new File(tempDir, "md").getAbsolutePath();
		log.info("Gateway -> [{}] md api use temp file path : {}", gatewayId, tempFilePath);
		// 创建mdApi
		this.mdApi = CThostFtdcMdApi.CreateFtdcMdApi(tempFilePath);
		// 创建mdSpi
		CThostFtdcMdSpi mdSpi = new FtdcMdSpiImpl(new FtdcMdCallback(handler));
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
	 * 行情相关回调
	 * 
	 * @author yellow013
	 *
	 */
	class FtdcMdCallback extends FtdcCallback {

		FtdcMdCallback(Handler<FtdcRspMsg> handler) {
			super(handler);
		}

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

	@Override
	public void close() throws IOException {
		startNewThread("MdApi-Release", () -> {
			if (mdApi != null)
				mdApi.Release();
			log.info("CThostFtdcMdApi released.");
		});
		sleep(1000);
	}

}