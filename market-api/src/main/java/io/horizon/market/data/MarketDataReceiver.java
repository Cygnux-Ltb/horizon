package io.horizon.market.data;

import javax.annotation.Nonnull;

import io.horizon.market.transport.inbound.MarketDataSubscribe;

public interface MarketDataReceiver {

    /**
     * 订阅行情
     *
     * @param subscribe MarketDataSubscribe
     * @return boolean
     */
    boolean subscribeMarketData(@Nonnull MarketDataSubscribe subscribe);

}
