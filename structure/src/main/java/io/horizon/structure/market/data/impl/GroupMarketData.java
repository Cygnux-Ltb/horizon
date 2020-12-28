package io.horizon.definition.market.data.impl;

import org.eclipse.collections.api.set.MutableSet;

import io.horizon.definition.market.data.MarketData;

public final class GroupMarketData<M extends MarketData> {

	private MutableSet<M> marketDataSet;

	public GroupMarketData(MutableSet<M> marketDataSet) {
		this.marketDataSet = marketDataSet;
	}

	public MutableSet<M> getMarketDataSet() {
		return marketDataSet;
	}

}
