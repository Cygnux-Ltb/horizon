package io.horizon.market.instrument;

import java.time.ZoneOffset;

import io.mercury.common.fsm.Enableable;
import io.mercury.common.functional.Formattable;

public interface Instrument extends Enableable, Comparable<Instrument>, Formattable<String> {

	/**
	 * Integer.MAX_VALUE == 2147483647
	 * 
	 * STOCK : exchange|symbol<br>
	 * MAX_VALUE == 214|7483647<br>
	 * 
	 * FUTURES:exchange|symbol|term<br>
	 * MAX_VALUE == 214| 74 |83647<br>
	 * 
	 * FOREX : exchange|symbol<br>
	 * MAX_VALUE == 214|7483647<br>
	 * 
	 * @return int
	 */
	int getInstrumentId();

	/**
	 * 
	 * @return
	 */
	String getInstrumentCode();

	/**
	 * 
	 * @return
	 */
	Symbol getSymbol();

	/**
	 * 
	 * @return
	 */
	default String getSymbolCode() {
		return getSymbol().getSymbolCode();
	}

	/**
	 * 
	 * @return
	 */
	default String getExchangeCode() {
		return getSymbol().getExchangeCode();
	}

	/**
	 * 
	 * @return
	 */
	default ZoneOffset getZoneOffset() {
		return getSymbol().getZoneOffset();
	}

	/**
	 * 
	 * @return PriceMultiplier
	 */
	default PriceMultiplier getPriceMultiplier() {
		return getSymbol().getPriceMultiplier();
	}

	/**
	 * 
	 * @return InstrumentType
	 */
	default InstrumentType getType() {
		return getSymbol().getType();
	}

	/**
	 * 是否立即可用<br>
	 * 
	 * 用于计算可卖出仓位, 例如中国股票的买入并不是立即可以卖出
	 * 
	 * @return
	 */
	default boolean isAvailableImmediately() {
		return true;
	}

	/**
	 * 优先平仓类型
	 * 
	 * @return
	 */
	default PriorityCloseType getPriorityCloseType() {
		return PriorityCloseType.NONE;
	}

	@Override
	default int compareTo(Instrument o) {
		return getInstrumentId() < o.getInstrumentId() ? -1 : getInstrumentId() > o.getInstrumentId() ? 1 : 0;
	}

	default int getTradeFee() {
		return 0;
	}

	/**
	 * 
	 * 优先平仓类型枚举<br>
	 * 
	 * 上海期货交易所会调整平今仓和平昨仓的手续费<br>
	 * 动态调整优先平仓类型可节约手续费
	 * 
	 * @author yellow013
	 *
	 */
	public static enum PriorityCloseType {
		// 无
		NONE,
		// 优先平昨仓
		YESTERDAY,
		// 优先平今仓
		TODAY
	}

}
