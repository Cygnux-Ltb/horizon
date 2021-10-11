package io.horizon.trader.order.attr;

public enum OrdType {

	Invalid('I'),

	Limit('L'),

	Market('M'),

	Stop('S'),

	StopLimit('T'),

	FOK('O'),

	FAK('A');

	private final char code;

	private OrdType(char code) {
		this.code = code;
	}

	public char getCode() {
		return code;
	}

}
