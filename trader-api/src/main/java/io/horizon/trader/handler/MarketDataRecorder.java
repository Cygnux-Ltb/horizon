package io.horizon.trader.handler;

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.market.data.MarketData;
import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.handler.MarketDataHandler;
import io.horizon.market.instrument.Instrument;
import io.horizon.trader.adaptor.Adaptor;
import io.horizon.trader.adaptor.AdaptorEvent;
import io.mercury.common.log.CommonLoggerFactory;

/**
 * 
 * 行情记录器接口
 * 
 * @author yellow013
 *
 * @param <M>
 */
public interface MarketDataRecorder<M extends MarketData> extends MarketDataHandler<M>, AdaptorEventHandler, Closeable {

	MarketDataRecorder<M> addAdaptor(@Nonnull final Adaptor adaptor);

	/**
	 * 
	 * MarketDataRecorder base implements
	 * 
	 * @author yellow013
	 *
	 * @param <M>
	 */
	public static abstract class AbstractMarketDataRecorder<M extends MarketData> implements MarketDataRecorder<M> {

		private static final Logger log = CommonLoggerFactory.getLogger(AbstractMarketDataRecorder.class);

		protected final Instrument[] instruments;

		protected Adaptor adaptor;

		protected AbstractMarketDataRecorder(@Nonnull Instrument... instruments) {
			this.instruments = instruments;
		}

		@Override
		public void onAdaptorEvent(AdaptorEvent event) {
			log.info("Received event -> {}", event);
			switch (event.getStatus()) {
			case MdEnable:
				if (adaptor != null)
					adaptor.subscribeMarketData(instruments);
				else
					throw new IllegalStateException("adaptor is null");
				break;
			case MdDisable:
				if (adaptor != null)
					log.info("Adaptor -> {} market data is disable", adaptor.getAdaptorId());
				else
					throw new IllegalStateException("adaptor is null");
				break;
			default:
				log.warn("Event no processing, AdaptorEvent -> {}", event);
				break;
			}
		}

		@Override
		public MarketDataRecorder<M> addAdaptor(Adaptor adaptor) {
			this.adaptor = adaptor;
			return this;
		}

	}

	/**
	 * 
	 * @author yellow013
	 *
	 */
	public static class LoggerMarketDataRecorder extends AbstractMarketDataRecorder<BasicMarketData> {

		private static final Logger log = CommonLoggerFactory.getLogger(LoggerMarketDataRecorder.class);

		public LoggerMarketDataRecorder(Instrument... instruments) {
			super(instruments);
		}

		@Override
		public void onMarketData(BasicMarketData marketData) {
			log.info("MarketData Recorde -> {}", marketData);
		}

		@Override
		public void close() throws IOException {
			log.info("LoggerMarketDataRecorder is closed");
		}

	}

}
