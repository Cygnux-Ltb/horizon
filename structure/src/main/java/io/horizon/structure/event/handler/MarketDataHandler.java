package io.horizon.structure.event.handler;

import javax.annotation.Nonnull;

import io.horizon.structure.market.data.MarketData;

@FunctionalInterface
public interface MarketDataHandler<M extends MarketData> {

	void onMarketData(@Nonnull final M marketData);

}
