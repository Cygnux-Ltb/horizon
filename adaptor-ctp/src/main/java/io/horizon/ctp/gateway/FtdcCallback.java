package io.horizon.ctp.gateway;

import ctp.thostapi.CThostFtdcRspInfoField;
import io.horizon.ctp.gateway.msg.FtdcRspMsg;
import io.horizon.ctp.gateway.rsp.FtdcRspInfo;
import io.mercury.common.functional.Handler;
import io.mercury.common.log4j2.Log4j2LoggerFactory;
import org.slf4j.Logger;

public class FtdcCallback {

    protected static final Logger log = Log4j2LoggerFactory.getLogger(FtdcCallback.class);

    // RSP消息处理器
    protected final Handler<FtdcRspMsg> handler;

    protected FtdcCallback(Handler<FtdcRspMsg> handler) {
        this.handler = handler;
    }

    /**
     * 错误推送回调
     *
     * @param field     CThostFtdcRspInfoField
     * @param RequestID int
     * @param isLast    boolean
     */
    void onRspError(CThostFtdcRspInfoField field, int RequestID, boolean isLast) {
        log.error("FtdcCallback::onRspError -> ErrorID==[{}], ErrorMsg==[{}]",
                field.getErrorID(), field.getErrorMsg());
        handler.handle(FtdcRspMsg.with(
                new FtdcRspInfo(field.getErrorID(), field.getErrorMsg(), RequestID), isLast));
    }

}
