package io.horizon.ftdc.gateway.bean;

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

}
