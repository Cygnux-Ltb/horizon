package io.mercury.redstone.core.event;

import io.mercury.market.data.api.MarketData;

@FunctionalInterface
public interface MarketDataHandler<M extends MarketData> {

	void onMarketData(M marketData);

}
