package io.horizon.definition.event.handler;

import javax.annotation.Nonnull;

import io.horizon.definition.market.data.MarketData;

@FunctionalInterface
public interface MarketDataHandler<M extends MarketData> {

	void onMarketData(@Nonnull final M marketData);

}
