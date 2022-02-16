package io.horizon.market.api;

import javax.annotation.Nonnull;

import io.horizon.market.transport.inbound.MarketDataSubscribe;

public interface MarketReceiver {

	/**
	 * 订阅行情
	 * 
	 * @param MarketDataSubscribe
	 * @return
	 */
	boolean subscribeMarketData(@Nonnull MarketDataSubscribe subscribe);

}
