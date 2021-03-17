package io.horizon.structure.event.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.structure.order.Order;
import lombok.RequiredArgsConstructor;

@FunctionalInterface
public interface OrderHandler {

	void onOrder(@Nonnull final Order order);

	/**
	 * 
	 * Logger implements AdaptorEventHandler
	 */
	@RequiredArgsConstructor
	public static class OrderLogger implements OrderHandler {

		private final Logger log;

		@Override
		public void onOrder(@Nonnull final Order order) {
			log.info("OrderLogger -> {}", order);
		}

	}

}
