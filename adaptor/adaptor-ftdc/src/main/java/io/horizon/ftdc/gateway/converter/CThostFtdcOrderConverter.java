package io.horizon.ftdc.gateway.converter;

import java.util.function.Function;

import ctp.thostapi.CThostFtdcOrderField;
import io.horizon.ftdc.gateway.rsp.FtdcOrder;

public class CThostFtdcOrderConverter implements Function<CThostFtdcOrderField, FtdcOrder> {

	@Override
	public FtdcOrder apply(CThostFtdcOrderField field) {
		return new FtdcOrder()
				
				.setBrokerID(field.getBrokerID())
				
				.setInvestorID(field.getInvestorID())
				
				.setInstrumentID(field.getInstrumentID())
				
				.setOrderRef(field.getOrderRef())
				
				.setUserID(field.getUserID())
				
				.setOrderPriceType(field.getOrderPriceType())
				
				.setDirection(field.getDirection())
				
				.setCombOffsetFlag(field.getCombOffsetFlag())
				
				.setCombHedgeFlag(field.getCombHedgeFlag())
				
				.setLimitPrice(field.getLimitPrice())
				
				.setVolumeTotalOriginal(field.getVolumeTotalOriginal())
				
				.setTimeCondition(field.getTimeCondition())
				
				.setGTDDate(field.getGTDDate())
				
				.setVolumeCondition(field.getVolumeCondition())
				
				.setMinVolume(field.getMinVolume())
				
				.setContingentCondition(field.getContingentCondition())
				
				.setStopPrice(field.getStopPrice())
				
				.setForceCloseReason(field.getForceCloseReason())
				
				.setIsAutoSuspend(field.getIsAutoSuspend())
				
				.setBusinessUnit(field.getBusinessUnit())
				
				.setRequestID(field.getRequestID())
				
				.setOrderLocalID(field.getOrderLocalID())
				
				.setExchangeID(field.getExchangeID())
				
				.setParticipantID(field.getParticipantID())
				
				.setClientID(field.getClientID())
				
				.setExchangeInstID(field.getExchangeInstID())
				
				.setTraderID(field.getTraderID())
				
				.setInstallID(field.getInstallID())
				
				.setOrderSubmitStatus(field.getOrderSubmitStatus())
				
				.setNotifySequence(field.getNotifySequence())
				
				.setTradingDay(field.getTradingDay())
				
				.setSettlementID(field.getSettlementID())
				
				.setOrderSysID(field.getOrderSysID())
				
				.setOrderSource(field.getOrderSource())
				
				.setOrderStatus(field.getOrderStatus())
				
				.setOrderType(field.getOrderType())
				
				.setVolumeTraded(field.getVolumeTraded())
				
				.setVolumeTotal(field.getVolumeTotal())
				
				.setInsertDate(field.getInsertDate())
				
				.setInsertTime(field.getInsertTime())
				
				.setActiveTime(field.getActiveTime())
				
				.setSuspendTime(field.getSuspendTime())
				
				.setUpdateTime(field.getUpdateTime())
				
				.setCancelTime(field.getCancelTime())
				
				.setActiveTraderID(field.getActiveTraderID())
				
				.setClearingPartID(field.getClearingPartID())
				
				.setSequenceNo(field.getSequenceNo())
				
				.setFrontID(field.getFrontID())
				
				.setSessionID(field.getSessionID())
				
				.setUserProductInfo(field.getUserProductInfo())
				
				.setStatusMsg(field.getStatusMsg())
				
				.setUserForceClose(field.getUserForceClose())
				
				.setActiveUserID(field.getActiveUserID())
				
				.setBrokerOrderSeq(field.getBrokerOrderSeq())
				
				.setRelativeOrderSysID(field.getRelativeOrderSysID())
				
				.setZCETotalTradedVolume(field.getZCETotalTradedVolume())
				
				.setIsSwapOrder(field.getIsSwapOrder())
				
				.setBranchID(field.getBranchID())
				
				.setInvestUnitID(field.getInvestUnitID())
				
				.setAccountID(field.getAccountID())
				
				.setCurrencyID(field.getCurrencyID())
				
				.setIPAddress(field.getIPAddress())
				
				.setMacAddress(field.getMacAddress());
	
	}

}
