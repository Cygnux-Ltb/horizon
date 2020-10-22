package io.gemini.definition.event;

import io.gemini.definition.adaptor.AdaptorEvent;
import io.gemini.definition.market.data.MarketData;
import io.gemini.definition.order.structure.OrdReport;

public interface InboundScheduler<M extends MarketData> {

	void onMarketData(M marketData);

	void onOrdReport(OrdReport report);

	void onAdaptorEvent(AdaptorEvent event);

}
