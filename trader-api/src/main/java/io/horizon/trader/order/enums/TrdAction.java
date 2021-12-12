package io.horizon.trader.order.enums;

public enum TrdAction {
	/**
	 * 无效
	 */
	Invalid(Code.INVALID),

	/**
	 * 开仓
	 */
	Open(Code.OPEN),

	/**
	 * 平仓
	 */
	Close(Code.CLOSE),

	/**
	 * 平今仓
	 */
	CloseToday(Code.CLOSE_TODAY),

	/**
	 * 平昨仓
	 */
	CloseYesterday(Code.CLOSE_YESTERDAY),

	;

	private final char code;

	private TrdAction(char code) {
		this.code = code;
	}

	public char getCode() {
		return code;
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public static TrdAction valueOf(int code) {
		switch (code) {
		case Code.OPEN:
			return Open;
		case Code.CLOSE:
			return Close;
		case Code.CLOSE_TODAY:
			return CloseToday;
		case Code.CLOSE_YESTERDAY:
			return CloseYesterday;
		default:
			return Invalid;
		}
	}

	private interface Code {
		// 无效
		char INVALID = 'I';
		// 开仓
		char OPEN = 'O';
		// 平仓
		char CLOSE = 'C';
		// 平今仓
		char CLOSE_TODAY = 'T';
		// 平昨仓
		char CLOSE_YESTERDAY = 'Y';
	}

}