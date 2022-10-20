package io.horizon.market.data.api;

public interface MarketDataReplaceOrder extends MarketDataEvent {

    double getPrice();

    double getQty();

    Side getSide();

    String getPrevOrderId();

    <R, I> R accept(Visitor<R, I> visitor, I input);

}
