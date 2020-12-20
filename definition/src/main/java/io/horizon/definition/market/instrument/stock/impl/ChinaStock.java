package io.horizon.definition.market.instrument.stock.impl;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.horizon.definition.market.instrument.Exchange;
import io.horizon.definition.market.instrument.PriceMultiplier;
import io.horizon.definition.market.instrument.stock.Stock;
import io.horizon.definition.vector.TradingPeriod;

public final class ChinaStock extends Stock {

	protected ChinaStock(int instrumentId, String instrumentCode) {
		super(instrumentId, instrumentCode);
	}

	@Override
	public boolean isAvailableImmediately() {
		return false;
	}

	@Override
	public String format() {
		return super.format();
	}

	@Override
	public PriceMultiplier getPriceMultiplier() {
		return PriceMultiplier.TEN_THOUSAND;
	}

	@Override
	public ImmutableSortedSet<TradingPeriod> getTradingPeriodSet() {
		// TODO 添加固定的股票交易时间
		return null;
	}

	@Override
	public Exchange exchange() {
		return Exchange.SHFE;
	}

	@Override
	public int symbolId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
