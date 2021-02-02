package io.horizon.structure.order.enums;

import lombok.Getter;

public enum TrdAction {

	Invalid(-1),

	Open(1),

	Close(2),

	CloseToday(4),

	CloseYesterday(8),

	;

	@Getter
	private final int code;

	/**
	 * 
	 * @param code 代码
	 */
	private TrdAction(int code) {
		this.code = code;
	}

}
