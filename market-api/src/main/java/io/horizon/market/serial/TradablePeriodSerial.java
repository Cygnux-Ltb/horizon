package io.horizon.market.serial;

import static io.mercury.common.datetime.DateTimeUtil.currentDate;
import static io.mercury.common.datetime.DateTimeUtil.nextDate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import io.mercury.common.collections.MutableLists;
import io.mercury.common.datetime.TimeConst;
import io.mercury.common.datetime.TimeZone;
import io.mercury.common.sequence.Serial;
import io.mercury.common.util.Assertor;
import lombok.Getter;

/**
 * 指示某交易标的一段交易时间
 * 
 * @author yellow013
 */
public final class TradablePeriodSerial implements Serial {

	@Getter
	private LocalTime startTime;

	@Getter
	private int startSecondOfDay;

	@Getter
	private LocalTime endTime;

	@Getter
	private int endSecondOfDay;

	@Getter
	private Duration duration;

	private int serialId;

	private boolean isCrossDay;

	public TradablePeriodSerial(int serialId, LocalTime startTime, LocalTime endTime) {
		Assertor.nonNull(startTime, "startTime");
		Assertor.nonNull(endTime, "endTime");
		this.serialId = serialId;
		this.startTime = startTime;
		this.startSecondOfDay = startTime.toSecondOfDay();
		this.endTime = endTime;
		this.endSecondOfDay = endTime.toSecondOfDay();
		if (startSecondOfDay > endSecondOfDay) {
			isCrossDay = true;
			duration = Duration.ofSeconds(endSecondOfDay - startSecondOfDay + TimeConst.SECONDS_PER_DAY);
		} else {
			isCrossDay = false;
			duration = Duration.ofSeconds(endSecondOfDay - startSecondOfDay);
		}
	}

	@Override
	public long getSerialId() {
		return serialId;
	}

	public boolean isPeriod(LocalTime time) {
		int secondOfDay = time.toSecondOfDay();
		if (!isCrossDay)
			return (startSecondOfDay <= secondOfDay && endSecondOfDay >= secondOfDay) ? true : false;
		else
			return (startSecondOfDay <= secondOfDay || endSecondOfDay >= secondOfDay) ? true : false;
	}

	/**
	 * 分割交易时段
	 * 
	 * @param zoneId
	 * @param period
	 * @return
	 */
	public ImmutableList<TimePeriodSerial> segmentation(@Nonnull ZoneId zoneId, @Nonnull Duration duration) {
		// 获取分割参数的秒数
		int seconds = (int) duration.getSeconds();
		// 判断分割段是否大于半天
		if (seconds > TimeConst.SECONDS_PER_HALF_DAY) {
			// 如果交易周期跨天,则此分割周期等于当天开始时间至次日结束时间
			// 如果交易周期未跨天,则此分割周期等于当天开始时间至当天结束时间
			return MutableLists.newFastList(isCrossDay
					? new TimePeriodSerial(ZonedDateTime.of(currentDate(), startTime, zoneId),
							ZonedDateTime.of(nextDate(), endTime, zoneId), duration)
					: new TimePeriodSerial(ZonedDateTime.of(currentDate(), startTime, zoneId),
							ZonedDateTime.of(currentDate(), endTime, zoneId), duration))
					.toImmutable();
		} else {
			// 获取此交易时间段的总时长
			int totalSeconds = (int) duration.getSeconds();
			// 计算按照分割参数总的段数
			int count = totalSeconds / seconds;
			if (totalSeconds % seconds > 0)
				count++;
			MutableList<TimePeriodSerial> mutableList = MutableLists.newFastList(count);
			// 计算开始时间点
			ZonedDateTime startPoint = ZonedDateTime.of(currentDate(), startTime, zoneId);
			// 计算结束时间点,如果跨天则日期加一天
			ZonedDateTime lastEndPoint = ZonedDateTime.of(isCrossDay ? nextDate() : currentDate(), endTime, zoneId);
			for (int i = 0; i < count; i++) {
				ZonedDateTime nextStartPoint = startPoint.plusSeconds(seconds);
				if (nextStartPoint.isBefore(lastEndPoint)) {
					ZonedDateTime endPoint = nextStartPoint.minusNanos(1);
					mutableList.add(new TimePeriodSerial(startPoint, endPoint, duration));
				} else {
					mutableList.add(new TimePeriodSerial(startPoint, lastEndPoint, duration));
					break;
				}
				startPoint = nextStartPoint;
			}
			return mutableList.toImmutable();
		}
	}

	public static void main(String[] args) {

		TradablePeriodSerial tradingPeriod = new TradablePeriodSerial(0, LocalTime.of(21, 00, 00),
				LocalTime.of(2, 30, 00));

		System.out.println(tradingPeriod.isPeriod(LocalTime.of(14, 00, 00)));

		tradingPeriod.segmentation(TimeZone.CST, Duration.ofMinutes(45))
				.each(timePeriod -> System.out.println(timePeriod.getStartTime() + " - " + timePeriod.getEndTime()));

		LocalDateTime of = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 55, 30));

		System.out.println(of);
		System.out.println(of.plusMinutes(30));

	}

}
