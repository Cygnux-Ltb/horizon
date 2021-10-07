package io.horizon.trader.order.attr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrdLevel {

	Child('C'),

	Parent('P'),

	Strategy('S'),

	Group('G');

	@Getter
	private final char code;

}