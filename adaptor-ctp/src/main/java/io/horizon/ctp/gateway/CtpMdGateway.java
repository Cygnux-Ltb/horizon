package io.horizon.ctp.gateway;

import ctp.thostapi.CThostFtdcDepthMarketDataField;
import ctp.thostapi.CThostFtdcMdApi;
import ctp.thostapi.CThostFtdcMdSpi;
import ctp.thostapi.CThostFtdcReqUserLoginField;
import ctp.thostapi.CThostFtdcRspUserLoginField;
import ctp.thostapi.CThostFtdcSpecificInstrumentField;
import io.horizon.ctp.adaptor.CtpConfig;
import io.horizon.ctp.gateway.converter.CThostFtdcDepthMarketDataConverter;
import io.horizon.ctp.gateway.msg.FtdcRspMsg;
import io.horizon.ctp.gateway.rsp.FtdcMdConnect;
import io.horizon.ctp.gateway.utils.CtpLibraryLoader;
import io.mercury.common.annotation.thread.MustBeThreadSafe;
import io.mercury.common.datetime.DateTimeUtil;
import io.mercury.common.file.FileUtil;
import io.mercury.common.functional.Handler;
import io.mercury.common.lang.Asserter;
import io.mercury.common.lang.exception.NativeLibraryLoadException;
import io.mercury.common.log.Log4j2LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Native;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static io.mercury.common.thread.SleepSupport.sleep;
import static io.mercury.common.thread.ThreadSupport.startNewMaxPriorityThread;
import static io.mercury.common.thread.ThreadSupport.startNewThread;

public class CtpMdGateway implements Closeable {

    private static final Logger log = Log4j2LoggerFactory.getLogger(CtpGateway.class);

    // 静态加载FtdcLibrary
    static {
        try {
            CtpLibraryLoader.loadLibrary(CtpMdGateway.class);
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
    private final AtomicBoolean isInitialize = new AtomicBoolean(false);

    // 是否登陆行情接口
    private final AtomicBoolean isMdLogin = new AtomicBoolean(false);

    // 行情请求ID
    private final AtomicInteger mdRequestId = new AtomicInteger(0);

    // RSP消息处理器
    private final Handler<FtdcRspMsg> handler;

    /**
     * @param gatewayId String
     * @param config    CtpConfig
     * @param handler   Handler<FtdcRspMsg>
     */
    public CtpMdGateway(@Nonnull String gatewayId, @Nonnull CtpConfig config,
                        @MustBeThreadSafe @Nonnull Handler<FtdcRspMsg> handler) {
        Asserter.nonEmpty(gatewayId, "gatewayId");
        Asserter.nonNull(config, "config");
        Asserter.nonNull(handler, "handler");
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
                startNewMaxPriorityThread("FtdcMd-Thread", this::mdApiInitAndJoin);
            } catch (Exception e) {
                log.error("Method initAndJoin throw Exception -> {}", e.getMessage(), e);
                isInitialize.set(false);
                throw new RuntimeException(e);
            }
        }
    }

    private void mdApiInitAndJoin() {
        // 创建CTP数据文件临时目录
        File tempDir = FileUtil.mkdirInTmp(gatewayId + "-" + DateTimeUtil.date());
        log.info("Gateway -> [{}] md temp dir: {}", gatewayId, tempDir.getAbsolutePath());
        // 指定md临时文件地址
        String tempFile = new File(tempDir, "md").getAbsolutePath();
        log.info("Gateway -> [{}] md temp file : {}", gatewayId, tempFile);
        // 创建mdApi
        this.mdApi = CThostFtdcMdApi.CreateFtdcMdApi(tempFile);
        // 创建mdSpi
        CThostFtdcMdSpi mdSpi = new FtdcMdSpiImpl(new FtdcMdCallback(handler));
        // 将mdSpi注册到mdApi
        mdApi.RegisterSpi(mdSpi);
        // 注册到md前置机
        mdApi.RegisterFront(config.getMdAddr());
        // 初始化mdApi
        log.info("Call native function MdApi::Init()");
        mdApi.Init();
        // 阻塞当前线程
        log.info("Call native function MdApi::Join()");
        mdApi.Join();
    }

