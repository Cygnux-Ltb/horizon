package io.horizon.definition.market.instrument.futures.impl;

import io.horizon.definition.market.instrument.futures.Futures;

public final class ChinaFutures extends Futures {

	private PriorityCloseType priorityCloseType;

	public ChinaFutures(ChinaFuturesSymbol symbol, int term) {
		this(symbol, term, String.valueOf(term));
	}

	public ChinaFutures(ChinaFuturesSymbol symbol, int term, String codeTail) {
		super(symbol.acquireInstrumentId(term), symbol.symbolCode() + codeTail, symbol);
		this.priorityCloseType = symbol.getPriorityCloseType();
	}

	@Override
	public PriorityCloseType getPriorityCloseType() {
		return priorityCloseType;
	}

}
