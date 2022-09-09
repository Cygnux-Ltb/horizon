package io.horizon.trader.handler;

import io.horizon.trader.transport.outbound.DtoOrderReport;
import io.mercury.common.log.Log4j2LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface OrderReportHandler {

    void onOrderReport(@Nonnull final DtoOrderReport report);

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
        public void onOrderReport(@Nonnull final DtoOrderReport report) {
            log.info("OrderReportLogger -> {}", report);
        }

    }

}
