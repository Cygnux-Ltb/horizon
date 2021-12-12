package io.horizon.ctp.gateway.base;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcRspInfoField;
import io.mercury.common.log.CommonLoggerFactory;

public final class FtdcErrorValidator {

	private static final Logger log = CommonLoggerFactory.getLogger(FtdcErrorValidator.class);

	public static final boolean hasError(String spiFuncName, CThostFtdcRspInfoField ftdcRspInfo) {
		if (ftdcRspInfo != null && ftdcRspInfo.getErrorID() != 0) {
			log.error("SPI Error -> {} : ErrorID == [{}], ErrorMsg == [{}]", spiFuncName, ftdcRspInfo.getErrorID(),
					ftdcRspInfo.getErrorMsg());
			return true;
		} else {
			return false;
		}

	}

}
