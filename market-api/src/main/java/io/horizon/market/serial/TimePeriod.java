package io.horizon.market.serial;

import java.time.Duration;
import java.time.ZonedDateTime;

import javax.annotation.Nonnull;

import io.mercury.common.sequence.Serial;
import io.mercury.common.util.Assertor;

/**
 * 时间周期序列
 * 
 * @author yellow013
 */
public final class TimePeriodSerial implements Serial {

	private final long epochSecond;

	private final Duration duration;

	private final ZonedDateTime startTime;

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

	public long getEpochSecond() {
		return epochSecond;
	}

	public Duration getDuration() {
		return duration;
	}

	public ZonedDateTime getStartTime() {
		return startTime;
	}

	public ZonedDateTime getEndTime() {
		return endTime;
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
