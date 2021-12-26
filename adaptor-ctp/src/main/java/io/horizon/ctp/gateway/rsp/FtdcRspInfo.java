package io.horizon.ctp.gateway.rsp;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public final class FtdcRspInfo {

	/// 错误代码
	private int ErrorID;

	/// 错误信息
	private String ErrorMsg;

	/// 请求码
	private int requestId;

}