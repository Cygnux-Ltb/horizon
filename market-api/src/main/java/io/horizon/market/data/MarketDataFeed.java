package io.horizon.market.data;

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.Nonnull;

import io.horizon.market.instrument.Instrument;
import io.mercury.common.fsm.Enableable;
import io.mercury.common.lang.exception.ComponentStartupException;

public interface MarketDataFeed extends Closeable, Enableable {

	/**
	 *  启动函数
	 * 
	 * @return
	 * @throws IllegalStateException
	 */
	boolean startup() throws IOException, IllegalStateException, ComponentStartupException;

	/**
	 * 订阅行情
	 * 
	 * @param subscribeMarketData
	 * @return
	 */
	boolean subscribeMarketData(@Nonnull Instrument[] instruments);

}
