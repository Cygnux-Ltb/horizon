package io.gemini.definition.market.instrument.futures.impl;

import io.gemini.definition.market.instrument.Symbol;
import io.gemini.definition.market.instrument.futures.Futures;

public final class AmericaFutures extends Futures {

	public AmericaFutures(int instrumentId, String instrumentCode, Symbol symbol) {
		super(instrumentId, instrumentCode, symbol);
	}

	@Override
	public String fmtText() {
		return "";
	}

}
