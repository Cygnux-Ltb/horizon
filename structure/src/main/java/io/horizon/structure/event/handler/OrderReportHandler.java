package io.horizon.structure.event.handler;

import javax.annotation.Nonnull;

import io.horizon.structure.order.OrderReport;

@FunctionalInterface
public interface OrderReportHandler {

	void onOrderReport(@Nonnull final OrderReport report);

}
