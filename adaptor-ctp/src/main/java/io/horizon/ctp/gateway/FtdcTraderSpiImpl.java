package io.horizon.ctp.gateway;

import static io.horizon.ctp.gateway.handler.FtdcRspInfoHandler.hasError;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcInputOrderActionField;
import ctp.thostapi.CThostFtdcInputOrderField;
import ctp.thostapi.CThostFtdcInstrumentField;
import ctp.thostapi.CThostFtdcInvestorPositionField;
import ctp.thostapi.CThostFtdcOrderActionField;
import ctp.thostapi.CThostFtdcOrderField;
import ctp.thostapi.CThostFtdcRspAuthenticateField;
import ctp.thostapi.CThostFtdcRspInfoField;
import ctp.thostapi.CThostFtdcRspUserLoginField;
import ctp.thostapi.CThostFtdcSettlementInfoField;
import ctp.thostapi.CThostFtdcTradeField;
import ctp.thostapi.CThostFtdcTraderSpi;
import ctp.thostapi.CThostFtdcTradingAccountField;
import ctp.thostapi.CThostFtdcUserLogoutField;
import io.horizon.ctp.gateway.CtpTraderGateway.FtdcTraderCallback;
import io.mercury.common.log.Log4j2LoggerFactory;

public final class FtdcTraderSpiImpl extends CThostFtdcTraderSpi {

    private static final Logger log = Log4j2LoggerFactory.getLogger(FtdcTraderSpiImpl.class);

    private final FtdcTraderCallback callback;

    FtdcTraderSpiImpl(FtdcTraderCallback callback) {
        this.callback = callback;
    }

    @Override
    public void OnFrontConnected() {
        log.info("FtdcTraderSpi::OnFrontConnected");
        callback.onTraderFrontConnected();
    }

    @Override
    public void OnFrontDisconnected(int nReason) {
        log.error("FtdcTraderSpi::OnFrontDisconnected, nReason==[{}]", nReason);
        callback.onTraderFrontDisconnected();
    }

