package io.horizon.structure.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrdLevel {

	Group(1),

	Strategy(2),

	Parent(4),

	Child(8),

	;

	@Getter
	private final int code;

}
