package io.horizon.structure.order.enums;

public enum OrdLevel {

	Group(1),

	Strategy(2),

	Parent(4),

	Child(8),

	;

	public final int code;

	private OrdLevel(int code) {
		this.code = code;
	}

}
