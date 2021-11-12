package io.horizon.market.instrument.impl.stock;

import org.eclipse.collections.api.list.ImmutableList;

import io.horizon.market.instrument.Exchange;
import io.horizon.market.instrument.InstrumentType;
import io.horizon.market.instrument.PriceMultiplier;
import io.horizon.market.instrument.Symbol;
import io.horizon.market.instrument.TradablePeriod;
import io.horizon.market.instrument.impl.AbstractInstrument;

public abstract class AbstractStock extends AbstractInstrument implements Symbol {

	protected AbstractStock(int instrumentId, String instrumentCode, Exchange exchange, PriceMultiplier priceMultiplier,
			ImmutableList<TradablePeriod> tradablePeriods) {
		super(instrumentId, instrumentCode, exchange);
	}

	@Override
	public InstrumentType getType() {
		return InstrumentType.STOCK;
	}

	@Override
	public int getSymbolId() {
		return instrumentId;
	}

	@Override
	public String getSymbolCode() {
		return instrumentCode;
	}

	@Override
	public Symbol getSymbol() {
		return this;
	}

}
