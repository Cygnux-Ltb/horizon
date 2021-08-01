package io.horizon.ftdc.gateway.msg.rsp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public final class FtdcTraderConnect {

	private final boolean Available;

	private int FrontID;
	private int SessionID;

}
