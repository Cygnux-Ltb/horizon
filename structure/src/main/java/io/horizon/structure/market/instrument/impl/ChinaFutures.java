package io.horizon.structure.market.instrument.impl;

import io.horizon.structure.market.instrument.AbstractInstrument;
import lombok.Getter;

public final class ChinaFutures extends AbstractInstrument {

	@Getter
	private PriorityCloseType priorityCloseType;

	public ChinaFutures(ChinaFuturesSymbol symbol, int term) {
		this(symbol, term, String.valueOf(term));
	}

	public ChinaFutures(ChinaFuturesSymbol symbol, int term, String codeTail) {
		super(symbol.acquireInstrumentId(term), symbol.getSymbolCode() + codeTail, symbol);
		this.priorityCloseType = symbol.getPriorityCloseType();
	}

	@Override
	public boolean isAvailableImmediately() {
		return true;
	}

}
