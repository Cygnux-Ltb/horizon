package io.horizon.structure.event.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.structure.adaptor.AdaptorEvent;
import lombok.RequiredArgsConstructor;

@FunctionalInterface
public interface AdaptorEventHandler {

	void onAdaptorEvent(@Nonnull final AdaptorEvent event);

	/**
	 * 
	 * Logger implements AdaptorEventHandler
	 */
	@RequiredArgsConstructor
	public static class AdaptorEventLogger implements AdaptorEventHandler {

		private final Logger log;

		@Override
		public void onAdaptorEvent(AdaptorEvent event) {
			log.info("AdaptorEventLogger -> {}", event);
		}

	}

}
