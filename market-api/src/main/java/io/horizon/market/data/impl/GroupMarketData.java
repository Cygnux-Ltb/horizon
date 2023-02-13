package io.horizon.market.data.impl;

import io.horizon.market.data.MarketData;
import org.eclipse.collections.api.set.MutableSet;

public record GroupMarketData<M extends MarketData>(
        MutableSet<M> marketDataSet) {

}
