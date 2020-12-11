package io.horizon.definition.order.enums;

public enum OrdLevel {

	Group(1),

	Strategy(1 << 1),

	Parent(1 << 2),

	Child(1 << 3),

	;

	private final int code;

	private OrdLevel(int code) {
		this.code = code;
	}

	public int code() {
		return code;
	}

}
