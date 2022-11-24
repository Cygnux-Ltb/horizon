package io.horizon.market.instrument.base;

import java.util.HashMap;
import java.util.Map;

import io.horizon.market.instrument.Exchange;
import io.horizon.market.instrument.Instrument;
import io.mercury.common.fsm.EnableableComponent;
import io.mercury.serialization.json.JsonWrapper;

public abstract class BaseInstrument extends EnableableComponent implements Instrument {

    // 唯一编码
    protected final int instrumentId;

    // String唯一编码
    protected final String instrumentCode;

    // 所属交易所
    protected final Exchange exchange;

    /**
     * @param instrumentId   int
     * @param instrumentCode String
     * @param exchange       Exchange
     */
    protected BaseInstrument(int instrumentId, String instrumentCode, Exchange exchange) {
        this.instrumentId = instrumentId;
        this.instrumentCode = instrumentCode;
        this.exchange = exchange;
    }

    @Override
    public int getInstrumentId() {
        return instrumentId;
    }

    @Override
    public String getInstrumentCode() {
        return instrumentCode;
    }

    @Override
    public Exchange getExchange() {
        return exchange;
    }

    @Override
    public String toString() {
        return instrumentCode;
    }

    private String formatText;

    @Override
    public String format() {
        if (formatText == null) {
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("type", getType());
            tempMap.put("instrumentId", getInstrumentId());
            tempMap.put("instrumentCode", getInstrumentCode());
            tempMap.put("symbol", getSymbol());
            this.formatText = JsonWrapper.toJson(tempMap);
        }
        return formatText;
    }

}
