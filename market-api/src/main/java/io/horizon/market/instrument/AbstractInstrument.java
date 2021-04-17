package io.horizon.market.instrument;

import java.util.HashMap;
import java.util.Map;

import io.mercury.common.fsm.EnableableComponent;
import io.mercury.serialization.json.JsonWrapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
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
