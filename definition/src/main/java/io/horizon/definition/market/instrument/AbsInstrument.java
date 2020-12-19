package io.horizon.definition.market.instrument;

import io.mercury.common.util.StringUtil;

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

	private String fmtText;

	@Override
	public String fmtText() {
		if (fmtText == null) {
			this.fmtText = "{\"type\" : " + StringUtil.toText(type()) + ", \"instrumentId\" : " + instrumentId
					+ ", \"instrumentCode\" : " + StringUtil.toText(instrumentCode) + "}";
		}
		return fmtText;
	}

}
