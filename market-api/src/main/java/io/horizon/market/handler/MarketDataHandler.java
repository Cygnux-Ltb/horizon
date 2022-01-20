package io.horizon.market.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.market.api.MarketData;

@FunctionalInterface
public interface MarketDataHandler<M extends MarketData> {

	void onMarketData(@Nonnull final M marketData);

	/**
	 * 
	 * @author yellow013
	 *
	 * @param <M>
	 */
	public abstract class AbstractMarketDataHandler<M extends MarketData> implements MarketDataHandler<M> {

		protected M last;

		@Override
		public void onMarketData(M marketData) {
			onMarketData0(marketData);
			this.last = marketData;
		}

		protected abstract void onMarketData0(M marketData);

	}

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
			log.info("MarketData record -> {}", marketData);
		}

	}

}
