package io.horizon.definition.event;

import io.horizon.definition.event.handler.AdaptorEventHandler;
import io.horizon.definition.event.handler.MarketDataHandler;
import io.horizon.definition.event.handler.OrdReportHandler;
import io.horizon.definition.market.data.MarketData;

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
