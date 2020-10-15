package io.gemini.definition.event;

import io.gemini.definition.order.structure.OrdReport;

@FunctionalInterface
public interface OrdReportHandler {

	void onOrdReport(OrdReport ordReport);

}
