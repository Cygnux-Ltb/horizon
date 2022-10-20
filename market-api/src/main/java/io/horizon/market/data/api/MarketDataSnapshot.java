package io.horizon.market.data.api;

public interface MarketDataSnapshot extends MarketDataMessage {

    <R, I> R accept(Visitor<R, I> visitor, I input);

}
