package io.horizon.definition.event.handler;

import io.horizon.definition.order.OrdReport;

@FunctionalInterface
public interface OrdReportHandler {

	void onOrdReport(OrdReport report);

}
