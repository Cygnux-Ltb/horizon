package io.horizon.trader.order.attr;

public enum TrdDirection {

	Invalid('I'),

	Long('L'),

	Short('S');

	private final char code;

	private TrdDirection(char code) {
		this.code = code;
	}

	public char getCode() {
		return code;
	}

}