package io.gemini.definition.event;

import io.gemini.definition.event.handler.AdaptorEventHandler;
import io.gemini.definition.event.handler.MarketDataHandler;
import io.gemini.definition.event.handler.OrdReportHandler;
import io.gemini.definition.market.data.MarketData;

/**
 * 
 * 处理Adaptor的入站信息抽象
 * 
 * @author yellow013
 *
 * @param <M>
 */
public interface InboundScheduler<M extends MarketData>
		extends MarketDataHandler<M>, OrdReportHandler, AdaptorEventHandler {

}
