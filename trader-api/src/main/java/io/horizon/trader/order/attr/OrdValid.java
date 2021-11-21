package io.horizon.trader.order.attr;

public enum OrdValid {

	/**
	 * Good Till Cancel, 将一直有效, 直到交易员取消这个报单, 或者该合约本身到期的报单.
	 */
	GTC('C'),

	/**
	 * Good Till Date, 将一直有效, 直到指定日期或交易员取消这个报单, 或者该合约本身到期的报单.
	 */
	GTD('D'),

	/**
	 * Good For Day, 只在当日的交易时段有效, 一旦当前交易时段结束, 自动取消的报单.
	 */
	GFD('G'),

	;

	private final char code;

	private OrdValid(char code) {
		this.code = code;
	}

	/**
	 * @return OrdValid.GTC
	 */
	public static final OrdValid getDefault() {
		return OrdValid.GTC;
	}

	public char getCode() {
		return code;
	}

}
