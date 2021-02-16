package io.horizon.structure.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TrdAction {

	Invalid(-1),

	Open(1),

	Close(2),

	CloseToday(4),

	CloseYesterday(8),

	;

	@Getter
	private final int code;

}
