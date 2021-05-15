package io.horizon.market.serial;

import java.time.Duration;
import java.time.ZonedDateTime;

import javax.annotation.Nonnull;

import io.mercury.common.sequence.Serial;
import io.mercury.common.util.Assertor;
import lombok.Getter;

/**
 * 时间周期序列
 * 
 * @author yellow013
 */
public final class TimePeriodSerial implements Serial {

	@Getter
	private final long epochSecond;

	@Getter
	private final Duration duration;

	@Getter
	private final ZonedDateTime startTime;

	@Getter
	private final ZonedDateTime endTime;

	public TimePeriodSerial(@Nonnull ZonedDateTime startTime, @Nonnull ZonedDateTime endTime,
			@Nonnull Duration duration) {
		Assertor.nonNull(startTime, "startTime");
		Assertor.nonNull(endTime, "endTime");
		Assertor.nonNull(duration, "duration");
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.epochSecond = startTime.toEpochSecond();
	}

	@Override
	public long getSerialId() {
		return epochSecond;
	}

	public boolean isPeriod(ZonedDateTime time) {
		return startTime.isBefore(time) && endTime.isAfter(time) ? true : false;
	}

	private String toStringCache;

	@Override
	public String toString() {
		if (toStringCache == null)
			toStringCache = epochSecond + " -> [" + startTime.getZone() + "][" + startTime.toLocalDateTime() + " - "
					+ endTime.toLocalDateTime() + "]";
		return toStringCache;
	}

}
