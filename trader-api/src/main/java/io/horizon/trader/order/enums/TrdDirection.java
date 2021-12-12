package io.horizon.trader.order.enums;

public enum TrdDirection {

	Invalid(Code.INVALID),

	Long(Code.LONG),

	Short(Code.SHORT),

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
		case Code.LONG:
			return Long;
		case Code.SHORT:
			return Short;
		default:
			return Invalid;
		}
	}

	private interface Code {
		// 无效
		char INVALID = 'I';
		// 多
		char LONG = 'L';
		// 空
		char SHORT = 'S';
	}

}