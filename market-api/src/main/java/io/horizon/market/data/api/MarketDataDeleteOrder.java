package org.dev4fx.marketdata.model.api;

public interface MarketDataDeleteOrder extends MarketDataEvent {

    <R, I> R accept(Visitor<R, I> visitor, I input);

}
