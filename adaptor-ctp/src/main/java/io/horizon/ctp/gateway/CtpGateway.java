package io.horizon.ctp.gateway;

import ctp.thostapi.CThostFtdcInputOrderActionField;
import ctp.thostapi.CThostFtdcInputOrderField;
import io.horizon.ctp.adaptor.CtpConfig;
import io.horizon.ctp.gateway.msg.FtdcRspMsg;
import io.horizon.trader.adaptor.AdaptorRunMode;
import io.mercury.common.annotation.thread.MustBeThreadSafe;
import io.mercury.common.functional.Handler;
import io.mercury.common.lang.Asserter;
import io.mercury.common.lang.exception.NativeLibraryLoadException;
import io.mercury.common.log4j2.Log4j2LoggerFactory;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.io.Closeable;
import java.io.IOException;

import static io.horizon.ctp.gateway.utils.CtpLibraryLoader.loadLibrary;
import static io.mercury.common.thread.SleepSupport.sleep;

@NotThreadSafe
public final class CtpGateway implements Closeable {

    private static final Logger log = Log4j2LoggerFactory.getLogger(CtpGateway.class);

    // 静态加载FtdcLibrary
    static {
        try {
            loadLibrary(CtpGateway.class);
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

    // 行情Gateway
    private CtpMdGateway mdGateway;

    // 交易Gateway
    private CtpTraderGateway traderGateway;

    // RSP消息处理器
    private final Handler<FtdcRspMsg> handler;

    public enum CtpRunMode {

        Normal, OnlyMarketData, OnlyTrade;

        public static CtpRunMode get(AdaptorRunMode mode) {
            return switch (mode) {
                case Normal -> Normal;
                case OnlyMarketData -> OnlyMarketData;
                case OnlyTrade -> OnlyTrade;
            };
        }
    }

    /**
     * @param gatewayId String
     * @param config    CtpConfig
     * @param handler   Handler<FtdcRspMsg>
     * @param mode      运行模式: 0,正常模式; 1,行情模式; 2,交易模式
     */
    public CtpGateway(@Nonnull String gatewayId,
                      @Nonnull CtpConfig config,
                      @Nonnull CtpRunMode mode,
                      @MustBeThreadSafe Handler<FtdcRspMsg> handler) {
        Asserter.nonEmpty(gatewayId, "gatewayId");
        Asserter.nonNull(config, "config");
        Asserter.nonNull(handler, "handler");
        this.gatewayId = gatewayId;
        this.config = config;
        this.handler = handler;
        initializer(mode);
    }

    @PostConstruct
    private void initializer(CtpRunMode mode) {
        switch (mode) {
            case OnlyMarketData -> this.mdGateway = new CtpMdGateway(gatewayId, config, handler);
            case OnlyTrade -> this.traderGateway = new CtpTraderGateway(gatewayId, config, handler);
            default -> {
                this.mdGateway = new CtpMdGateway(gatewayId, config, handler);
                this.traderGateway = new CtpTraderGateway(gatewayId, config, handler);
            }
        }
    }

    /**
     * 启动并挂起线程
     */
    public void bootstrap() {
        if (mdGateway != null) {
            mdGateway.bootstrap();
            sleep(777);
        }
        if (traderGateway != null) {
            traderGateway.bootstrap();
            sleep(777);
        }
    }

    /**
     * 行情订阅接口
     *
     * @param instruments String[]
     */
    public void SubscribeMarketData(@Nonnull String[] instruments) {
        mdGateway.SubscribeMarketData(instruments);
    }

    /**
     * 报单请求
     *
     * @param field CThostFtdcInputOrderField
     */
    public void ReqOrderInsert(CThostFtdcInputOrderField field) {
        traderGateway.ReqOrderInsert(field);
    }

    /**
     * 撤单请求
     *
     * @param field CThostFtdcInputOrderActionField
     */
    public void ReqOrderAction(CThostFtdcInputOrderActionField field) {
        traderGateway.ReqOrderAction(field);
    }

    /**
     * 查询订单
     *
     * @param exchangeCode   String
     * @param instrumentCode String
     */
    public void ReqQryOrder(String exchangeCode, String instrumentCode) {
        traderGateway.ReqQryOrder(exchangeCode, instrumentCode);
    }

    /**
     * 查询账户
     */
    public void ReqQryTradingAccount() {
        traderGateway.ReqQryTradingAccount();
    }

    /**
     * @param exchangeCode   String
     * @param instrumentCode String
     */
    public void ReqQryInvestorPosition(String exchangeCode, String instrumentCode) {
        traderGateway.ReqQryInvestorPosition(exchangeCode, instrumentCode);
    }

    /**
     * 查询结算信息
     */
    public void ReqQrySettlementInfo() {
        traderGateway.ReqQrySettlementInfo();
    }

    /**
     * 查询交易标的
     *
     * @param exchangeId   String
     * @param instrumentId String
     */
    public void ReqQryInstrument(String exchangeId, String instrumentId) {
        traderGateway.ReqQryInstrument(exchangeId, instrumentId);
    }

    @Override
    public void close() throws IOException {
        if (mdGateway != null) {
            mdGateway.close();
            sleep(777);
        }
        if (traderGateway != null) {
            traderGateway.close();
            sleep(777);
        }
    }

}