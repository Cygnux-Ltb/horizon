package io.horizon.ctp.gateway.converter;

import ctp.thostapi.CThostFtdcInputOrderActionField;
import io.horizon.ctp.gateway.rsp.FtdcInputOrderAction;

import java.util.function.Function;

public final class CThostFtdcInputOrderActionConverter
		implements Function<CThostFtdcInputOrderActionField, FtdcInputOrderAction> {

	@Override
	public FtdcInputOrderAction apply(CThostFtdcInputOrderActionField field) {
		return new FtdcInputOrderAction()

				.setBrokerID(field.getBrokerID())

				.setInvestorID(field.getInvestorID())

				.setOrderActionRef(field.getOrderActionRef())

				.setOrderRef(field.getOrderRef())

				.setRequestID(field.getRequestID())

				.setFrontID(field.getFrontID())

				.setSessionID(field.getSessionID())

				.setExchangeID(field.getExchangeID())

				.setOrderSysID(field.getOrderSysID())

				.setActionFlag(field.getActionFlag())

				.setLimitPrice(field.getLimitPrice())

				.setVolumeChange(field.getVolumeChange())

				.setUserID(field.getUserID())

				.setInstrumentID(field.getInstrumentID())

				.setInvestUnitID(field.getInvestUnitID())

				.setIPAddress(field.getIPAddress())

				.setMacAddress(field.getMacAddress());

	}

}
