package io.horizon.market.instrument.impl.futures;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.horizon.market.instrument.Exchange;
import io.horizon.market.instrument.InstrumentType;
import io.horizon.market.instrument.PriceMultiplier;
import io.horizon.market.instrument.Symbol;
import io.horizon.market.instrument.TradablePeriod;

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
	public ImmutableSortedSet<TradablePeriod> getTradablePeriodSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PriceMultiplier getPriceMultiplier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTickSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSeqWithinExchange() {
		// TODO Auto-generated method stub
		return 0;
	}

}
