package io.horizon.definition.market.instrument;

import io.mercury.common.fsm.Enable;

public interface Instrument extends Enable<Instrument>, FinancialObj {

	/**
	 * STOCK : exchange|symbol<br>
	 * MAX_VALUE == 214|7483647<br>
	 * 
	 * FUTURES:exchange|symbol|term<br>
	 * MAX_VALUE == 214| 74 |83647<br>
	 * 
	 * @return int
	 */
	Symbol symbol();

	default Exchange exchange() {
		return symbol().exchange();
	}

	default PriceMultiplier getPriceMultiplier() {
		return symbol().getPriceMultiplier();
	}

	InstrumentType type();

	boolean isAvailableImmediately();

	default PriorityClose getPriorityClose() {
		return PriorityClose.NONE;
	}

	public static enum PriorityClose {
		NONE, YESTERDAY, TODAY
	}

	String fmtText();

}
