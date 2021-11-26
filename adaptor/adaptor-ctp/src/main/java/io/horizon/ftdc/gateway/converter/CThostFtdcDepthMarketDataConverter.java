package io.horizon.ftdc.gateway.converter;

import java.util.function.Function;

import ctp.thostapi.CThostFtdcDepthMarketDataField;
import io.horizon.ftdc.gateway.rsp.FtdcDepthMarketData;

public final class CThostFtdcDepthMarketDataConverter implements Function<CThostFtdcDepthMarketDataField, FtdcDepthMarketData> {

	@Override
	public FtdcDepthMarketData apply(CThostFtdcDepthMarketDataField field) {
		return new FtdcDepthMarketData()
				
				.setTradingDay(field.getTradingDay())
				
				.setInstrumentID(field.getInstrumentID())
				
				.setExchangeID(field.getExchangeID())
				
				.setExchangeInstID(field.getExchangeInstID())
				
				.setLastPrice(field.getLastPrice())
				
				.setPreSettlementPrice(field.getPreSettlementPrice())
				
				.setPreClosePrice(field.getPreClosePrice())
				
				.setPreOpenInterest(field.getPreOpenInterest())
				
				.setOpenPrice(field.getOpenPrice())
				
				.setHighestPrice(field.getHighestPrice())
				
				.setLowestPrice(field.getLowestPrice())
				
				.setVolume(field.getVolume())
				
				.setTurnover(field.getTurnover())
				
				.setOpenInterest(field.getOpenInterest())
				
				.setClosePrice(field.getClosePrice())
				
				.setSettlementPrice(field.getSettlementPrice())
				
				.setUpperLimitPrice(field.getUpperLimitPrice())
				
				.setLowerLimitPrice(field.getLowerLimitPrice())
				
				.setPreDelta(field.getPreDelta())
				
				.setCurrDelta(field.getCurrDelta())
				
				.setBidPrice1(field.getBidPrice1())
				
				.setBidVolume1(field.getBidVolume1())
				
				.setAskPrice1(field.getAskPrice1())
				
				.setAskVolume1(field.getAskVolume1())
				
				.setBidPrice2(field.getBidPrice2())
				
				.setBidVolume2(field.getBidVolume2())
				
				.setAskPrice2(field.getAskPrice2())
				
				.setAskVolume2(field.getAskVolume2())
				
				.setBidPrice3(field.getBidPrice3())
				
				.setBidVolume3(field.getBidVolume3())
				
				.setAskPrice3(field.getAskPrice3())
				
				.setAskVolume3(field.getAskVolume3())
				
				.setBidPrice4(field.getBidPrice4())
				
				.setBidVolume4(field.getBidVolume4())
				
				.setAskPrice4(field.getAskPrice4())
				
				.setAskVolume4(field.getAskVolume4())
				
				.setBidPrice5(field.getBidPrice5())
				
				.setBidVolume5(field.getBidVolume5())
				
				.setAskPrice5(field.getAskPrice5())
				
				.setAskVolume5(field.getAskVolume5())
				
				.setAveragePrice(field.getAveragePrice())
				
				.setUpdateTime(field.getUpdateTime())
				
				.setUpdateMillisec(field.getUpdateMillisec())
				
				.setActionDay(field.getActionDay());
		
	}

}