    /**
     * 行情订阅接口
     *
     * @param instruments String[]
     */
    public final void SubscribeMarketData(@Nonnull String[] instruments) {
        if (isMdLogin.get()) {
            mdApi.SubscribeMarketData(instruments, instruments.length);
            log.info("Send SubscribeMarketData -> count==[{}]", instruments.length);
        } else
            log.warn("Cannot SubscribeMarketData -> isMdLogin == [false]");
    }

    /**
     * 行情相关回调
     *
     * @author yellow013
     */
    class FtdcMdCallback extends FtdcCallback {

        FtdcMdCallback(Handler<FtdcRspMsg> handler) {
            super(handler);
        }

        /**
         * 行情前置断开回调
         */
        void onMdFrontDisconnected() {
            log.warn("FtdcCallback::onMdFrontDisconnected");
            // 行情断开处理逻辑
            isMdLogin.set(false);
            handler.handle(FtdcRspMsg.with(new FtdcMdConnect(isMdLogin.get())));
        }

        /**
         * 行情前置连接回调
         */
        void onMdFrontConnected() {
            log.info("FtdcCallback::onMdFrontConnected");
            // this.isMdConnect = true;
            CThostFtdcReqUserLoginField field = new CThostFtdcReqUserLoginField();
            field.setBrokerID(config.getBrokerId());
            field.setUserID(config.getUserId());
            field.setClientIPAddress(config.getIpAddr());
            field.setMacAddress(config.getMacAddr());
            int RequestID = mdRequestId.incrementAndGet();
            mdApi.ReqUserLogin(field, RequestID);
            log.info("Send Md ReqUserLogin OK -> nRequestID==[{}]", RequestID);
        }

        /**
         * 行情登录回调
         *
         * @param field CThostFtdcRspUserLoginField
         */
        void onMdRspUserLogin(CThostFtdcRspUserLoginField field) {
            log.info("FtdcCallback::onMdRspUserLogin -> FrontID==[{}], SessionID==[{}], TradingDay==[{}]",
                    field.getFrontID(), field.getSessionID(), field.getTradingDay());
            isMdLogin.set(true);
            handler.handle(FtdcRspMsg.with(new FtdcMdConnect(isMdLogin.get())));
        }

        /**
         * 订阅行情回调
         *
         * @param field CThostFtdcSpecificInstrumentField
         */
        void onRspSubMarketData(CThostFtdcSpecificInstrumentField field) {
            log.info("FtdcCallback::onRspSubMarketData -> InstrumentCode==[{}]",
                    field.getInstrumentID());
        }

        private final CThostFtdcDepthMarketDataConverter depthMarketDataConverter =
                new CThostFtdcDepthMarketDataConverter();

        /**
         * 行情推送回调
         *
         * @param field CThostFtdcDepthMarketDataField
         */
        void onRtnDepthMarketData(CThostFtdcDepthMarketDataField field) {
            log.debug(
                    "FtdcCallback::onRtnDepthMarketData -> InstrumentID == [{}], " +
                            "UpdateTime==[{}], UpdateMillisec==[{}]",
                    field.getInstrumentID(), field.getUpdateTime(), field.getUpdateMillisec());
            handler.handle(FtdcRspMsg.with(depthMarketDataConverter.apply(field)));
        }
    }

    @Override
    public void close() throws IOException {
        startNewThread("MdApi-Release", () -> {
            log.info("CThostFtdcMdApi start release");
            if (mdApi != null)
                mdApi.Release();
            log.info("CThostFtdcMdApi is released");
        });
        sleep(1000);
    }

}