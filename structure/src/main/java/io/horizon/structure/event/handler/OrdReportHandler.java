package io.horizon.structure.event.handler;

import javax.annotation.Nonnull;

import io.horizon.structure.order.OrdReport;

@FunctionalInterface
public interface OrdReportHandler {

	void onOrdReport(@Nonnull final OrdReport report);

}
