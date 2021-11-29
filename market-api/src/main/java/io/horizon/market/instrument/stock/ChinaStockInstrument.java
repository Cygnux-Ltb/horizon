package io.horizon.market.instrument.stock;

import org.eclipse.collections.api.list.ImmutableList;

import io.horizon.market.instrument.Exchange;
import io.horizon.market.instrument.PriceMultiplier;
import io.horizon.market.instrument.Symbol;
import io.horizon.market.instrument.TradablePeriod;

public class ChinaStockInstrument extends AbstractStock implements Symbol {

	public ChinaStockInstrument(int instrumentId, String instrumentCode, Exchange exchange,
			PriceMultiplier priceMultiplier, ImmutableList<TradablePeriod> tradablePeriods) {
		super(instrumentId, instrumentCode, exchange, priceMultiplier, 1, tradablePeriods);
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
