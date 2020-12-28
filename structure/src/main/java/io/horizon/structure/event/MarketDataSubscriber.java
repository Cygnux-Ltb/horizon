package io.horizon.structure.event;

import javax.annotation.Nonnull;

import io.horizon.structure.adaptor.Adaptor;
import io.horizon.structure.market.instrument.Instrument;

public interface MarketDataSubscriber {

	void subscribeMarketData(@Nonnull Instrument... instruments);

	void registerAdaptor(@Nonnull Adaptor adaptor);

}
