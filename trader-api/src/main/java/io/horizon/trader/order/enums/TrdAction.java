package io.horizon.trader.order.enums;

import static io.horizon.trader.order.enums.TrdAction.TrdActionCode.CLOSE;
import static io.horizon.trader.order.enums.TrdAction.TrdActionCode.CLOSE_TODAY;
import static io.horizon.trader.order.enums.TrdAction.TrdActionCode.CLOSE_YESTERDAY;
import static io.horizon.trader.order.enums.TrdAction.TrdActionCode.INVALID;
import static io.horizon.trader.order.enums.TrdAction.TrdActionCode.OPEN;

import io.horizon.trader.transport.enums.TTrdAction;

public enum TrdAction {
	
	/**
	 * 无效
	 */
	Invalid(INVALID, TTrdAction.INVALID),

	/**
	 * 开仓
	 */
	Open(OPEN, TTrdAction.OPEN),

	/**
	 * 平仓
	 */
	Close(CLOSE, TTrdAction.CLOSE),

	/**
	 * 平今仓
	 */
	CloseToday(CLOSE_TODAY, TTrdAction.CLOSE_TODAY),

	/**
	 * 平昨仓
	 */
	CloseYesterday(CLOSE_YESTERDAY, TTrdAction.CLOSE_YESTERDAY),

	;

	private final char code;

	private final TTrdAction action;

	private TrdAction(char code, TTrdAction action) {
		this.code = code;
		this.action = action;
	}

	public char getCode() {
		return code;
	}

	public TTrdAction getTTrdAction() {
		return action;
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

	public static TrdAction valueOf(TTrdAction action) {
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

	public static interface TrdActionCode {
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