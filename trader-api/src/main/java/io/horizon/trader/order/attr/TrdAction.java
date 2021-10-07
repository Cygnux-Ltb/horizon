package io.horizon.trader.order.attr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TrdAction {

	Invalid('I'),

	Open('O'),

	Close('C'),

	CloseToday('T'),

	CloseYesterday('Y');

	@Getter
	private final char code;

}