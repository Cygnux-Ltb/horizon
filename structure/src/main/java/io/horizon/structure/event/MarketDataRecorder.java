package io.horizon.structure.event;

import javax.annotation.Nonnull;

import io.horizon.structure.adaptor.Adaptor;
import io.horizon.structure.event.handler.AdaptorEventHandler;
import io.horizon.structure.event.handler.MarketDataHandler;
import io.horizon.structure.market.data.MarketData;
import io.horizon.structure.market.instrument.Instrument;

public interface MarketDataRecorder<M extends MarketData> extends AdaptorEventHandler, MarketDataHandler<M> {

	void subscribeMarketData(@Nonnull Instrument... instruments);

	void registerAdaptor(@Nonnull Adaptor adaptor);

}
