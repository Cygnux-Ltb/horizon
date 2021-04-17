package io.horizon.ftdc.gateway.converter;

import java.util.function.Function;

import ctp.thostapi.CThostFtdcOrderActionField;
import io.horizon.ftdc.gateway.msg.rsp.FtdcOrderAction;

public class CThostFtdcOrderActionConverter implements Function<CThostFtdcOrderActionField, FtdcOrderAction> {

	@Override
	public FtdcOrderAction apply(CThostFtdcOrderActionField field) {
		return new FtdcOrderAction()

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

				.setActionDate(field.getActionDate())

				.setActionTime(field.getActionTime())

				.setTraderID(field.getTraderID())

				.setInstallID(field.getInstallID())

				.setOrderLocalID(field.getOrderLocalID())

				.setActionLocalID(field.getActionLocalID())

				.setParticipantID(field.getParticipantID())

				.setClientID(field.getClientID())

				.setBusinessUnit(field.getBusinessUnit())

				.setOrderActionStatus(field.getOrderActionStatus())

				.setUserID(field.getUserID())

				.setStatusMsg(field.getStatusMsg())

				.setInstrumentID(field.getInstrumentID())

				.setBranchID(field.getBranchID())

				.setInvestUnitID(field.getInvestUnitID())

				.setIPAddress(field.getIPAddress())

				.setMacAddress(field.getMacAddress());

	}

}
