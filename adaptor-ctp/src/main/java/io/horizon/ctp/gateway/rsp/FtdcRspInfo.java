package io.horizon.ctp.gateway.rsp;

public record FtdcRspInfo(
        // 错误代码
        int ErrorID,
        // 错误信息
        String ErrorMsg,
        // 请求码
        int RequestID) {
}
