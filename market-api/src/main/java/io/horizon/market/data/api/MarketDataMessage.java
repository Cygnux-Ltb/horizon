package org.dev4fx.marketdata.model.api;

import java.util.List;

public interface MarketDataMessage extends Visitable {

    long getTriggerTimestamp();

    long getEventTimestamp();

    List<? extends MarketDataEvent> getEvents();

}
