package io.horizon.definition.market.instrument.forex.impl;

import io.horizon.definition.market.instrument.Symbol;
import io.horizon.definition.market.instrument.forex.Forex;

public class LegalCurrency extends Forex {

	protected LegalCurrency(int instrumentId, String instrumentCode, Symbol symbol) {
		super(instrumentId, instrumentCode, symbol);
	}

	@Override
	public String fmtText() {
		return null;
	}

}
