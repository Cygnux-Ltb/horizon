package io.horizon.ctp.gateway.converter;

import ctp.thostapi.CThostFtdcInputOrderField;
import io.horizon.ctp.gateway.rsp.FtdcInputOrder;

import java.util.function.Function;

public class CThostFtdcInputOrderConverter implements Function<CThostFtdcInputOrderField, FtdcInputOrder> {

	@Override
	public FtdcInputOrder apply(CThostFtdcInputOrderField field) {
		return new FtdcInputOrder()

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

				.setUserForceClose(field.getUserForceClose())

				.setIsSwapOrder(field.getIsSwapOrder())

				.setExchangeID(field.getExchangeID())

				.setInvestUnitID(field.getInvestUnitID())

				.setAccountID(field.getAccountID())

				.setCurrencyID(field.getCurrencyID())

				.setClientID(field.getClientID())

				.setIPAddress(field.getIPAddress())

				.setMacAddress(field.getMacAddress());

	}

}
