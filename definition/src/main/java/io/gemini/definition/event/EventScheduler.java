package io.gemini.definition.event;

import io.gemini.definition.adaptor.AdaptorEvent;
import io.gemini.definition.market.data.api.MarketData;
import io.gemini.definition.order.structure.OrdReport;

public interface EventScheduler<M extends MarketData> {

	void onMarketData(M marketData);

	void onOrdReport(OrdReport report);

	void onAdaptorEvent(AdaptorEvent event);

}
