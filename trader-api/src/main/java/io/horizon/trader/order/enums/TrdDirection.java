package io.horizon.trader.order.enums;

public enum TrdDirection {

	Invalid(TrdDirectionCode.INVALID),

	Long(TrdDirectionCode.LONG),

	Short(TrdDirectionCode.SHORT),

	;

	private final char code;

	private TrdDirection(char code) {
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

	private interface TrdDirectionCode {
		// 无效
		char INVALID = 'I';
		// 多
		char LONG = 'L';
		// 空
		char SHORT = 'S';
	}

}