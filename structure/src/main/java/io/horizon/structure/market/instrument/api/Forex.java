package io.horizon.structure.market.instrument.api;

import io.horizon.structure.market.instrument.AbsInstrument;
import io.horizon.structure.market.instrument.InstrumentType;
import io.horizon.structure.market.instrument.Symbol;

public abstract class Forex extends AbsInstrument {

	private Symbol symbol;

	protected Forex(int instrumentId, String instrumentCode, Symbol symbol) {
		super(instrumentId, instrumentCode);
		this.symbol = symbol;
	}

	@Override
	public InstrumentType type() {
		return InstrumentType.FOREX;
	}

	@Override
	public boolean isAvailableImmediately() {
		return true;
	}

	@Override
	public Symbol symbol() {
		return symbol;
	}

}
