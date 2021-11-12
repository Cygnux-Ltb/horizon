package io.horizon.market.instrument.impl.stock;

import org.eclipse.collections.api.list.ImmutableList;

import io.horizon.market.instrument.Exchange;
import io.horizon.market.instrument.PriceMultiplier;
import io.horizon.market.instrument.Symbol;
import io.horizon.market.instrument.TradablePeriod;

public class ChinaStock extends AbstractStock implements Symbol {

	protected ChinaStock(int instrumentId, String instrumentCode, Exchange exchange, PriceMultiplier priceMultiplier,
			ImmutableList<TradablePeriod> tradablePeriods) {
		super(instrumentId, instrumentCode, exchange, priceMultiplier, tradablePeriods);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getTickSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ImmutableList<TradablePeriod> getTradablePeriods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PriceMultiplier getPriceMultiplier() {
		// TODO Auto-generated method stub
		return null;
	}

}
