package io.horizon.market.instrument.futures;

import io.horizon.market.instrument.AbstractInstrument;
import io.horizon.market.instrument.InstrumentType;
import io.horizon.market.instrument.Symbol;

public abstract class AbstractFutures extends AbstractInstrument {

	private Symbol symbol;

	protected AbstractFutures(int instrumentId, String instrumentCode, Symbol symbol) {
		super(instrumentId, instrumentCode, symbol.getExchange());
		this.symbol = symbol;
	}

	@Override
	public InstrumentType getType() {
		return InstrumentType.FUTURES;
	}

	@Override
	public Symbol getSymbol() {
		return symbol;
	}

}
