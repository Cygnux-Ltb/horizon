package io.horizon.ctp.gateway;

import ctp.thostapi.CThostFtdcDepthMarketDataField;
import ctp.thostapi.CThostFtdcMdSpi;
import ctp.thostapi.CThostFtdcRspInfoField;
import ctp.thostapi.CThostFtdcRspUserLoginField;
import ctp.thostapi.CThostFtdcSpecificInstrumentField;
import ctp.thostapi.CThostFtdcUserLogoutField;
import io.horizon.ctp.gateway.CtpMdGateway.FtdcMdCallback;
import io.mercury.common.log4j2.Log4j2LoggerFactory;
import org.slf4j.Logger;

import static io.horizon.ctp.gateway.handler.FtdcRspInfoHandler.hasError;

public final class FtdcMdSpiImpl extends CThostFtdcMdSpi {

    private static final Logger log = Log4j2LoggerFactory.getLogger(FtdcMdSpiImpl.class);

    private final FtdcMdCallback callback;

    FtdcMdSpiImpl(FtdcMdCallback callback) {
        this.callback = callback;
    }

    @Override
    public void OnFrontConnected() {
        log.info("FtdcMdSpi::OnFrontConnected");
        callback.onMdFrontConnected();
    }

    @Override
    public void OnFrontDisconnected(int nReason) {
        log.error("FtdcMdSpi::OnFrontDisconnected, nReason==[{}]", nReason);
        callback.onMdFrontDisconnected();
    }

    @Override
    public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin,
                               CThostFtdcRspInfoField pRspInfo,
                               int nRequestID, boolean bIsLast) {
        log.info("FtdcMdSpi::OnRspUserLogin");
        if (!hasError("FtdcMdSpi::OnRspUserLogin", pRspInfo)) {
            if (pRspUserLogin != null)
                callback.onMdRspUserLogin(pRspUserLogin);
            else
                log.error("FtdcMdSpi::OnRspUserLogin return null");
        }
    }

    @Override
    public void OnRspUserLogout(CThostFtdcUserLogoutField pUserLogout,
                                CThostFtdcRspInfoField pRspInfo,
                                int nRequestID, boolean bIsLast) {
        log.info("FtdcMdSpi::OnRspUserLogout");
        if (!hasError("FtdcMdSpi::OnRspUserLogout", pRspInfo)) {
            if (pUserLogout != null)
                // TODO 处理用户登出
                log.info("Output :: OnRspUserLogout -> BrokerID==[{}], UserID==[{}]", pUserLogout.getBrokerID(),
                        pUserLogout.getUserID());
            else
                log.error("FtdcMdSpi::OnRspUserLogin return null");
        }

    }

    @Override
    public void OnRspSubMarketData(CThostFtdcSpecificInstrumentField pSpecificInstrument,
                                   CThostFtdcRspInfoField pRspInfo,
                                   int nRequestID, boolean bIsLast) {
        log.info("FtdcMdSpi::OnRspSubMarketData");
        if (!hasError("FtdcMdSpi::OnRspSubMarketData", pRspInfo)) {
            if (pSpecificInstrument != null)
                callback.onRspSubMarketData(pSpecificInstrument);
            else
                log.error("FtdcMdSpi::OnRspSubMarketData return null");
        }
    }

    @Override
    public void OnRtnDepthMarketData(CThostFtdcDepthMarketDataField pDepthMarketData) {
        if (pDepthMarketData != null)
            callback.onRtnDepthMarketData(pDepthMarketData);
        else
            log.error("FtdcMdSpi::OnRtnDepthMarketData return null");
    }

    /**
     * 错误回调
     */
    @Override
    public void OnRspError(CThostFtdcRspInfoField pRspInfo,
                           int nRequestID, boolean bIsLast) {
        log.error("FtdcMdSpi::OnRspError, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
        callback.onRspError(pRspInfo, nRequestID, bIsLast);
    }

}