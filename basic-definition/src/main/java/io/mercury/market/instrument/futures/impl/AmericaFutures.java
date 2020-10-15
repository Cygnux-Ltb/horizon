package io.mercury.market.instrument.futures.impl;

import io.mercury.market.instrument.Symbol;
import io.mercury.market.instrument.futures.api.Futures;

public final class AmericaFutures extends Futures {

	public AmericaFutures(int instrumentId, String instrumentCode, Symbol symbol) {
		super(instrumentId, instrumentCode, symbol);
	}

	@Override
	public String fmtText() {
		return "";
	}

}
