package io.horizon.trader.event.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.trader.order.Order;
import lombok.RequiredArgsConstructor;

@FunctionalInterface
public interface OrderHandler {

	void onOrder(@Nonnull final Order order);

	/**
	 * Logger implements AdaptorEventHandler
	 * 
	 * @author yellow013
	 *
	 */
	@RequiredArgsConstructor
	public static class OrderLogger implements OrderHandler {

		private final Logger log;

		@Override
		public void onOrder(@Nonnull final Order order) {
			log.info("OrderLogger record -> {}", order);
		}

	}

}
