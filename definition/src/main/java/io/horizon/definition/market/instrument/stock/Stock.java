package io.horizon.definition.market.instrument.stock;

import io.horizon.definition.market.instrument.AbsInstrument;
import io.horizon.definition.market.instrument.InstrumentType;
import io.horizon.definition.market.instrument.PriceMultiplier;
import io.horizon.definition.market.instrument.Symbol;

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
