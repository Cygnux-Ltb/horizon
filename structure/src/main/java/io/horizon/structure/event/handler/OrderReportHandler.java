package io.horizon.structure.event.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.structure.order.OrderReport;
import lombok.RequiredArgsConstructor;

@FunctionalInterface
public interface OrderReportHandler {

	void onOrderReport(@Nonnull final OrderReport report);

	/**
	 * 
	 * Logger implements AdaptorEventHandler
	 */
	@RequiredArgsConstructor
	public static class OrderReportLogger implements OrderReportHandler {

		private final Logger log;

		@Override
		public void onOrderReport(OrderReport report) {
			log.info("OrderReportLogger -> {}", report);
		}

	}

}
