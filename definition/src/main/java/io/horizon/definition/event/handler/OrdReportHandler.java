package io.horizon.definition.event.handler;

import javax.annotation.Nonnull;

import io.horizon.definition.order.OrdReport;

@FunctionalInterface
public interface OrdReportHandler {

	void onOrdReport(@Nonnull final OrdReport report);

}
