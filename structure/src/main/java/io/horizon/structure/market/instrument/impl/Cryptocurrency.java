package io.horizon.definition.market.instrument.forex.impl;

import io.horizon.definition.market.instrument.Symbol;
import io.horizon.definition.market.instrument.forex.Forex;

public final class Cryptocurrency extends Forex {

	protected Cryptocurrency(int instrumentId, Symbol symbol) {
		super(instrumentId, symbol.symbolCode(), symbol);
	}



}
