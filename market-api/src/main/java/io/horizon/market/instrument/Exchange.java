package io.horizon.market.instrument;

import static io.mercury.common.datetime.TimeZone.CST;
import static io.mercury.common.datetime.TimeZone.JST;
import static io.mercury.common.datetime.TimeZone.UTC;

import java.time.ZoneOffset;

import io.mercury.common.datetime.TimeZone;

public enum Exchange {

	TOCOM(11, "Tokyo Commodity Exchange", JST),

	TFX(12, "Tokyo Financial Exchange", JST),

	LME(22, "London Metal Exchange", UTC),

	SHFE(41, "Shanghai Futures Exchange", CST),

	DCE(42, "Dalian Commodity Exchange", CST),

	ZCE(43, "Zhengzhou Commodity Exchange", CST),

	CFFEX(44, "China Financial Futures Exchange", TimeZone.CST),

	SHINE(45, "Shanghai International Energy Exchange", TimeZone.CST),

	SSE(46, "Shanghai Stock Exchange", TimeZone.CST),

	SZSE(47, "Shenzhen Stock Exchange", TimeZone.CST),

	;

	private final int exchangeId;

	private final String desc;

	private final ZoneOffset zoneOffset;

	private Exchange(int exchangeId, String desc, ZoneOffset zoneOffset) {
		this.exchangeId = exchangeId * 10000000;
		this.desc = desc;
		this.zoneOffset = zoneOffset;
	}

	public int getExchangeId() {
		return exchangeId;
	}

	public String getDesc() {
		return desc;
	}

	public ZoneOffset getZoneOffset() {
		return zoneOffset;
	}

	public final String code() {
		return name();
	}

	/**
	 * 传入交易标的在交易所内的序列
	 * 
	 * @param exchangeSeq
	 * @return
	 */
	public int genSymbolId(int exchangeSeq) {
		return exchangeId + exchangeSeq * 100000;
	}

}
