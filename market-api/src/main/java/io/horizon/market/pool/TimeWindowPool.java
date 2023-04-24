package io.horizon.market.pool;

import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.Symbol;
import io.mercury.common.collections.MutableMaps;
import io.mercury.common.collections.MutableSets;
import io.mercury.common.param.JointKeyParams;
import io.mercury.common.sequence.TimeWindow;
import org.eclipse.collections.api.map.primitive.ImmutableLongObjectMap;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.collector.Collectors2;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.time.Duration;
import java.time.LocalDate;

import static io.mercury.common.lang.Asserter.requiredLength;

@ThreadSafe
public final class TimeWindowPool {

    public static final TimeWindowPool Singleton = new TimeWindowPool();

    private TimeWindowPool() {
    }

    /**
     * 使用联合主键进行索引,高位为symbolId, 低位为period <br>
     * 可变的Pool,最终元素为Set <br>
     * Map<(period + symbolId), Set<TimePeriod>>
     */
    private final MutableLongObjectMap<ImmutableSortedSet<TimeWindow>> timeWindowPool =
            MutableMaps.newLongObjectHashMap();

    /**
     * 使用联合主键进行索引,高位为symbolId, 低位为period <br>
     * 可变的Pool,最终元素为Map <br>
     * Map<(period + symbolId), Map<SerialNumber,TimePeriod>>
     */
    private final MutableLongObjectMap<ImmutableLongObjectMap<TimeWindow>> timePeriodMapPool =
            MutableMaps.newLongObjectHashMap();

    /**
     * @param date      LocalDate
     * @param symbol    Symbol
     * @param durations Duration...
     */
    public void register(@Nonnull LocalDate date, @Nonnull Symbol symbol, Duration... durations) {
        register(date, new Symbol[]{symbol}, durations);
    }

    /**
     * @param date      LocalDate
     * @param symbols   Symbol[]
     * @param durations Duration...
     */
    public void register(@Nonnull LocalDate date, @Nonnull Symbol[] symbols, Duration... durations) {
        requiredLength(symbols, 1, "symbols");
        requiredLength(durations, 1, "durations");
        for (Duration duration : durations)
            generateTimePeriod(date, symbols, duration);
    }

    private void generateTimePeriod(@Nonnull LocalDate date, @Nonnull Symbol[] symbols, Duration duration) {
        for (Symbol symbol : symbols) {
            MutableSortedSet<TimeWindow> timePeriodSet = MutableSets.newTreeSortedSet();
            MutableLongObjectMap<TimeWindow> timePeriodMap = MutableMaps.newLongObjectHashMap();
            // 获取指定品种下的全部交易时段,将交易时段按照指定指标周期切分
            symbol.getTradablePeriods().stream()
                    .flatMap(tradingPeriod -> tradingPeriod
                            .segmentation(date, symbol.getExchange().getZoneOffset(), duration).stream())
                    .collect(Collectors2.toList()).each(serial -> {
                        timePeriodSet.add(serial);
                        timePeriodMap.put(serial.serialId(), serial);
                    });
            long symbolTimeKey = mergeSymbolTimeKey(symbol, duration);
            timeWindowPool.put(symbolTimeKey, timePeriodSet.toImmutable());
            timePeriodMapPool.put(symbolTimeKey, timePeriodMap.toImmutable());
        }
    }

    private long mergeSymbolTimeKey(@Nonnull Symbol symbol, Duration duration) {
        return JointKeyParams.mergeJointKey(symbol.getSymbolId(), (int) duration.getSeconds());
    }

    /**
     * 获取指定Instrument和指定指标周期下的全部时间分割点
     *
     * @param instrument Instrument
     * @param duration   Duration
     * @return ImmutableSortedSet<TimeWindow>
     */
    public ImmutableSortedSet<TimeWindow> getTimePeriodSet(Instrument instrument, Duration duration) {
        return getTimePeriodSet(instrument.getSymbol(), duration);
    }

    /**
     * 在指定Symbol列表中找出相应的时间分割点信息
     *
     * @param symbol   Symbol
     * @param duration Duration
     * @return ImmutableSortedSet<TimeWindow>
     */
    public ImmutableSortedSet<TimeWindow> getTimePeriodSet(Symbol symbol, Duration duration) {
        long symbolTimeKey = mergeSymbolTimeKey(symbol, duration);
        ImmutableSortedSet<TimeWindow> sortedSet = timeWindowPool.get(symbolTimeKey);
        if (sortedSet == null) {
            // TODO ??? LocalDate.now()
            register(LocalDate.now(), symbol, duration);
            sortedSet = timeWindowPool.get(symbolTimeKey);
        }
        return sortedSet;
    }

    /**
     * @param instrument Instrument
     * @param duration   Duration
     * @return ImmutableLongObjectMap<TimeWindow>
     */
    public ImmutableLongObjectMap<TimeWindow> getTimePeriodMap(Instrument instrument, Duration duration) {
        return getTimePeriodMap(instrument.getSymbol(), duration);
    }

    /**
     * @param symbol   Symbol
     * @param duration Duration
     * @return ImmutableLongObjectMap<TimeWindow>
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
     * @param instrument Instrument
     * @param duration   Duration
     * @param window     TimeWindow
     * @return TimeWindow
     */
    @CheckForNull
    public TimeWindow getNextTimePeriod(Instrument instrument, Duration duration, TimeWindow window) {
        return getNextTimePeriod(instrument.getSymbol(), duration, window);
    }

    /**
     * @param symbol   Symbol
     * @param duration Duration
     * @param window   TimeWindow
     * @return TimeWindow
     */
    @CheckForNull
    public TimeWindow getNextTimePeriod(@Nonnull Symbol symbol, Duration duration, TimeWindow window) {
        ImmutableLongObjectMap<TimeWindow> longObjectMap = getTimePeriodMap(symbol, duration);
        return longObjectMap.get(window.serialId() + duration.getSeconds());
    }

}
