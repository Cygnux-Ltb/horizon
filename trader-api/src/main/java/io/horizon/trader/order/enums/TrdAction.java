package io.horizon.trader.order.attr;

public enum TrdAction {

	Invalid('I'),

	Open('O'),

	Close('C'),

	CloseToday('T'),

	CloseYesterday('Y');

	private final char code;

	private TrdAction(char code) {
		this.code = code;
	}

	public char getCode() {
		return code;
	}

}