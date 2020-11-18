package io.horizon.definition.event.handler;

import io.horizon.definition.order.structure.OrdReport;

@FunctionalInterface
public interface OrdReportHandler {

	void onOrdReport(OrdReport report);

}
