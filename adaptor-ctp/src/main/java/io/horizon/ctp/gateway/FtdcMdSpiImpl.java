package io.horizon.ctp.gateway;

import static io.horizon.ctp.gateway.FtdcErrorValidator.hasError;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcDepthMarketDataField;
import ctp.thostapi.CThostFtdcMdSpi;
import ctp.thostapi.CThostFtdcRspInfoField;
import ctp.thostapi.CThostFtdcRspUserLoginField;
import ctp.thostapi.CThostFtdcSpecificInstrumentField;
import ctp.thostapi.CThostFtdcUserLogoutField;
import io.horizon.ctp.gateway.CtpGateway.FtdcMdCallback;
import io.mercury.common.log.Log4j2LoggerFactory;

public final class FtdcMdSpiImpl extends CThostFtdcMdSpi {

	private static final Logger log = Log4j2LoggerFactory.getLogger(FtdcMdSpiImpl.class);

	private final FtdcMdCallback callback;

	FtdcMdSpiImpl(FtdcMdCallback callback) {
		this.callback = callback;
	}

	@Override
	public void OnFrontConnected() {
		log.info("MdSpiImpl :: OnFrontConnected");
		callback.onMdFrontConnected();
	}

	@Override
	public void OnFrontDisconnected(int nReason) {
		log.error("MdSpiImpl :: OnFrontDisconnected, nReason==[{}]", nReason);
		callback.onMdFrontDisconnected();
	}

	@Override
	public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
		log.info("MdSpiImpl :: OnRspUserLogin");
		if (!hasError("MdSpi :: OnRspUserLogin", pRspInfo)) {
			if (pRspUserLogin != null)
				callback.onMdRspUserLogin(pRspUserLogin);
			else
				log.error("FtdcMdSpiImpl :: OnRspUserLogin return null");
		}
	}

	@Override
	public void OnRspUserLogout(CThostFtdcUserLogoutField pUserLogout, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {
		log.info("MdSpiImpl :: OnRspUserLogout");
		if (!hasError("MdSpi :: OnRspUserLogout", pRspInfo)) {
			if (pUserLogout != null)
				// TODO 处理用户登出
				log.info("Output :: OnRspUserLogout -> BrokerID==[{}], UserID==[{}]", pUserLogout.getBrokerID(),
						pUserLogout.getUserID());
			else
				log.error("FtdcMdSpiImpl :: OnRspUserLogin return null");
		}

	}

	@Override
	public void OnRspSubMarketData(CThostFtdcSpecificInstrumentField pSpecificInstrument,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		log.info("MdSpiImpl :: OnRspSubMarketData");
		if (!hasError("MdSpi :: OnRspSubMarketData", pRspInfo)) {
			if (pSpecificInstrument != null)
				callback.onRspSubMarketData(pSpecificInstrument);
			else
				log.error("FtdcMdSpiImpl :: OnRspSubMarketData return null");
		}
	}

	@Override
	public void OnRtnDepthMarketData(CThostFtdcDepthMarketDataField pDepthMarketData) {
		if (pDepthMarketData != null)
			callback.onRtnDepthMarketData(pDepthMarketData);
		else
			log.error("FtdcMdSpiImpl :: OnRtnDepthMarketData return null");
	}

	/**
	 * 错误回调
	 */
	@Override
	public void OnRspError(CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		log.error("MdSpiImpl :: OnRspError, nRequestID==[{}], bIsLast==[{}]", nRequestID, bIsLast);
		callback.onRspError(pRspInfo, nRequestID, bIsLast);
	}

}