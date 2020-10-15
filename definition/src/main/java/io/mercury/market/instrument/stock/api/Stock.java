package io.mercury.market.instrument.stock.api;

import io.mercury.market.instrument.AbsInstrument;
import io.mercury.market.instrument.InstrumentType;
import io.mercury.market.instrument.PriceMultiplier;
import io.mercury.market.instrument.Symbol;

public abstract class Stock extends AbsInstrument implements Symbol {

	protected Stock(int instrumentId, String instrumentCode) {
		super(instrumentId, instrumentCode);
	}

	@Override
	public InstrumentType type() {
		return InstrumentType.STOCK;
	}

	@Override
	public PriceMultiplier getPriceMultiplier() {
		return super.getPriceMultiplier();
	}

	@Override
	public Symbol symbol() {
		return this;
	}

}
