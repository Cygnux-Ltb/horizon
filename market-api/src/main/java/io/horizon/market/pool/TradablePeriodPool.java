package io.horizon.market.pool;

import java.time.LocalTime;

import javax.annotation.concurrent.ThreadSafe;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;

import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.Symbol;
import io.horizon.market.instrument.TradablePeriod;
import io.mercury.common.collections.MutableMaps;
import io.mercury.common.util.Assertor;

@ThreadSafe
public final class TradablePeriodPool {

	private TradablePeriodPool() {
	}

	// Map<symbolId, ImmutableList<TradablePeriod>>
	private static ImmutableIntObjectMap<ImmutableList<TradablePeriod>> Pool;

	public synchronized static void register(Symbol[] symbols) {
		Assertor.requiredLength(symbols, 1, "symbols");
		MutableIntObjectMap<ImmutableList<TradablePeriod>> map = MutableMaps.newIntObjectHashMap();
		if (Pool != null)
			Pool.forEachKeyValue((key, value) -> map.put(key, value));
		for (Symbol symbol : symbols) {
			if (!map.containsKey(symbol.getSymbolId()))
				map.put(symbol.getSymbolId(), symbol.getTradablePeriods());
		}
		Pool = map.toImmutable();
	}

	/**
	 * 获取指定Instrument的交易周期
	 * 
	 * @param instrument
	 * @return
	 */
	public static synchronized ImmutableList<TradablePeriod> getTradingPeriods(Instrument instrument) {
		return Pool.get(instrument.getSymbol().getSymbolId());
	}

	/**
	 * 获取指定Symbol的交易周期
	 * 
	 * @param symbol
	 * @return
	 */
	public static synchronized ImmutableList<TradablePeriod> getTradingPeriods(Symbol symbol) {
		return Pool.get(symbol.getSymbolId());
	}

	/**
	 * 
	 * @param instrument
	 * @param time
	 * @return
	 */
	public static synchronized TradablePeriod nextTradingPeriod(Instrument instrument, LocalTime time) {
		return nextTradingPeriod(instrument.getSymbol(), time);
	}

	/**
	 * 获取下一个交易时段
	 * 
	 * @param symbol
	 * @param time
	 * @return
	 */
	public static synchronized TradablePeriod nextTradingPeriod(Symbol symbol, LocalTime time) {
		ImmutableList<TradablePeriod> tradingPeriodSet = getTradingPeriods(symbol);
		TradablePeriod result = null;
		int baseTime = time.toSecondOfDay();
		int baseDiff = Integer.MAX_VALUE;
		for (TradablePeriod period : tradingPeriodSet) {
			int start = period.getStart().toSecondOfDay();
			int diff = Math.abs(start - baseTime);
			if (diff < baseDiff) {
				baseDiff = diff;
				result = period;
			}
		}
		return result;
	}

}
