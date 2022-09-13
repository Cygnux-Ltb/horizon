package io.horizon.ctp.gateway.handler;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcRspInfoField;
import io.mercury.common.log.Log4j2LoggerFactory;

public final class FtdcRspInfoHandler {

    private static final Logger log = Log4j2LoggerFactory.getLogger(FtdcRspInfoHandler.class);

    public static boolean hasError(String func, CThostFtdcRspInfoField field) {
        if (field != null && field.getErrorID() != 0) {
            log.error("{} -> ErrorID == [{}], ErrorMsg == [{}]",
                    func, field.getErrorID(), field.getErrorMsg());
            return true;
        } else
            return false;
    }

}