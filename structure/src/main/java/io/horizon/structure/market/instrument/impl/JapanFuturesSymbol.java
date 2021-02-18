package io.horizon.structure.market.instrument.impl;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.horizon.structure.market.instrument.Exchange;
import io.horizon.structure.market.instrument.InstrumentType;
import io.horizon.structure.market.instrument.PriceMultiplier;
import io.horizon.structure.market.instrument.Symbol;
import io.horizon.structure.serial.TradablePeriodSerial;

public enum JapanFuturesSymbol implements Symbol {

	// TODO

	;

	@Override
	public String format() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSymbolId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSymbolCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exchange getExchange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InstrumentType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImmutableSortedSet<TradablePeriodSerial> getTradablePeriodSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PriceMultiplier getPriceMultiplier() {
		// TODO Auto-generated method stub
		return null;
	}

}
