package io.horizon.market.instrument;

import java.util.HashMap;
import java.util.Map;

import io.mercury.common.fsm.EnableableComponent;
import io.mercury.serialization.json.JsonWrapper;
import lombok.Getter;

public abstract class AbstractInstrument extends EnableableComponent implements Instrument {

	// 唯一编码
	@Getter
	private final int instrumentId;

	// String唯一编码
	@Getter
	private final String instrumentCode;

	// symbol
	@Getter
	private final Symbol symbol;

	protected AbstractInstrument(int instrumentId, String instrumentCode, Symbol symbol) {
		this.instrumentId = instrumentId;
		this.instrumentCode = instrumentCode;
		this.symbol = symbol;
	}

	protected AbstractInstrument(int instrumentId, String instrumentCode) {
		this.instrumentId = instrumentId;
		this.instrumentCode = instrumentCode;
		this.symbol = null;
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
