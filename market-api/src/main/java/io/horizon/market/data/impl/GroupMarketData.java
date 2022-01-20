package io.horizon.market.data.impl;

import org.eclipse.collections.api.set.MutableSet;

import io.horizon.market.api.MarketData;

public final class GroupMarketData<M extends MarketData> {

	private MutableSet<M> marketDataSet;

	public GroupMarketData(MutableSet<M> marketDataSet) {
		this.marketDataSet = marketDataSet;
	}

	public MutableSet<M> getMarketDataSet() {
		return marketDataSet;
	}

}
