package io.horizon.trader.order.attr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrdLevel {

	Child(1), Parent(2), Strategy(4), Group(8);

	@Getter
	private final int code;

}