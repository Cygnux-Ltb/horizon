package io.horizon.market.instrument;

import io.horizon.market.instrument.attr.InstrumentType;
import io.horizon.market.instrument.attr.PriceMultiplier;
import io.horizon.market.instrument.attr.PriorityCloseType;
import io.mercury.common.fsm.Enableable;
import io.mercury.common.functional.Formatter;

import java.time.ZoneOffset;

public interface Instrument extends Enableable, Comparable<Instrument>, Formatter<String> {

    /**
     * Integer.MAX_VALUE == 2147483647
     * <p>
     * STOCK : exchange|symbol<br>
     * MAX_VALUE == 214|7483647<br>
     * <p>
     * FUTURES:exchange|symbol|term<br>
     * MAX_VALUE == 214| 74 |83647<br>
     * <p>
     * FOREX : exchange|symbol<br>
     * MAX_VALUE == 214|7483647<br>
     *
     * @return int
     */
    int getInstrumentId();

    String getInstrumentCode();

    Symbol getSymbol();

    Exchange getExchange();

    default String getExchangeCode() {
        return getExchange().getExchangeCode();
    }

    default ZoneOffset getZoneOffset() {
        return getExchange().getZoneOffset();
    }

    InstrumentType getType();

    default PriceMultiplier getMultiplier() {
        return getSymbol().getMultiplier();
    }

    default int getTickSize() {
        return getSymbol().getTickSize();
    }

    /**
     * 是否立即可用<br>
     * <p>
     * 用于计算可卖出仓位, 例如中国大陆股票的买入并不是立即可以卖出
     *
     * @return boolean
     */
    default boolean isAvailableImmediately() {
        return true;
    }

    /**
     * 优先平仓类型
     *
     * @return PriorityCloseType
     */
    default PriorityCloseType getPriorityCloseType() {
        return PriorityCloseType.NONE;
    }

    default int getTradeFee() {
        return 0;
    }

    @Override
    default int compareTo(Instrument o) {
        return Integer.compare(getInstrumentId(), o.getInstrumentId());
    }

}
