package io.horizon.ctp.gateway.rsp;

public record FtdcTraderConnect(
		// 可用状态
		boolean available,
		// 前置机ID
		int frontId,
		// 会话ID
		int sessionId) {

}
