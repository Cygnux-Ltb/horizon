package io.horizon.trader.order.enums;

import io.horizon.trader.transport.enums.TTrdDirection;

public enum TrdDirection {

	Invalid(TrdDirectionCode.INVALID, TTrdDirection.INVALID),

	Long(TrdDirectionCode.LONG, TTrdDirection.LONG),

	Short(TrdDirectionCode.SHORT, TTrdDirection.SHORT),

	;

	private final char code;

	private final TTrdDirection direction;

	private TrdDirection(char code, TTrdDirection direction) {
		this.code = code;
		this.direction = direction;
	}

	public char getCode() {
		return code;
	}

	public TTrdDirection getTTrdDirection() {
		return direction;
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public static TrdDirection valueOf(int code) {
		switch (code) {
		case TrdDirectionCode.LONG:
			return Long;
		case TrdDirectionCode.SHORT:
			return Short;
		default:
			return Invalid;
		}
	}

	/**
	 * 
	 * @param direction
	 * @return
	 */
	public static TrdDirection valueOf(TTrdDirection direction) {
		switch (direction) {
		case LONG:
			return Long;
		case SHORT:
			return Short;
		default:
			return Invalid;
		}
	}

	public static interface TrdDirectionCode {
		// 无效
		char INVALID = 'I';
		// 多
		char LONG = 'L';
		// 空
		char SHORT = 'S';
	}

}