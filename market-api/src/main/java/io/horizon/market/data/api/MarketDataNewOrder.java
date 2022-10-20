package org.dev4fx.marketdata.model.api;

public interface MarketDataNewOrder extends MarketDataEvent {

    double getPrice();

    double getQty();

    Side getSide();

    <R, I> R accept(Visitor<R, I> visitor, I input);

}
