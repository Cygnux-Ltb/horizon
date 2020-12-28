package io.horizon.structure.market.instrument.impl;

import io.horizon.structure.market.instrument.Symbol;
import io.horizon.structure.market.instrument.api.Forex;

public final class Cryptocurrency extends Forex {

	protected Cryptocurrency(int instrumentId, Symbol symbol) {
		super(instrumentId, symbol.symbolCode(), symbol);
	}



}
