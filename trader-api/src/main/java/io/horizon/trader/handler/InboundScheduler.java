package io.horizon.trader.handler;

import java.io.Closeable;

import io.horizon.market.data.MarketData;
import io.horizon.market.handler.MarketDataHandler;

/**
 * 
 * 处理Adaptor的入站信息接口
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
		AdaptorEventHandler,
		// 用于清理资源
		Closeable {

}
