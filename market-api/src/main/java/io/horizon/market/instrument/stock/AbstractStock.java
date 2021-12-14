package io.horizon.market.instrument.stock;

import org.eclipse.collections.api.list.ImmutableList;

import io.horizon.market.instrument.AbstractInstrument;
import io.horizon.market.instrument.Exchange;
import io.horizon.market.instrument.InstrumentType;
import io.horizon.market.instrument.PriceMultiplier;
import io.horizon.market.instrument.Symbol;
import io.horizon.market.instrument.TradablePeriod;

public abstract class AbstractStock extends AbstractInstrument implements Symbol {

	private final int tickSize;

	protected AbstractStock(int instrumentId, String instrumentCode, Exchange exchange, PriceMultiplier priceMultiplier,
			int tickSize, ImmutableList<TradablePeriod> tradablePeriods) {
		super(instrumentId, instrumentCode, exchange);
		this.tickSize = tickSize;
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

	@Override
	public int getTickSize() {
		return tickSize;
	}

	@Override
	public PriceMultiplier getMultiplier() {
		// TODO Auto-generated method stub
		return null;
	}

}
