package io.horizon.definition.event.handler;

import io.horizon.definition.market.data.MarketData;

@FunctionalInterface
public interface MarketDataHandler<M extends MarketData> {

	void onMarketData(M marketData);

}
