package io.horizon.market.instrument.impl;

import io.horizon.market.instrument.Exchange;
import io.horizon.market.instrument.InstrumentType;
import io.horizon.market.instrument.Symbol;

public abstract class AbstractFutures extends AbstractInstrument {

	private Symbol symbol;

	protected AbstractFutures(int instrumentId, String instrumentCode, Symbol symbol, Exchange exchange) {
		super(instrumentId, instrumentCode, exchange);
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
