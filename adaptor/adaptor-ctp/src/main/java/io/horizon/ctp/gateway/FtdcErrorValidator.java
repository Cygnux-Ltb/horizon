package io.horizon.ctp.gateway;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcRspInfoField;
import io.mercury.common.log.CommonLoggerFactory;

public final class FtdcErrorValidator {

	private static final Logger log = CommonLoggerFactory.getLogger(FtdcErrorValidator.class);

	public static final boolean hasError(String func, CThostFtdcRspInfoField Field) {
		if (Field != null && Field.getErrorID() != 0) {
			log.error("{} -> ErrorID == [{}], ErrorMsg == [{}]", func, Field.getErrorID(), Field.getErrorMsg());
			return true;
		} else {
			return false;
		}

	}

}
