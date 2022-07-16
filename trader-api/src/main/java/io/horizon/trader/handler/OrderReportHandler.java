package io.horizon.trader.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.trader.transport.outbound.OrderReport;
import io.mercury.common.log.Log4j2LoggerFactory;

@FunctionalInterface
public interface OrderReportHandler {

    void onOrderReport(@Nonnull final OrderReport report);

    /**
     * Logger implements AdaptorEventHandler
     *
     * @author yellow013
     */
    class OrderReportLogger implements OrderReportHandler {

        private final Logger log;

        public OrderReportLogger(Logger log) {
            this.log = log == null ? Log4j2LoggerFactory.getLogger(getClass()) : log;
        }

        @Override
        public void onOrderReport(@Nonnull final OrderReport report) {
            log.info("OrderReportLogger -> {}", report);
        }

    }

}
