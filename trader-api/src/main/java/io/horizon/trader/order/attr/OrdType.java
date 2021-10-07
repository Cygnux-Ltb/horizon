package io.horizon.trader.order.attr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrdType {

	Invalid('I'), 
	
	Limit('L'), 
	
	Market('M'), 
	
	Stop('S'), 
	
	StopLimit('T'), 
	
	FOK('O'), 
	
	FAK('A');

	@Getter
	private final char code;

}
