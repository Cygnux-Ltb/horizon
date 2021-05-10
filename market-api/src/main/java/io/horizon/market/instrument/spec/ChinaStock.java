package io.horizon.market.instrument.impl;

import io.horizon.market.instrument.AbstractInstrument;
import io.horizon.market.instrument.InstrumentType;
import io.horizon.market.instrument.PriceMultiplier;
import io.horizon.market.instrument.Symbol;

public final class ChinaStock extends AbstractInstrument {

	protected ChinaStock(int instrumentId, String instrumentCode, Symbol symbol) {
		super(instrumentId, instrumentCode, symbol);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isAvailableImmediately() {
		return false;
	}

	@Override
	public String format() {
		return super.format();
	}

	@Override
	public PriceMultiplier getPriceMultiplier() {
		return PriceMultiplier.TEN_THOUSAND;
	}

	@Override
	public String getSymbolCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstrumentType getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
