package io.horizon.trader.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.trader.order.Order;

@FunctionalInterface
public interface OrderHandler {

	void onOrder(@Nonnull final Order order);

	/**
	 * Logger implements OrderHandler
	 * 
	 * @author yellow013
	 *
	 */
	public static class OrderLogger implements OrderHandler {

		private final Logger log;

		public OrderLogger(Logger log) {
			this.log = log;
		}

		@Override
		public void onOrder(@Nonnull final Order order) {
			log.info("OrderLogger record -> {}", order);
		}

	}

}
