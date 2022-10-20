package org.dev4fx.marketdata.model.api;

public interface MarketDataIncrement extends MarketDataMessage {

    <R, I> R accept(Visitor<R, I> visitor, I input);

}
