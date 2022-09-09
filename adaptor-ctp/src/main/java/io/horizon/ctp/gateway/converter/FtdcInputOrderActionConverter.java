package io.horizon.ctp.gateway.converter;

import java.util.function.Function;

import ctp.thostapi.CThostFtdcInputOrderActionField;
import io.horizon.ctp.gateway.rsp.FtdcInputOrderAction;

@FunctionalInterface
public interface FtdcInputOrderActionConverter extends Function<CThostFtdcInputOrderActionField, FtdcInputOrderAction> {

	FtdcInputOrderAction convertFromFtdcStructure(CThostFtdcInputOrderActionField field);

	@Override
	default FtdcInputOrderAction apply(CThostFtdcInputOrderActionField field) {
		return convertFromFtdcStructure(field);
	}

}
