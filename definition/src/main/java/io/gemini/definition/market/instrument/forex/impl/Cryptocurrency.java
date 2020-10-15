package io.gemini.definition.market.instrument.forex.impl;

import io.gemini.definition.market.instrument.Symbol;
import io.gemini.definition.market.instrument.forex.api.Forex;

public final class Cryptocurrency extends Forex {

	protected Cryptocurrency(int instrumentId, Symbol symbol) {
		super(instrumentId, symbol.code(), symbol);
	}

	@Override
	public String fmtText() {
		return null;
	}

}
