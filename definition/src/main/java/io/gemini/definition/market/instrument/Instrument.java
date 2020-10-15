package io.gemini.definition.market.instrument;

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

	InstrumentType type();

	boolean isAvailableImmediately();

	default PriorityClose getPriorityClose() {
		return PriorityClose.NONE;
	}

	default PriceMultiplier getPriceMultiplier() {
		return symbol().getPriceMultiplier();
	}

	public static enum PriorityClose {
		NONE, YESTERDAY, TODAY
	}

	String fmtText();

}
