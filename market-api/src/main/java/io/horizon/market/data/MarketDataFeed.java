package io.horizon.market.data;

import io.horizon.market.instrument.Instrument;
import io.mercury.common.fsm.Enableable;
import io.mercury.common.lang.exception.ComponentStartupException;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;

public interface MarketDataFeed extends Closeable, Enableable {

    /**
     * 启动函数
     *
     * @return boolean
     */
    boolean startup() throws IOException, IllegalStateException, ComponentStartupException;

    /**
     * 订阅行情
     *
     * @param instruments Instrument[]
     */
    boolean subscribeMarketData(@Nonnull Instrument[] instruments);

}
