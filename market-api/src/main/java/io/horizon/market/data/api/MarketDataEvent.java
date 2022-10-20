package org.dev4fx.marketdata.model.api;

public interface MarketDataEvent extends Visitable {

    String getOrderId();

    String getInstrument();

    String getMarket();

}
