package io.horizon.structure.order.enums;

import lombok.Getter;

public enum OrdLevel {

	Group(1),

	Strategy(2),

	Parent(4),

	Child(8),

	;

	@Getter
	private final int code;

	private OrdLevel(int code) {
		this.code = code;
	}

}
