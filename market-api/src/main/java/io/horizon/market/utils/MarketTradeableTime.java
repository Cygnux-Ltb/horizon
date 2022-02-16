package io.horizon.market.global;

import static io.mercury.common.datetime.pattern.DateTimePattern.YYYY_MM_DD_HH_MM_SS_SSSSSS;

import java.time.LocalDateTime;

import org.slf4j.Logger;

import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.common.thread.ScheduleTaskExecutor;

public final class MarketTradeableTime {

	private static final Logger log = Log4j2LoggerFactory.getLogger(MarketTradeableTime.class);

	public static void registerCloseTime(LocalDateTime datetime) {
		log.info("Register next close time -> [{}]", YYYY_MM_DD_HH_MM_SS_SSSSSS.format(datetime));
		ScheduleTaskExecutor.singleThreadSchedule(datetime, () -> {
			log.info("Execution close function, current time -> [{}]",
					YYYY_MM_DD_HH_MM_SS_SSSSSS.format(LocalDateTime.now()));
			System.exit(0);
		});
	}

}
