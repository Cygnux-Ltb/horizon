package io.horizon.ctp.gateway.handler;

import ctp.thostapi.CThostFtdcRspInfoField;
import io.mercury.common.log.Log4j2LoggerFactory;
import org.slf4j.Logger;

public final class FtdcRspInfoHandler {

    private static final Logger log = Log4j2LoggerFactory.getLogger(FtdcRspInfoHandler.class);

    public static boolean hasError(String errFunc, CThostFtdcRspInfoField field) {
        if (field != null && field.getErrorID() != 0) {
            log.error("Error func : {}, ErrorID == [{}], ErrorMsg == [{}]",
                    errFunc, field.getErrorID(), field.getErrorMsg());
            return true;
        } else
            return false;
    }

}