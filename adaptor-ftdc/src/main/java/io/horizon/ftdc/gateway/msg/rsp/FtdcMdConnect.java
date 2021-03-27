package io.horizon.ftdc.gateway.msg.rsp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class FtdcMdConnect {

	private final boolean Available;

	public boolean isAvailable() {
		return Available;
	}

}
