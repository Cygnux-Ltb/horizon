package io.horizon.trader.order.attr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TrdDirection {

	Invalid('I'),

	Long('L'),

	Short('S');

	@Getter
	private final char code;

}