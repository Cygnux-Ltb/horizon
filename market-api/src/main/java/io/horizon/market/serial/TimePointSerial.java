package io.horizon.market.serial;

import java.time.ZonedDateTime;

import io.mercury.common.datetime.EpochUtil;
import io.mercury.common.sequence.Serial;
import io.mercury.common.util.Assertor;

/**
 * 时间点序列
 * 
 * @author yellow013
 */
public final class TimePointSerial implements Serial {

	private final ZonedDateTime timePoint;

	private final long epochSecond;

	private final long repeat;

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

	public ZonedDateTime getTimePoint() {
		return timePoint;
	}

	public long getEpochSecond() {
		return epochSecond;
	}

	public long getRepeat() {
		return repeat;
	}

	public long getSerialId() {
		return serialId;
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

		System.out.println(EpochUtil.getEpochMillis());
		System.out.println(EpochUtil.getEpochSeconds());

		System.out.println(Long.MAX_VALUE);

	}

}
