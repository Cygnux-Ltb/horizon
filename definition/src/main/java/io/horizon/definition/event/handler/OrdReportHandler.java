package io.gemini.definition.event.handler;

import io.gemini.definition.order.structure.OrdReport;

@FunctionalInterface
public interface OrdReportHandler {

	void onOrdReport(OrdReport report);

}
