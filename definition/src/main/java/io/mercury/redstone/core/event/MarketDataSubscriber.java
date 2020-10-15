package io.mercury.redstone.core.event;

import io.mercury.market.instrument.Instrument;

@FunctionalInterface
public interface MarketDataSubscriber {

	void subscribeMarketData(Instrument... instruments);

}
