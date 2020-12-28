package io.horizon.structure.market.instrument.impl;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.horizon.structure.market.instrument.Exchange;
import io.horizon.structure.market.instrument.PriceMultiplier;
import io.horizon.structure.market.instrument.api.Stock;
import io.horizon.structure.vector.TradingPeriod;

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
