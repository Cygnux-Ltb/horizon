package io.horizon.market.instrument.impl;

import java.util.HashMap;
import java.util.Map;

import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.Symbol;
import io.mercury.common.fsm.EnableableComponent;
import io.mercury.serialization.json.JsonWrapper;

public abstract class AbstractInstrument extends EnableableComponent implements Instrument {

	// 唯一编码
	private final int instrumentId;

	// String唯一编码
	private final String instrumentCode;

	// symbol
	private final Symbol symbol;

	/**
	 * 
	 * @param instrumentId
	 * @param instrumentCode
	 * @param symbol
	 */
	AbstractInstrument(int instrumentId, String instrumentCode, Symbol symbol) {
		this.instrumentId = instrumentId;
		this.instrumentCode = instrumentCode;
		this.symbol = symbol;
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
	public Symbol getSymbol() {
		return symbol;
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
			tempMap.put("instrumentId", instrumentId);
			tempMap.put("instrumentCode", instrumentCode);
			this.formatText = JsonWrapper.toJson(tempMap);
		}
		return formatText;
	}

}
