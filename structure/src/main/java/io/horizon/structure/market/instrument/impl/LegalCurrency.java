package io.horizon.structure.market.instrument.impl;

import io.horizon.structure.market.instrument.Symbol;
import io.horizon.structure.market.instrument.api.Forex;

public class LegalCurrency extends Forex {

	protected LegalCurrency(int instrumentId, String instrumentCode, Symbol symbol) {
		super(instrumentId, instrumentCode, symbol);
	}

}
