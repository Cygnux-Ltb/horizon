package io.horizon.trader.handler;

import io.horizon.trader.transport.outbound.TdxAdaptorReport;
import io.mercury.common.log4j2.Log4j2LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface AdaptorReportHandler {

    void onAdaptorReport(@Nonnull final TdxAdaptorReport report);

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
        public void onAdaptorReport(@Nonnull final TdxAdaptorReport report) {
            log.info("AdaptorEventLogger -> {}", report);
        }

    }

}
