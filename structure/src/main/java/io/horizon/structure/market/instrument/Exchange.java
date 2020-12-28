package io.horizon.structure.market.instrument;

import java.time.ZoneOffset;

import io.mercury.common.datetime.TimeZone;

public enum Exchange {

	TOCOM(11, "Tokyo Commodity Exchange", TimeZone.JST_OFFSET),

	TFX(12, "Tokyo Financial Exchange", TimeZone.JST_OFFSET),

	LME(22, "London Metal Exchange", TimeZone.UTC),

	SHFE(41, "Shanghai Futures Exchange", TimeZone.CST_OFFSET),

	DCE(42, "Dalian Commodity Exchange", TimeZone.CST_OFFSET),

	ZCE(43, "Zhengzhou Commodity Exchange", TimeZone.CST_OFFSET),

	CFFEX(44, "China Financial Futures Exchange", TimeZone.CST_OFFSET),

	SHINE(45, "Shanghai International Energy Exchange", TimeZone.CST_OFFSET),

	SSE(46, "Shanghai Stock Exchange", TimeZone.CST_OFFSET),

	SZSE(47, "Shenzhen Stock Exchange", TimeZone.CST_OFFSET),

	;

	private int exchangeId;
	private String desc;
	private ZoneOffset zoneOffset;

	private Exchange(int exchangeId, String desc, ZoneOffset zoneOffset) {
		this.exchangeId = exchangeId * 10000000;
		this.zoneOffset = zoneOffset;
	}

	public int exchangeId() {
		return exchangeId;
	}

	public String exchangeCode() {
		return name();
	}

	public String desc() {
		return desc;
	}

	public ZoneOffset zoneOffset() {
		return zoneOffset;
	}
	
	public int genSymbolId(int seqWithinExchange) {
		return exchangeId + seqWithinExchange * 100000;
	}

}
