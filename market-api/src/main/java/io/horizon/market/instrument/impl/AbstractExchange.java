package io.horizon.market.instrument.impl;

import java.time.ZoneOffset;

import io.horizon.market.instrument.Exchange;
import io.horizon.market.instrument.Symbol;

public abstract class AbstractExchange implements Exchange {

	// 唯一编码
	private final int exchangeId;

	// String唯一编码
	private final String code;

	// 交易所时区
	private final ZoneOffset zoneOffset;

	// 交易所全名
	private final String fullName;

	/**
	 * 
	 * @param exchangeId
	 * @param code
	 * @param zoneOffset
	 * @param fullName
	 */
	protected AbstractExchange(int exchangeId, String code, ZoneOffset zoneOffset, String fullName) {
		this.exchangeId = exchangeId;
		this.code = code;
		this.zoneOffset = zoneOffset;
		this.fullName = fullName;
	}

	@Override
	public int getExchangeId() {
		return exchangeId;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public ZoneOffset getZoneOffset() {
		return zoneOffset;
	}

	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public int genSymbolId(Symbol symbol) {
		return exchangeId + symbol.getSeqWithinExchange() * 100000;
	}

}
