package io.gemini.definition.event;

import javax.annotation.Nonnull;

import io.gemini.definition.adaptor.Adaptor;
import io.gemini.definition.market.instrument.Instrument;

public interface MarketDataSubscriber {

	void subscribeMarketData(@Nonnull Instrument... instruments);

	void registerAdaptor(@Nonnull Adaptor adaptor);

}
