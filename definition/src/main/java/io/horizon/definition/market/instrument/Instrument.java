package io.horizon.definition.market.instrument;

import io.mercury.common.fsm.Enable;

public interface Instrument extends Enable<Instrument> {

	int instrumentId();

	String instrumentCode();

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

	default String symbolCode() {
		return symbol().symbolCode();
	}

	default Exchange exchange() {
		return symbol().exchange();
	}

	default String exchangeCode() {
		return symbol().exchange().exchangeCode();
	}

	default PriceMultiplier getPriceMultiplier() {
		return symbol().getPriceMultiplier();
	}

	InstrumentType type();

	/**
	 * 是否立即可用
	 * @return
	 */
	default boolean isAvailableImmediately() {
		return true;
	}

	/**
	 * 优先平仓类型
	 * @return
	 */
	default PriorityClose getPriorityClose() {
		return PriorityClose.NONE;
	}

	public static enum PriorityClose {
		NONE, YESTERDAY, TODAY
	}

	String fmtText();

}
