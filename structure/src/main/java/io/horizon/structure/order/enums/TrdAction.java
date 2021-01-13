package io.horizon.structure.order.enums;

public enum TrdAction {

	Invalid(-1),

	Open(1),

	Close(2),

	CloseToday(4),

	CloseYesterday(8),

	;

	public final int code;

	/**
	 * 
	 * @param code 代码
	 */
	private TrdAction(int code) {
		this.code = code;
	}

}
