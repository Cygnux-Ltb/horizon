package io.gemini.definition.event;

import io.gemini.definition.market.instrument.Instrument;

@FunctionalInterface
public interface MarketDataSubscriber {

	void subscribeMarketData(Instrument... instruments);

}
