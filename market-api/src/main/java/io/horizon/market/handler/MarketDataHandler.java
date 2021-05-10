package io.horizon.market.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.market.data.MarketData;

@FunctionalInterface
public interface MarketDataHandler<M extends MarketData> {

	void onMarketData(@Nonnull final M marketData);

	/**
	 * Logger implements MarketDataHandler
	 * 
	 * @author yellow013
	 *
	 * @param <M>
	 */
	public static class MarketDataLogger<M extends MarketData> implements MarketDataHandler<M> {

		private final Logger log;

		public MarketDataLogger(Logger log) {
			this.log = log;
		}

		@Override
		public void onMarketData(final M marketData) {
			log.info("MarketDataLogger -> {}", marketData);
		}

	}

}
