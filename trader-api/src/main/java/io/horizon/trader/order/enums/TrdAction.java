package io.horizon.trader.order.enums;

import io.horizon.trader.report.enums.EAction;

public enum TrdAction {
	/**
	 * 无效
	 */
	Invalid(TrdActionCode.INVALID, EAction.INVALID),

	/**
	 * 开仓
	 */
	Open(TrdActionCode.OPEN, EAction.OPEN),

	/**
	 * 平仓
	 */
	Close(TrdActionCode.CLOSE, EAction.CLOSE),

	/**
	 * 平今仓
	 */
	CloseToday(TrdActionCode.CLOSE_TODAY, EAction.CLOSE_TODAY),

	/**
	 * 平昨仓
	 */
	CloseYesterday(TrdActionCode.CLOSE_YESTERDAY, EAction.CLOSE_YESTERDAY),

	;

	private final char code;

	private final EAction eaction;

	private TrdAction(char code, EAction eaction) {
		this.code = code;
		this.eaction = eaction;
	}

	public char getCode() {
		return code;
	}

	public EAction getEAction() {
		return eaction;
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

	public static TrdAction valueOf(EAction action) {
		switch (action) {
		case OPEN:
			return Open;
		case CLOSE:
			return Close;
		case CLOSE_TODAY:
			return CloseToday;
		case CLOSE_YESTERDAY:
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