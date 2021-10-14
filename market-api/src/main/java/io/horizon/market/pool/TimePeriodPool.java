package io.horizon.market.pool;

import java.time.Duration;
import java.time.LocalDate;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.collections.api.map.primitive.ImmutableLongObjectMap;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.collector.Collectors2;

import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.Symbol;
import io.mercury.common.collections.MutableMaps;
import io.mercury.common.collections.MutableSets;
import io.mercury.common.param.JointKeyParams;
import io.mercury.common.sequence.TimeWindow;
import io.mercury.common.util.Assertor;

@NotThreadSafe
public final class TimePeriodPool {

	public static final TimePeriodPool Singleton = new TimePeriodPool();

	private TimePeriodPool() {
	}

	/**
	 * 使用联合主键进行索引,高位为symbolId, 低位为period <br>
	 * 可变的Pool,最终元素为Set <br>
	 * Map<(period + symbolId), Set<TimePeriod>>
	 */
	private MutableLongObjectMap<ImmutableSortedSet<TimeWindow>> timePeriodSetPool = MutableMaps.newLongObjectHashMap();

	/**
	 * 使用联合主键进行索引,高位为symbolId, 低位为period <br>
	 * 可变的Pool,最终元素为Map <br>
	 * Map<(period + symbolId), Map<SerialNumber,TimePeriod>>
	 */
	private MutableLongObjectMap<ImmutableLongObjectMap<TimeWindow>> timePeriodMapPool = MutableMaps
			.newLongObjectHashMap();

	/**
	 * 
	 * @param symbol
	 * @param durations
	 */
	public void register(@Nonnull LocalDate date, @Nonnull Symbol symbol, Duration... durations) {
		register(date, new Symbol[] { symbol }, durations);
	}

	/**
	 * 
	 * @param symbols
	 * @param durations
	 */
	public void register(@Nonnull LocalDate date, @Nonnull Symbol[] symbols, Duration... durations) {
		Assertor.requiredLength(symbols, 1, "symbols");
		Assertor.requiredLength(durations, 1, "durations");
		for (Duration duration : durations)
			generateTimePeriod(date, symbols, duration);
	}

	private void generateTimePeriod(@Nonnull LocalDate date, @Nonnull Symbol[] symbols, Duration duration) {
		for (Symbol symbol : symbols) {
			MutableSortedSet<TimeWindow> timePeriodSet = MutableSets.newTreeSortedSet();
			MutableLongObjectMap<TimeWindow> timePeriodMap = MutableMaps.newLongObjectHashMap();
			// 获取指定品种下的全部交易时段,将交易时段按照指定指标周期切分
			symbol.getTradablePeriodSet().stream().flatMap(
					tradingPeriod -> tradingPeriod.segmentation(date, symbol.getZoneOffset(), duration).stream())
					.collect(Collectors2.toList()).each(serial -> {
						timePeriodSet.add(serial);
						timePeriodMap.put(serial.getSerialId(), serial);
					});
			long symbolTimeKey = mergeSymbolTimeKey(symbol, duration);
			timePeriodSetPool.put(symbolTimeKey, timePeriodSet.toImmutable());
			timePeriodMapPool.put(symbolTimeKey, timePeriodMap.toImmutable());
		}
	}

	private long mergeSymbolTimeKey(@Nonnull Symbol symbol, Duration duration) {
		return JointKeyParams.mergeJointKey(symbol.getSymbolId(), (int) duration.getSeconds());
	}

	/**
	 * 获取指定Instrument和指定指标周期下的全部时间分割点
	 * 
	 * @param instrument
	 * @param duration
	 * @return
	 */
	public ImmutableSortedSet<TimeWindow> getTimePeriodSet(Instrument instrument, Duration duration) {
		return getTimePeriodSet(instrument.getSymbol(), duration);
	}

	/**
	 * 在指定Symbol列表中找出相应的时间分割点信息
	 * 
	 * @param symbol
	 * @param duration
	 * @return
	 */
	public ImmutableSortedSet<TimeWindow> getTimePeriodSet(Symbol symbol, Duration duration) {
		long symbolTimeKey = mergeSymbolTimeKey(symbol, duration);
		ImmutableSortedSet<TimeWindow> sortedSet = timePeriodSetPool.get(symbolTimeKey);
		if (sortedSet == null) {
			// TODO ??? LocalDate.now()
			register(LocalDate.now(), symbol, duration);
			sortedSet = timePeriodSetPool.get(symbolTimeKey);
		}
		return sortedSet;
	}

	/**
	 * 
	 * @param instrument
	 * @param duration
	 * @return
	 */
	public ImmutableLongObjectMap<TimeWindow> getTimePeriodMap(Instrument instrument, Duration duration) {
		return getTimePeriodMap(instrument.getSymbol(), duration);
	}

	/**
	 * 
	 * @param symbol
	 * @param duration
	 * @return
	 */
	public ImmutableLongObjectMap<TimeWindow> getTimePeriodMap(Symbol symbol, Duration duration) {
		long symbolTimeKey = mergeSymbolTimeKey(symbol, duration);
		ImmutableLongObjectMap<TimeWindow> longObjectMap = timePeriodMapPool.get(symbolTimeKey);
		if (longObjectMap == null) {
			// TODO ??? LocalDate.now()
			register(LocalDate.now(), symbol, duration);
			longObjectMap = timePeriodMapPool.get(symbolTimeKey);
		}
		return longObjectMap;
	}

	/**
	 * 
	 * @param instrument
	 * @param duration
	 * @param serial
	 * @return
	 */
	public TimeWindow getNextTimePeriod(Instrument instrument, Duration duration, TimeWindow serial) {
		return getNextTimePeriod(instrument.getSymbol(), duration, serial);
	}

	/**
	 * 
	 * @param symbol
	 * @param duration
	 * @param serial
	 * @return
	 */
	@CheckForNull
	public TimeWindow getNextTimePeriod(@Nonnull Symbol symbol, Duration duration, TimeWindow serial) {
		ImmutableLongObjectMap<TimeWindow> longObjectMap = getTimePeriodMap(symbol, duration);
		return longObjectMap.get(serial.getSerialId() + duration.getSeconds());
	}

}
