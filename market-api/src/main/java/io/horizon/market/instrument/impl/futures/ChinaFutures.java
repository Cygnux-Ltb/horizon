package io.horizon.market.instrument.impl.futures;

import io.horizon.market.instrument.impl.AbstractInstrument;

public final class ChinaFutures extends AbstractInstrument {

	private final PriorityCloseType priorityCloseType;

	/**
	 * 
	 * @param symbol
	 * @param term
	 */
	public ChinaFutures(ChinaFuturesSymbol symbol, int term) {
		this(symbol, term, String.valueOf(term));
	}

	/**
	 * 
	 * @param symbol
	 * @param term
	 * @param codeTail
	 */
	public ChinaFutures(ChinaFuturesSymbol symbol, int term, String codeTail) {
		super(symbol.acquireInstrumentId(term), symbol.getSymbolCode() + codeTail, symbol);
		this.priorityCloseType = symbol.getPriorityCloseType();
	}

	@Override
	public PriorityCloseType getPriorityCloseType() {
		return priorityCloseType;
	}

	@Override
	public boolean isAvailableImmediately() {
		return true;
	}

}
