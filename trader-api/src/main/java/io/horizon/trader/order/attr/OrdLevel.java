package io.horizon.trader.order.attr;

public enum OrdLevel {

	Child('C'),

	Parent('P'),

	Strategy('S'),

	Group('G');

	private final char code;

	private OrdLevel(char code) {
		this.code = code;
	}

	public char getCode() {
		return code;
	}

}