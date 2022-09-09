package io.horizon.market.handler;

import io.horizon.market.data.MarketData;
import io.mercury.common.log.Log4j2LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

@FunctionalInterface
public interface MarketDataHandler<M extends MarketData> {

    void onMarketData(@Nonnull final M marketData);

    /**
     * @param <M>
     * @author yellow013
     */
    @NotThreadSafe
    abstract class BaseMarketDataHandler<M extends MarketData> implements MarketDataHandler<M> {

        protected M last;

        @Override
        public void onMarketData(@Nonnull M marketData) {
            onMarketData0(marketData);
            this.last = marketData;
        }

        protected abstract void onMarketData0(M marketData);

    }

    /**
     * Logger implements MarketDataHandler
     *
     * @param <M>
     * @author yellow013
     */
    class MarketDataLogger<M extends MarketData> implements MarketDataHandler<M> {

        private final Logger log;

        public MarketDataLogger(Logger log) {
            this.log = log == null ?
                    Log4j2LoggerFactory.getLogger(getClass()) : log;
        }

        @Override
        public void onMarketData(@Nonnull final M marketData) {
            log.info("Received MarketData -> {}", marketData);
        }

    }

}
