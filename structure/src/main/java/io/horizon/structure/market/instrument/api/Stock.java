package io.horizon.structure.market.instrument.api;

import io.horizon.structure.market.instrument.AbsInstrument;
import io.horizon.structure.market.instrument.Exchange;
import io.horizon.structure.market.instrument.InstrumentType;
import io.horizon.structure.market.instrument.PriceMultiplier;
import io.horizon.structure.market.instrument.Symbol;

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

	@Override
	public String symbolCode() {
		return instrumentCode();
	}

	@Override
	public Exchange exchange() {
		return super.exchange();
	}

}
