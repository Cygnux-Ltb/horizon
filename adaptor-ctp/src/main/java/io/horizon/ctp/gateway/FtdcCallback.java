package io.horizon.ctp.gateway;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcRspInfoField;
import io.horizon.ctp.gateway.rsp.FtdcRspInfo;
import io.mercury.common.functional.Handler;
import io.mercury.common.log.Log4j2LoggerFactory;

public class FtdcCallback {

	private static final Logger log = Log4j2LoggerFactory.getLogger(FtdcCallback.class);

	// RSP消息处理器
	protected final Handler<FtdcRspMsg> handler;

	FtdcCallback(Handler<FtdcRspMsg> handler) {
		this.handler = handler;
	}

	/**
	 * 错误推送回调
	 * 
	 * @param field
	 */
	void onRspError(CThostFtdcRspInfoField field, int requestID, boolean isLast) {
		log.error("FtdcCallback onRspError -> ErrorID==[{}], ErrorMsg==[{}]", field.getErrorID(), field.getErrorMsg());
		handler.handle(
				new FtdcRspMsg(new FtdcRspInfo().setErrorID(field.getErrorID()).setErrorMsg(field.getErrorMsg())));
	}

}
