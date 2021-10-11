package io.horizon.market.pool;

import java.time.LocalTime;

import javax.annotation.concurrent.ThreadSafe;

import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.Symbol;
import io.horizon.market.serial.TradablePeriod;
import io.mercury.common.collections.MutableMaps;

@ThreadSafe
public final class TradablePeriodPool {

	public static final TradablePeriodPool Singleton = new TradablePeriodPool();

	private TradablePeriodPool() {
	}

	// Map<Symbol, Set<TimePeriod>>
	private MutableIntObjectMap<ImmutableSortedSet<TradablePeriod>> tradingPeriodMap = MutableMaps
			.newIntObjectHashMap();

	// Map<Symbol, Set<TimePeriod>>
	private ImmutableIntObjectMap<ImmutableSortedSet<TradablePeriod>> immutablePool;

	public void register(Symbol... symbols) {
		if (symbols == null)
			throw new IllegalArgumentException("Illegal Argument -> symbols is null");
		for (Symbol symbol : symbols)
			putTradingPeriod(symbol);
		toImmutable();
	}

	private void putTradingPeriod(Symbol symbol) {
		if (!tradingPeriodMap.containsKey(symbol.getSymbolId()))
			tradingPeriodMap.put(symbol.getSymbolId(), symbol.getTradablePeriodSet());
	}

	private void toImmutable() {
		this.immutablePool = tradingPeriodMap.toImmutable();
	}

	/**
	 * 获取当前指定Symbol的交易周期Set
	 * 
	 * @param period
	 * @param symbol
	 * @return
	 */
	public ImmutableSortedSet<TradablePeriod> getTradingPeriodSet(Instrument instrument) {
		return getTradingPeriodSet(instrument.getSymbol());
	}

	/**
	 * 
	 * @param symbol
	 * @return
	 */
	public ImmutableSortedSet<TradablePeriod> getTradingPeriodSet(Symbol symbol) {
		return immutablePool.get(symbol.getSymbolId());
	}

	/**
	 * 
	 * @param instrument
	 * @param time
	 * @return
	 */
	public TradablePeriod nextTradingPeriod(Instrument instrument, LocalTime time) {
		return nextTradingPeriod(instrument.getSymbol(), time);
	}

	/**
	 * 获取下一个交易时段
	 * 
	 * @param symbol
	 * @param time
	 * @return
	 */
	public TradablePeriod nextTradingPeriod(Symbol symbol, LocalTime time) {
		ImmutableSortedSet<TradablePeriod> tradingPeriodSet = getTradingPeriodSet(symbol);
		TradablePeriod rtnTradingPeriod = null;
		int baseTime = time.toSecondOfDay();
		int baseDiff = Integer.MAX_VALUE;
		for (TradablePeriod tradingPeriod : tradingPeriodSet) {
			int startSecondOfDay = tradingPeriod.getStartSecondOfDay();
			int diff = Math.abs(startSecondOfDay - baseTime);
			if (diff < baseDiff) {
				baseDiff = diff;
				rtnTradingPeriod = tradingPeriod;
			}
		}
		return rtnTradingPeriod;
	}

}
