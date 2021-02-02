package io.horizon.structure.event.handler;

import javax.annotation.Nonnull;

import io.horizon.structure.order.OrderReport;

@FunctionalInterface
public interface OrdReportHandler {

	void onOrdReport(@Nonnull final OrderReport report);

}
