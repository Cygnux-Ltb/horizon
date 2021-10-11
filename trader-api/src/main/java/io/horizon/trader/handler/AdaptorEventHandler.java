package io.horizon.trader.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.trader.adaptor.AdaptorEvent;

@FunctionalInterface
public interface AdaptorEventHandler {

	void onAdaptorEvent(@Nonnull final AdaptorEvent event);

	/**
	 * Logger implements AdaptorEventHandler
	 * 
	 * @author yellow013
	 *
	 */

	public static class AdaptorEventLogger implements AdaptorEventHandler {

		private final Logger log;

		public AdaptorEventLogger(Logger log) {
			this.log = log;
		}

		@Override
		public void onAdaptorEvent(final AdaptorEvent event) {
			log.info("AdaptorEventLogger -> {}", event);
		}

	}

}
