package io.horizon.trader.order.attr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TrdDirection {

	Invalid(-1),

	Long(1),

	Short(2);

	@Getter
	private final int code;

}