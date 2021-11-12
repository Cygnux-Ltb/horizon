package io.horizon.market.instrument.impl.futures;

import static io.mercury.common.datetime.TimeZone.CST;
import static io.mercury.common.datetime.TimeZone.JST;
import static io.mercury.common.datetime.TimeZone.UTC;

import java.time.ZoneOffset;

import io.horizon.market.instrument.Exchange;
import io.horizon.market.instrument.Symbol;
import io.mercury.common.datetime.TimeZone;

public enum JapanExchange implements Exchange {

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

	private JapanExchange(int exchangeId, String desc, ZoneOffset zoneOffset) {
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

	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int genSymbolId(Symbol symbol) {
		// TODO Auto-generated method stub
		return 0;
	}

}
