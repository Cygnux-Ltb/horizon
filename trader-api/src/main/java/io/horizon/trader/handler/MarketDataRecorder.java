package io.horizon.trader.handler;

import io.horizon.market.data.MarketData;
import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.instrument.Instrument;
import io.horizon.trader.adaptor.Adaptor;
import io.horizon.trader.transport.outbound.TdxAdaptorReport;
import io.horizon.trader.transport.outbound.TdxOrderReport;
import io.mercury.common.log.Log4j2LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;

/**
 * 行情记录器接口
 *
 * @param <M>
 * @author yellow013
 */
public interface MarketDataRecorder<M extends MarketData> extends InboundHandler<M>, Closeable {

    void setAdaptor(@Nonnull final Adaptor adaptor);

    /**
     * MarketDataRecorder base implements
     *
     * @param <M>
     * @author yellow013
     */
    abstract class BaseMarketDataRecorder<M extends MarketData> implements MarketDataRecorder<M> {

        private static final Logger log = Log4j2LoggerFactory.getLogger(BaseMarketDataRecorder.class);

        protected final Instrument[] instruments;

        protected Adaptor adaptor;

        protected BaseMarketDataRecorder(@Nonnull Instrument[] instruments) {
            this.instruments = instruments;
        }

        @Override
        public void onAdaptorReport(@Nonnull TdxAdaptorReport event) {
            log.info("Received event -> {}", event);
            if (adaptor == null) {
                throw new IllegalStateException("adaptor is null");
            }
            switch (event.getStatus()) {
                case MD_ENABLE -> adaptor.subscribeMarketData(instruments);
                case MD_DISABLE -> log.info("Adaptor -> {} market data is disable", adaptor.getAdaptorId());
                default -> log.warn("Event no processing, AdaptorEvent -> {}", event);
            }
        }

        @Override
        public void onOrderReport(@Nonnull TdxOrderReport report) {
            log.info("Ignored order report -> {}", report);
        }

        @Override
        public void setAdaptor(@Nonnull Adaptor adaptor) {
            if (this.adaptor == null) {
                this.adaptor = adaptor;
            } else {
                throw new IllegalStateException("Adaptor repeat setting.");
            }
        }

    }

    /**
     * @author yellow013
     */
    class LoggerMarketDataRecorder extends BaseMarketDataRecorder<BasicMarketData> {

        private final Logger log = Log4j2LoggerFactory.getLogger(getClass());

        public LoggerMarketDataRecorder(Instrument[] instruments) {
            super(instruments);
        }

        @Override
        public void onMarketData(@Nonnull BasicMarketData marketData) {
            log.info("LoggerMarketDataRecorder written -> {}", marketData);
        }

        @Override
        public void close() throws IOException {
            log.info("LoggerMarketDataRecorder is closed");
        }

    }

}
