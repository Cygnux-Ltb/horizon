package io.horizon.structure.market.instrument;

import java.util.HashMap;
import java.util.Map;

import io.mercury.serialization.json.JsonWrapper;

public abstract class AbsInstrument implements Instrument {

	/**
	 * 唯一编码
	 */
	private final int instrumentId;

	/**
	 * String唯一编码
	 */
	private final String instrumentCode;

	/**
	 * 
	 * @param id
	 * @param code
	 */
	protected AbsInstrument(int instrumentId, String instrumentCode) {
		this.instrumentId = instrumentId;
		this.instrumentCode = instrumentCode;
	}

	/**
	 * 激活标识
	 */
	private boolean isEnable;

	@Override
	public Instrument enable() {
		this.isEnable = true;
		return this;
	}

	@Override
	public Instrument disable() {
		this.isEnable = false;
		return this;
	}

	@Override
	public boolean isEnabled() {
		return isEnable;
	}

	@Override
	public boolean isDisabled() {
		return !isEnable;
	}

	@Override
	public int instrumentId() {
		return instrumentId;
	}

	@Override
	public String instrumentCode() {
		return instrumentCode;
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
			tempMap.put("type", type());
			tempMap.put("instrumentId", instrumentId);
			tempMap.put("instrumentCode", instrumentCode);
			this.formatText = JsonWrapper.toJson(tempMap);
		}
		return formatText;
	}

}
