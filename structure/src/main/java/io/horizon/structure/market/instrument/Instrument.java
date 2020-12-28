package io.horizon.definition.market.instrument;

import java.time.ZoneOffset;

import io.mercury.common.fsm.Enable;
import io.mercury.common.functional.Formattable;

public interface Instrument extends Enable<Instrument>, Comparable<Instrument>, Formattable<String> {

	/**
	 * Integer.MAX_VALUE == 2147483647
	 * 
	 * STOCK : exchange|symbol<br>
	 * MAX_VALUE == 214|7483647<br>
	 * 
	 * FUTURES:exchange|symbol|term<br>
	 * MAX_VALUE == 214| 74 |83647<br>
	 * 
	 * @return int
	 */
	int instrumentId();

	String instrumentCode();

	Symbol symbol();

	default String symbolCode() {
		return symbol().symbolCode();
	}

	default Exchange exchange() {
		return symbol().exchange();
	}

	default String exchangeCode() {
		return symbol().exchange().exchangeCode();
	}

	default ZoneOffset zoneOffset() {
		return symbol().exchange().zoneOffset();
	}

	default PriceMultiplier getPriceMultiplier() {
		return symbol().getPriceMultiplier();
	}

	InstrumentType type();

	/**
	 * 是否立即可用<br>
	 * 
	 * 用于计算可买出仓位, 例如中国股票的买入并是不立即可以卖出
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
		return instrumentId() < o.instrumentId() ? -1 : instrumentId() > o.instrumentId() ? 1 : 0;
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

	public static void main(String[] args) {

		System.out.println(Integer.MAX_VALUE);
	}

}
