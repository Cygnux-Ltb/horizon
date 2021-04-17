package io.horizon.market.pool;

import java.time.LocalTime;

import javax.annotation.concurrent.ThreadSafe;

import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.Symbol;
import io.horizon.market.instrument.impl.ChinaFuturesSymbol;
import io.horizon.market.serial.TradablePeriodSerial;
import io.mercury.common.collections.MutableMaps;

@ThreadSafe
public final class TradablePeriodPool {

	public static final TradablePeriodPool Singleton = new TradablePeriodPool();

	private TradablePeriodPool() {
	}

	// Map<Symbol, Set<TimePeriod>>
	private MutableIntObjectMap<ImmutableSortedSet<TradablePeriodSerial>> tradingPeriodMap = MutableMaps
			.newIntObjectHashMap();

	// Map<Symbol, Set<TimePeriod>>
	private ImmutableIntObjectMap<ImmutableSortedSet<TradablePeriodSerial>> immutablePool;

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
	public ImmutableSortedSet<TradablePeriodSerial> getTradingPeriodSet(Instrument instrument) {
		return getTradingPeriodSet(instrument.getSymbol());
	}

	/**
	 * 
	 * @param symbol
	 * @return
	 */
	public ImmutableSortedSet<TradablePeriodSerial> getTradingPeriodSet(Symbol symbol) {
		return immutablePool.get(symbol.getSymbolId());
	}

	/**
	 * 
	 * @param instrument
	 * @param time
	 * @return
	 */
	public TradablePeriodSerial getAfterTradingPeriod(Instrument instrument, LocalTime time) {
		return getAfterTradingPeriod(instrument.getSymbol(), time);
	}

	/**
	 * 获取下一个交易时段
	 * 
	 * @param symbol
	 * @param time
	 * @return
	 */
	public TradablePeriodSerial getAfterTradingPeriod(Symbol symbol, LocalTime time) {
		ImmutableSortedSet<TradablePeriodSerial> tradingPeriodSet = getTradingPeriodSet(symbol);
		TradablePeriodSerial rtnTradingPeriod = null;
		int baseTime = time.toSecondOfDay();
		int baseDiff = Integer.MAX_VALUE;
		for (TradablePeriodSerial tradingPeriod : tradingPeriodSet) {
			int startSecondOfDay = tradingPeriod.getStartSecondOfDay();
			int diff = Math.abs(startSecondOfDay - baseTime);
			if (diff < baseDiff) {
				baseDiff = diff;
				rtnTradingPeriod = tradingPeriod;
			}
		}
		return rtnTradingPeriod;
	}

	public static void main(String[] args) {
		Singleton.register(ChinaFuturesSymbol.values());

		TradablePeriodSerial afterTradingPeriod = Singleton.getAfterTradingPeriod(ChinaFuturesSymbol.RB,
				LocalTime.now());
		System.out.println(afterTradingPeriod.getStartTime());
	}

}
