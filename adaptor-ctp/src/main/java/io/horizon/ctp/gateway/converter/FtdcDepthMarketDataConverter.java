package io.horizon.ctp.gateway.converter;

import java.util.function.Function;

import ctp.thostapi.CThostFtdcDepthMarketDataField;
import io.horizon.ctp.gateway.rsp.FtdcDepthMarketData;

@FunctionalInterface
public interface FtdcDepthMarketDataConverter extends Function<CThostFtdcDepthMarketDataField, FtdcDepthMarketData> {

	FtdcDepthMarketData convertToFtdcDepthMarketData(CThostFtdcDepthMarketDataField field);

	@Override
	default FtdcDepthMarketData apply(CThostFtdcDepthMarketDataField field) {
		return convertToFtdcDepthMarketData(field);
	}

}
