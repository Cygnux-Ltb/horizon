package io.horizon.market.instrument;

import java.time.ZoneOffset;

import io.mercury.common.datetime.TimeZone;
import lombok.Getter;

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

	@Getter
	private final int exchangeId;

	@Getter
	private final String desc;

	@Getter
	private final ZoneOffset zoneOffset;

	private Exchange(int exchangeId, String desc, ZoneOffset zoneOffset) {
		this.exchangeId = exchangeId * 10000000;
		this.desc = desc;
		this.zoneOffset = zoneOffset;
	}

	public int genSymbolId(int exchangeSeq) {
		return exchangeId + exchangeSeq * 100000;
	}

}
