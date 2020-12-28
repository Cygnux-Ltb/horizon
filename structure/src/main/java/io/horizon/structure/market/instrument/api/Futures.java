package io.horizon.structure.market.instrument.api;

import io.horizon.structure.market.instrument.AbsInstrument;
import io.horizon.structure.market.instrument.InstrumentType;
import io.horizon.structure.market.instrument.Symbol;

public abstract class Futures extends AbsInstrument {

	// symbol
	protected final Symbol symbol;

	/**
	 * 
	 * @param instrumentId
	 * @param instrumentCode
	 * @param symbol
	 */
	protected Futures(int instrumentId, String instrumentCode, Symbol symbol) {
		super(instrumentId, instrumentCode);
		this.symbol = symbol;
	}

	@Override
	public Symbol symbol() {
		return symbol;
	}

	@Override
	public InstrumentType type() {
		return InstrumentType.FUTURES;
	}

	@Override
	public boolean isAvailableImmediately() {
		return true;
	}

}
