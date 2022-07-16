package io.horizon.trader.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.trader.transport.outbound.AdaptorReport;
import io.mercury.common.log.Log4j2LoggerFactory;

@FunctionalInterface
public interface AdaptorReportHandler {

    void onAdaptorReport(@Nonnull final AdaptorReport report);

    /**
     * Logger implements AdaptorEventHandler
     *
     * @author yellow013
     */
    class AdaptorReportLogger implements AdaptorReportHandler {

        private final Logger log;

        public AdaptorReportLogger(Logger log) {
            this.log = log == null ? Log4j2LoggerFactory.getLogger(getClass()) : log;
        }

        @Override
        public void onAdaptorReport(@Nonnull final AdaptorReport report) {
            log.info("AdaptorEventLogger -> {}", report);
        }

    }

}
