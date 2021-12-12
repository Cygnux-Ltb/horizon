package io.horizon.trader.order.enums;

public enum OrdLevel {

	/**
	 * 子订单
	 */
	Child('C'),

	/**
	 * 父订单
	 */
	Parent('P'),

	/**
	 * 策略订单
	 */
	Strategy('S'),

	/**
	 * 组订单
	 */
	Group('G');

	private final char code;

	private OrdLevel(char code) {
		this.code = code;
	}

	public char getCode() {
		return code;
	}

}