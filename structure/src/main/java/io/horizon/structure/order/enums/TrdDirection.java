package io.horizon.structure.order.enums;

import lombok.Getter;

public enum TrdDirection {

	Invalid(-1),

	Long(1),

	Short(2),

	;

	@Getter
	private final int code;

	/**
	 * 
	 * @param code 代码
	 */
	private TrdDirection(int code) {
		this.code = code;
	}

}