    @Override
    public void OnRspAuthenticate(CThostFtdcRspAuthenticateField pRspAuthenticate, CThostFtdcRspInfoField pRspInfo,
                                  int nRequestID, boolean bIsLast) {
        log.info("FtdcTraderSpi::OnRspAuthenticate, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
        if (!hasError("FtdcTraderSpi::OnRspAuthenticate", pRspInfo)) {
            if (pRspAuthenticate != null)
                callback.onRspAuthenticate(pRspAuthenticate);
            else
                log.warn("FtdcTraderSpi::OnRspAuthenticate return null");
        }
    }

    @Override
    public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo,
                               int nRequestID, boolean bIsLast) {
        log.info("FtdcTraderSpi::OnRspUserLogin, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
        if (!hasError("FtdcTraderSpi::OnRspUserLogin", pRspInfo)) {
            if (pRspUserLogin != null)
                callback.onTraderRspUserLogin(pRspUserLogin);
            else
                log.error("FtdcTraderSpi::OnRspUserLogin return null");
        }
    }

    @Override
    public void OnRspUserLogout(CThostFtdcUserLogoutField pUserLogout, CThostFtdcRspInfoField pRspInfo, int nRequestID,
                                boolean bIsLast) {
        log.info("FtdcTraderSpi::OnRspUserLogout, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
        if (!hasError("FtdcTraderSpi::OnRspUserLogout", pRspInfo)) {
            if (pUserLogout != null) {
                // TODO 处理用户登出
                log.info("Output :: OnRspUserLogout -> BrokerID==[{}], UserID==[{}]", pUserLogout.getBrokerID(),
                        pUserLogout.getUserID());
            } else {
                log.error("FtdcTraderSpi::OnRspUserLogout return null");
            }
        }
    }

    @Override
    public void OnRspQryOrder(CThostFtdcOrderField pOrder, CThostFtdcRspInfoField pRspInfo, int nRequestID,
                              boolean bIsLast) {
        log.info("FtdcTraderSpi::OnRspQryOrder, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
        if (!hasError("FtdcTraderSpi::OnRspQryOrder", pRspInfo)) {
            if (pOrder != null)
                callback.onRspQryOrder(pOrder, bIsLast);
            else
                log.error("FtdcTraderSpi::OnRspQryOrder return null");

        }
    }

    @Override
    public void OnRspQryTradingAccount(CThostFtdcTradingAccountField pTradingAccount, CThostFtdcRspInfoField pRspInfo,
                                       int nRequestID, boolean bIsLast) {
        log.info("FtdcTraderSpi::OnRspQryTradingAccount, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
        if (!hasError("FtdcTraderSpi::OnRspQryTradingAccount", pRspInfo)) {
            if (pTradingAccount != null)
                callback.onQryTradingAccount(pTradingAccount, bIsLast);
            else
                log.error("FtdcTraderSpi::OnRspQryTradingAccount return null");
        }
    }

    @Override
    public void OnRspQryInvestorPosition(CThostFtdcInvestorPositionField pInvestorPosition,
                                         CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        log.info("FtdcTraderSpi::OnRspQryInvestorPosition, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
        hasError("FtdcTraderSpi::OnRspQryInvestorPosition", pRspInfo);
        if (pInvestorPosition != null) {
            callback.onRspQryInvestorPosition(pInvestorPosition, bIsLast);
        } else {
            log.error("FtdcTraderSpi::OnRspQryInvestorPosition return null");
        }
    }

    @Override
    public void OnRspQrySettlementInfo(CThostFtdcSettlementInfoField pSettlementInfo, CThostFtdcRspInfoField pRspInfo,
                                       int nRequestID, boolean bIsLast) {
        log.info("FtdcTraderSpi::OnRspQrySettlementInfo, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
        if (!hasError("FtdcTraderSpi::OnRspQrySettlementInfo", pRspInfo)) {
            if (pSettlementInfo != null) {
                log.info(
                        """
                                Output :: OnRspQrySettlementInfo -> BrokerID==[{}], AccountID==[{}], InvestorID==[{}],
                                            SettlementID==[{}], TradingDay==[{}], CurrencyID==[{}]
                                                 <<<<<<<<<<<<<<<< CONTENT TEXT >>>>>>>>>>>>>>>>
                                                                    {}
                                                """,
                        pSettlementInfo.getBrokerID(), pSettlementInfo.getAccountID(), pSettlementInfo.getInvestorID(),
                        pSettlementInfo.getSettlementID(), pSettlementInfo.getTradingDay(),
                        pSettlementInfo.getCurrencyID(), pSettlementInfo.getContent());
            } else {
                log.error("FtdcTraderSpi::OnRspQrySettlementInfo return null");
            }
        }
    }

    @Override
    public void OnRspQryInstrument(CThostFtdcInstrumentField pInstrument, CThostFtdcRspInfoField pRspInfo,
                                   int nRequestID, boolean bIsLast) {
        log.info("FtdcTraderSpi::OnRspQryInstrument, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
        if (!hasError("FtdcTraderSpi::OnRspQryInstrument", pRspInfo)) {
            if (pInstrument != null)
                log.info("Output :: OnRspQryInstrument, ExchangeID==[{}], InstrumentID==[{}]",
                        pInstrument.getExchangeID(), pInstrument.getInstrumentID());
            else
                log.error("FtdcTraderSpi::OnRspQryInstrument return null");
        }
    }

    @Override
    public void OnRtnOrder(CThostFtdcOrderField pOrder) {
        if (pOrder != null)
            callback.onRtnOrder(pOrder);
        else
            log.error("FtdcTraderSpi::OnRtnOrder return null");
    }

    @Override
    public void OnRtnTrade(CThostFtdcTradeField pTrade) {
        if (pTrade != null)
            callback.onRtnTrade(pTrade);
        else
            log.error("FtdcTraderSpi::OnRtnTrade return null");
    }

    /**
     * 报单错误回调:1
     */
    @Override
    public void OnRspOrderInsert(CThostFtdcInputOrderField pInputOrder, CThostFtdcRspInfoField pRspInfo, int nRequestID,
                                 boolean bIsLast) {
        log.info("FtdcTraderSpi::OnRspOrderInsert, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
        if (!hasError("FtdcTraderSpi::OnRspOrderInsert", pRspInfo)) {
            if (pInputOrder != null)
                callback.onRspOrderInsert(pInputOrder);
            else
                log.error("FtdcTraderSpi::OnRspOrderInsert return null");
        }
    }

    /**
     * 报单错误回调:2
     */
    @Override
    public void OnErrRtnOrderInsert(CThostFtdcInputOrderField pInputOrder, CThostFtdcRspInfoField pRspInfo) {
        log.info("TraderSpi::OnErrRtnOrderInsert");
        if (!hasError("TraderSpi::OnErrRtnOrderInsert", pRspInfo)) {
            if (pInputOrder != null)
                callback.onErrRtnOrderInsert(pInputOrder);
            else
                log.error("TraderSpi::OnErrRtnOrderInsert return null");
        }
    }

    /**
     * 撤单错误回调:1
     */
    @Override
    public void OnRspOrderAction(CThostFtdcInputOrderActionField pInputOrderAction, CThostFtdcRspInfoField pRspInfo,
                                 int nRequestID, boolean bIsLast) {
        log.info("TraderSpi::OnRspOrderAction, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
        if (!hasError("TraderSpi::OnRspOrderAction", pRspInfo)) {
            if (pInputOrderAction != null)
                callback.onRspOrderAction(pInputOrderAction);
            else
                log.error("TraderSpi::OnRspOrderAction return null");
        }
    }

    /**
     * 撤单错误回调:2
     */
    @Override
    public void OnErrRtnOrderAction(CThostFtdcOrderActionField pOrderAction, CThostFtdcRspInfoField pRspInfo) {
        log.info("TraderSpi::OnErrRtnOrderAction");
        if (!hasError("TraderSpi::OnErrRtnOrderAction", pRspInfo)) {
            if (pOrderAction != null)
                callback.onErrRtnOrderAction(pOrderAction);
            else
                log.error("TraderSpi::OnErrRtnOrderAction return null");
        }
    }

    /**
     * 错误回调
     */
    @Override
    public void OnRspError(CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        log.info("TraderSpi::OnRspError, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
        callback.onRspError(pRspInfo, nRequestID, bIsLast);
    }

}