package io.horizon.trader.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.trader.report.AdaptorReport;

@FunctionalInterface
public interface AdaptorReportHandler {

	void onAdaptorReport(@Nonnull final AdaptorReport report);

	/**
	 * Logger implements AdaptorEventHandler
	 * 
	 * @author yellow013
	 *
	 */

	public static class AdaptorReportLogger implements AdaptorReportHandler {

		private final Logger log;

		public AdaptorReportLogger(Logger log) {
			this.log = log;
		}

		@Override
		public void onAdaptorReport(final AdaptorReport report) {
			log.info("AdaptorEventLogger -> {}", report);
		}

	}

}
