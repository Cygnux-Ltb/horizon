package io.horizon.structure.event;

import io.horizon.structure.event.handler.AdaptorEventHandler;
import io.horizon.structure.event.handler.MarketDataHandler;
import io.horizon.structure.event.handler.OrderReportHandler;
import io.horizon.structure.market.data.MarketData;

/**
 * 
 * 处理Adaptor的入站信息抽象
 * 
 * @author yellow013
 *
 * @param <M>
 */
public interface InboundScheduler<M extends MarketData> extends
		// 行情处理器
		MarketDataHandler<M>,
		// 订单回报处理器
		OrderReportHandler,
		// Adaptor事件处理器
		AdaptorEventHandler {

}
