package io.horizon.trader.order.enums;

public enum TrdAction {
	/**
	 * 无效
	 */
	Invalid(TrdActionCode.INVALID),

	/**
	 * 开仓
	 */
	Open(TrdActionCode.OPEN),

	/**
	 * 平仓
	 */
	Close(TrdActionCode.CLOSE),

	/**
	 * 平今仓
	 */
	CloseToday(TrdActionCode.CLOSE_TODAY),

	/**
	 * 平昨仓
	 */
	CloseYesterday(TrdActionCode.CLOSE_YESTERDAY),

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
		case TrdActionCode.OPEN:
			return Open;
		case TrdActionCode.CLOSE:
			return Close;
		case TrdActionCode.CLOSE_TODAY:
			return CloseToday;
		case TrdActionCode.CLOSE_YESTERDAY:
			return CloseYesterday;
		default:
			return Invalid;
		}
	}

	private interface TrdActionCode {
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