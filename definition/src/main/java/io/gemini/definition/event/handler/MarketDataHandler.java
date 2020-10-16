package io.gemini.definition.event.handler;

import io.gemini.definition.market.data.api.MarketData;

@FunctionalInterface
public interface MarketDataHandler<M extends MarketData> {

	void onMarketData(M marketData);

}
