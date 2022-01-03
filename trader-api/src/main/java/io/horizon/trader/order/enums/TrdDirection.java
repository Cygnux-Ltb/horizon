package io.horizon.trader.order.enums;

import io.horizon.trader.report.enums.EDirection;

public enum TrdDirection {

	Invalid(TrdDirectionCode.INVALID, EDirection.INVALID),

	Long(TrdDirectionCode.LONG, EDirection.LONG),

	Short(TrdDirectionCode.SHORT, EDirection.SHORT),

	;

	private final char code;

	private final EDirection edirection;

	private TrdDirection(char code, EDirection edirection) {
		this.code = code;
		this.edirection = edirection;
	}

	public char getCode() {
		return code;
	}

	public EDirection getEDirection() {
		return edirection;
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
	public static TrdDirection valueOf(EDirection direction) {
		switch (direction) {
		case LONG:
			return Long;
		case SHORT:
			return Short;
		default:
			return Invalid;
		}
	}

	private interface TrdDirectionCode {
		// 无效
		char INVALID = 'I';
		// 多
		char LONG = 'L';
		// 空
		char SHORT = 'S';
	}

}