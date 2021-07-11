package io.horizon.trader.event.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.trader.order.OrderReport;
import lombok.RequiredArgsConstructor;

@FunctionalInterface
public interface OrderReportHandler {

	void onOrderReport(@Nonnull final OrderReport report);

	/**
	 * Logger implements AdaptorEventHandler
	 * 
	 * @author yellow013
	 *
	 */
	@RequiredArgsConstructor
	public static class OrderReportLogger implements OrderReportHandler {

		private final Logger log;

		@Override
		public void onOrderReport(final OrderReport report) {
			log.info("OrderReportLogger -> {}", report);
		}

	}

}
