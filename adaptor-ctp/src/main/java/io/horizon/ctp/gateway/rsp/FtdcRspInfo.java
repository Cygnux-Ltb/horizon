package io.horizon.ctp.gateway.rsp;

public record FtdcRspInfo(
		// 错误代码
		int errorId,
		// 错误信息
		String errorMsg,
		// 请求码
		int requestId) {
}
