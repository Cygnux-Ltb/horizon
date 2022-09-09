package io.horizon.trader.handler;

import io.horizon.market.data.MarketData;
import io.horizon.market.handler.MarketDataHandler;
import io.horizon.trader.transport.outbound.DtoAdaptorReport;
import io.horizon.trader.transport.outbound.DtoOrderReport;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.common.util.ResourceUtil;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;

/**
 * 处理Adaptor的入站信息接口
 *
 * @param <M>
 * @author yellow013
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
     * @param <M>
     * @author yellow013
     */
    final class InboundSchedulerWrapper<M extends MarketData> implements InboundHandler<M> {

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
            this.hasMarketDataHandler = marketDataHandler != null;
            this.orderReportHandler = orderReportHandler;
            this.hasOrderReportHandler = orderReportHandler != null;
            this.adaptorReportHandler = adaptorReportHandler;
            this.hasAdaptorReportHandler = adaptorReportHandler != null;
            this.logger = logger;
        }

        @Override
        public void onMarketData(@Nonnull M marketData) {
            if (hasMarketDataHandler)
                marketDataHandler.onMarketData(marketData);
        }

        @Override
        public void onOrderReport(@Nonnull DtoOrderReport report) {
            if (hasOrderReportHandler)
                orderReportHandler.onOrderReport(report);
        }

        @Override
        public void onAdaptorReport(@Nonnull DtoAdaptorReport report) {
            if (hasAdaptorReportHandler)
                adaptorReportHandler.onAdaptorReport(report);
        }

        @Override
        public void close() throws IOException {
            if (hasMarketDataHandler) {
                try {
                    ResourceUtil.close(marketDataHandler);
                } catch (Exception e) {
                    if (logger != null) {
                        logger.error("Close MarketDataHandler -> {} throw {}",
                                marketDataHandler.getClass().getSimpleName(),
                                e.getClass().getSimpleName(), e);
                    }
                }
            }
            if (hasOrderReportHandler) {
                try {
                    ResourceUtil.close(orderReportHandler);
                } catch (Exception e) {
                    if (logger != null)
                        logger.error("Close OrderReportHandler -> {} throw {}",
                                orderReportHandler.getClass().getSimpleName(),
                                e.getClass().getSimpleName(), e);
                }
            }
            if (hasAdaptorReportHandler) {
                try {
                    ResourceUtil.close(adaptorReportHandler);
                } catch (Exception e) {
                    if (logger != null)
                        logger.error("Close AdaptorReportHandler -> {} throw {}",
                                adaptorReportHandler.getClass().getSimpleName(),
                                e.getClass().getSimpleName(), e);
                }
            }
        }
    }

    /**
     * @param <M>
     * @author yellow013
     */
    final class InboundSchedulerLogger<M extends MarketData> implements InboundHandler<M> {

        private final Logger log;

        public InboundSchedulerLogger(Logger log) {
            this.log = log == null ? Log4j2LoggerFactory.getLogger(getClass()) : log;
        }

        @Override
        public void onMarketData(@Nonnull M marketData) {
            log.info("InboundSchedulerLogger record marketData -> {}", marketData);
        }

        @Override
        public void onOrderReport(@Nonnull DtoOrderReport report) {
            log.info("InboundSchedulerLogger record orderReport -> {}", report);
        }

        @Override
        public void onAdaptorReport(@Nonnull DtoAdaptorReport report) {
            log.info("InboundSchedulerLogger record adaptorReport -> {}", report);
        }

        @Override
        public void close() throws IOException {
            log.info("InboundSchedulerLogger has been closed");
        }

    }

}
