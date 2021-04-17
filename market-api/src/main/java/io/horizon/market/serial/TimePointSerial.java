package io.horizon.market.serial;

import java.time.ZonedDateTime;

import io.mercury.common.datetime.EpochTime;
import io.mercury.common.sequence.Serial;
import io.mercury.common.util.Assertor;
import lombok.Getter;

/**
 * 时间点序列
 * 
 * @author yellow013
 */
public final class TimePointSerial implements Serial<TimePointSerial> {

	@Getter
	private final ZonedDateTime timePoint;
	
	@Getter
	private final long epochSecond;
	
	@Getter
	private final long repeat;
	
	@Getter
	private final long serialId;

	/**
	 * 根据固定时间创建新序列
	 * 
	 * @param timePoint
	 * @return
	 */
	public static TimePointSerial newSerial(ZonedDateTime timePoint) {
		Assertor.nonNull(timePoint, "timePoint");
		return new TimePointSerial(timePoint, 0);
	}

	/**
	 * 根据前一个序列创建新序列
	 * 
	 * @param previous
	 * @return
	 */
	public static TimePointSerial newWithPrevious(TimePointSerial previous) {
		Assertor.nonNull(previous, "previous");
		return new TimePointSerial(previous.timePoint, previous.repeat + 1);
	}

	private TimePointSerial(ZonedDateTime timePoint, long repeat) {
		this.timePoint = timePoint;
		this.epochSecond = timePoint.toEpochSecond();
		this.repeat = repeat;
		this.serialId = (epochSecond * 1000L) + repeat;
	}

	public static void main(String[] args) {

		ZonedDateTime now = ZonedDateTime.now();

		long epochSecond = now.toEpochSecond();
		System.out.println(epochSecond);

		TimePointSerial timeStarted0 = TimePointSerial.newSerial(now);
		System.out.println(timeStarted0.getTimePoint());
		System.out.println(timeStarted0.getEpochSecond());
		System.out.println(timeStarted0.getSerialId());

		TimePointSerial timeStarted1 = TimePointSerial.newWithPrevious(timeStarted0);
		System.out.println(timeStarted1.getTimePoint());
		System.out.println(timeStarted1.getEpochSecond());
		System.out.println(timeStarted1.getSerialId());

		System.out.println(EpochTime.millis());
		System.out.println(EpochTime.seconds());

		System.out.println(Long.MAX_VALUE);

	}

}
