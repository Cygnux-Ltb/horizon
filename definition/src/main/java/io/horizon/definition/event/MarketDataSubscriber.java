package io.horizon.definition.event;

import javax.annotation.Nonnull;

import io.horizon.definition.adaptor.Adaptor;
import io.horizon.definition.market.instrument.Instrument;

public interface MarketDataSubscriber {

	void subscribeMarketData(@Nonnull Instrument... instruments);

	void registerAdaptor(@Nonnull Adaptor adaptor);

}
