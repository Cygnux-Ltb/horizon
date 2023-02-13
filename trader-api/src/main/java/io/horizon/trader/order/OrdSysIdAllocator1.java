package io.horizon.trader.order;

import static io.mercury.common.datetime.TimeConst.MILLIS_PER_SECONDS;

import java.time.Instant;
import java.time.ZonedDateTime;

import javax.annotation.concurrent.NotThreadSafe;

import io.mercury.common.datetime.EpochTime;
import io.mercury.common.datetime.TimeConst;
import io.mercury.common.datetime.TimeZone;
import io.mercury.common.sequence.SnowflakeAlgo;
import io.mercury.common.util.BitFormatter;

/**
 * 
 * Generate规则<br>
 * <br>
 * A方案<br>
 * 1.获取当前epoch秒<br>
 * 2.如果是同一秒内生成的两个id, 则自增位加一<br>
 * <br>
 * B方案<br>
 * 1.使用一个固定日期作为基准<br>
 * 2.使用一个较长的自增位<br>
 * <br>
 * C方案<br>
 * 1.使用位运算合并long类型, 分配64位<br>
 * 2.最高位使用strategyId <br>
 * <br>
 * 当前实现为方案A<br>
 * 
 * @author yellow013
 * @creation 2019年4月13日
 */
@NotThreadSafe
@Deprecated
public final class OrdSysIdAllocator1 {

	@SuppressWarnings("unused")
	private static final SnowflakeAlgo[] algorithms = new SnowflakeAlgo[1024];

	/**
	 * 
	 * @param strategyId min value 1 max value 900
	 * @return long
	 */
	public static long allocate(int strategyId) {
		if (strategyId < 0 || strategyId > 1023)
			throw new RuntimeException("strategyId is illegal");
		return generate(strategyId);
	}

	public static long allocateWithExternal() {
		return generate(0);
	}

	private static volatile int increment;
	private static volatile long lastUseEpochSeconds;

	private static long generate(int highPos) {
		long epochSeconds = EpochTime.getEpochSeconds();
		if (epochSeconds != lastUseEpochSeconds) {
			lastUseEpochSeconds = epochSeconds;
			increment = 0;
		}
		return highPos * 10_000_000_000_000_000L + lastUseEpochSeconds * 1_000_000L + ++increment;
	}

	/**
	 * 
	 */
	private static final long BASELINE_2020_SECONDS = 1577836800L;
	private static final long BASELINE_2020_MILLIS = BASELINE_2020_SECONDS * MILLIS_PER_SECONDS;

	/**
	 * 
	 */
	private static final long BASELINE_2010_SECONDS = 1262304000L;
	private static final long BASELINE_2010_MILLIS = BASELINE_2010_SECONDS * TimeConst.MILLIS_PER_SECONDS;

	/**
	 * 
	 */
	private static final long BASELINE_2000_SECONDS = 946684800L;
	private static final long BASELINE_2000_MILLIS = BASELINE_2000_SECONDS * TimeConst.MILLIS_PER_SECONDS;

	public static int analyzeStrategyId(long ordSysId) {
		return 0;
	}

	public static long analyzeEpochSeconds(long ordSysId) {
		return 0;
	}

	public static void main(String[] args) throws InterruptedException {

		System.out.println(9219999999999999999L);
		System.out.println(Long.MAX_VALUE);
		System.out.println("000" + EpochTime.getEpochSeconds() + "000000");
		System.out.println("000" + EpochTime.getEpochMillis() + "000000");
		System.out.println(allocate(800));
		System.out.println("uniqueId");
		System.out.println(BitFormatter.longBinaryFormat(Long.MAX_VALUE));
		System.out.println(Short.MAX_VALUE);
		System.out.println(BitFormatter.intBinaryFormat(Short.MAX_VALUE));
		System.out.println(BitFormatter.intBinaryFormat(999));

		System.out.println(Long.MAX_VALUE);
		System.out.println(Long.SIZE);
		System.out.println(Short.MAX_VALUE);
		System.out.println(Short.SIZE);

		System.out.println(24 * 60 * 60);

		System.out.println(Instant.ofEpochMilli(BASELINE_2000_MILLIS));
		System.out.println(Instant.ofEpochMilli(BASELINE_2010_MILLIS));
		System.out.println(Instant.ofEpochMilli(BASELINE_2020_MILLIS));
		System.out.println(Instant.ofEpochSecond(BASELINE_2000_SECONDS));
		System.out.println(Instant.ofEpochSecond(BASELINE_2010_SECONDS));
		System.out.println(Instant.ofEpochSecond(BASELINE_2020_MILLIS));

		System.out.println(ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0, TimeZone.UTC).toEpochSecond());
		System.out.println(ZonedDateTime.of(2010, 1, 1, 0, 0, 0, 0, TimeZone.UTC).toEpochSecond());
		System.out.println(ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, TimeZone.UTC).toEpochSecond());

	}
}
