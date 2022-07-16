package io.horizon.trader.handler;

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;

import io.horizon.market.data.MarketData;
import io.horizon.market.handler.MarketDataHandler;
import io.horizon.trader.transport.outbound.AdaptorReport;
import io.horizon.trader.transport.outbound.OrderReport;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.common.util.ResourceUtil;

/**
 * 
 * 处理Adaptor的入站信息接口
 * 
 * @author yellow013
 *
 * @param <M>
 */
public interface InboundHandler<M extends MarketData> extends
		// 行情处理器
		MarketDataHandler<M>,
		// 订单回报处理器
		OrderReportHandler,
		// Adaptor事件处理器
		AdaptorReportHandler,
		// 用于清理资源
		Closeable {

	/**
	 * 
	 * @author yellow013
	 *
	 * @param <M>
	 */
	public static final class InboundSchedulerWrapper<M extends MarketData> implements InboundHandler<M> {

		private final MarketDataHandler<M> marketDataHandler;
		private final OrderReportHandler orderReportHandler;
		private final AdaptorReportHandler adaptorReportHandler;

		private final boolean hasMarketDataHandler;
		private final boolean hasOrderReportHandler;
		private final boolean hasAdaptorReportHandler;

		private final Logger logger;

		public InboundSchedulerWrapper(@Nullable MarketDataHandler<M> marketDataHandler,
				@Nullable OrderReportHandler orderReportHandler, @Nullable AdaptorReportHandler adaptorReportHandler,
				@Nullable Logger logger) {
			this.marketDataHandler = marketDataHandler;
			this.orderReportHandler = orderReportHandler;
			this.adaptorReportHandler = adaptorReportHandler;
			this.hasMarketDataHandler = marketDataHandler != null;
			this.hasOrderReportHandler = orderReportHandler != null;
			this.hasAdaptorReportHandler = adaptorReportHandler != null;
			this.logger = logger;
		}

		@Override
		public void onMarketData(M marketData) {
			if (hasMarketDataHandler)
				marketDataHandler.onMarketData(marketData);
		}

		@Override
		public void onOrderReport(OrderReport report) {
			if (hasOrderReportHandler)
				orderReportHandler.onOrderReport(report);
		}

		@Override
		public void onAdaptorReport(@Nonnull AdaptorReport report) {
			if (hasAdaptorReportHandler)
				adaptorReportHandler.onAdaptorReport(report);
		}

		@Override
		public void close() throws IOException {
			try {
				ResourceUtil.close(marketDataHandler);
			} catch (Exception e) {
				if (logger != null)
					logger.error("Close MarketDataHandler -> {} throw {}", marketDataHandler.getClass().getSimpleName(),
							e.getClass().getSimpleName(), e);
			}
			try {
				ResourceUtil.close(orderReportHandler);
			} catch (Exception e) {
				if (logger != null)
					logger.error("Close OrderReportHandler -> {} throw {}",
							orderReportHandler.getClass().getSimpleName(), e.getClass().getSimpleName(), e);
			}
			try {
				ResourceUtil.close(adaptorReportHandler);
			} catch (Exception e) {
				if (logger != null)
					logger.error("Close AdaptorReportHandler -> {} throw {}",
							adaptorReportHandler.getClass().getSimpleName(), e.getClass().getSimpleName(), e);
			}
		}
	}

	/**
	 * 
	 * @author yellow013
	 *
	 * @param <M>
	 */
	public static final class InboundSchedulerLogger<M extends MarketData> implements InboundHandler<M> {

		private final Logger log;

		public InboundSchedulerLogger(Logger log) {
			this.log = log == null ? Log4j2LoggerFactory.getLogger(getClass()) : log;
		}

		@Override
		public void onMarketData(M marketData) {
			log.info("InboundSchedulerLogger record marketData -> {}", marketData);
		}

		@Override
		public void onOrderReport(OrderReport report) {
			log.info("InboundSchedulerLogger record orderReport -> {}", report);
		}

		@Override
		public void onAdaptorReport(@Nonnull AdaptorReport report) {
			log.info("InboundSchedulerLogger record adaptorReport -> {}", report);
		}

		@Override
		public void close() throws IOException {
			log.info("InboundSchedulerLogger has been closed");
		}

	}

}
