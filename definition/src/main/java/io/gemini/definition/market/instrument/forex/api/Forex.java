package io.gemini.definition.market.instrument.forex.api;

import io.gemini.definition.market.instrument.AbsInstrument;
import io.gemini.definition.market.instrument.InstrumentType;
import io.gemini.definition.market.instrument.Symbol;

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
