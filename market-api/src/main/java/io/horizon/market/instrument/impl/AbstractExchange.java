package io.horizon.market.instrument.impl;

import java.time.ZoneOffset;

import io.horizon.market.instrument.Exchange;

public abstract class AbstractExchange implements Exchange {

	// 唯一编码
	private final int exchangeId;

	// String唯一编码
	private final String exchangeCode;

	// 交易所时区
	private final ZoneOffset zoneOffset;

	// 交易所全名
	private final String fullName;

	/**
	 * 
	 * @param exchangeId
	 * @param exchangeCode
	 * @param zoneOffset
	 * @param fullName
	 */
	protected AbstractExchange(int exchangeId, String exchangeCode, ZoneOffset zoneOffset, String fullName) {
		this.exchangeId = exchangeId;
		this.exchangeCode = exchangeCode;
		this.zoneOffset = zoneOffset;
		this.fullName = fullName;
	}

	@Override
	public int getExchangeId() {
		return exchangeId;
	}

	@Override
	public String getExchangeCode() {
		return exchangeCode;
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
	public int getSymbolId(int serialInExchange) {
		return exchangeId + serialInExchange * 100000;
	}

}
