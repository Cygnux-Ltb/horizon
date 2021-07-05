package io.horizon.trader.order.attr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrdType {

	Invalid(-1), 
	
	Limit(1), 
	
	Market(2), 
	
	Stop(4), 
	
	StopLimit(8), 
	
	FOK(16), 
	
	FAK(32);

	@Getter
	private final int code;

}
