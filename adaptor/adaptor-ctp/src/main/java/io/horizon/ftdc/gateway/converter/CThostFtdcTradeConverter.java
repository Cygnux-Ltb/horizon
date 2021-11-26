package io.horizon.ftdc.gateway.converter;

import java.util.function.Function;

import ctp.thostapi.CThostFtdcTradeField;
import io.horizon.ftdc.gateway.rsp.FtdcTrade;

public class CThostFtdcTradeConverter implements Function<CThostFtdcTradeField, FtdcTrade> {

	@Override
	public FtdcTrade apply(CThostFtdcTradeField field) {
		return new FtdcTrade()
				
				.setBrokerID(field.getBrokerID())
				
				.setInvestorID(field.getInvestorID())
				
				.setInstrumentID(field.getInstrumentID())
				
				.setOrderRef(field.getOrderRef())
				
				.setUserID(field.getUserID())
				
				.setExchangeID(field.getExchangeID())
				
				.setTradeID(field.getTradeID())
				
				.setDirection(field.getDirection())
				
				.setOrderSysID(field.getOrderSysID())
				
				.setParticipantID(field.getParticipantID())
				
				.setClientID(field.getClientID())
				
				.setTradingRole(field.getTradingRole())
				
				.setExchangeInstID(field.getExchangeInstID())
				
				.setOffsetFlag(field.getOffsetFlag())
				
				.setHedgeFlag(field.getHedgeFlag())
				
				.setPrice(field.getPrice())
				
				.setVolume(field.getVolume())
				
				.setTradeDate(field.getTradeDate())
				
				.setTradeTime(field.getTradeTime())
				
				.setTradeType(field.getTradeType())
				
				.setPriceSource(field.getPriceSource())
				
				.setTraderID(field.getTraderID())
				
				.setOrderLocalID(field.getOrderLocalID())
				
				.setClearingPartID(field.getClearingPartID())
				
				.setBusinessUnit(field.getBusinessUnit())
				
				.setSequenceNo(field.getSequenceNo())
				
				.setTradingDay(field.getTradingDay())
				
				.setSettlementID(field.getSettlementID())
				
				.setBrokerOrderSeq(field.getBrokerOrderSeq())
				
				.setTradeSource(field.getTradeSource())
				
				.setInvestUnitID(field.getInvestUnitID());
	
	}

}
