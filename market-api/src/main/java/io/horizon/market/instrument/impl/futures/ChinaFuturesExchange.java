package io.horizon.market.instrument.impl.futures;

import static io.mercury.common.datetime.TimeZone.CST;

import io.horizon.market.instrument.impl.AbstractExchange;

public abstract class ChinaFuturesExchange extends AbstractExchange {

	/**
	 * Shanghai Futures Exchange
	 */
	public static final ChinaFuturesExchange SHFE = new ChinaFuturesExchange(41, "SHFE", "Shanghai Futures Exchange") {
	};

	/**
	 * Dalian Commodity Exchange
	 */
	public static final ChinaFuturesExchange DCE = new ChinaFuturesExchange(42, "DCE", "Dalian Commodity Exchange") {
	};

	/**
	 * Zhengzhou Commodity Exchange
	 */
	public static final ChinaFuturesExchange ZCE = new ChinaFuturesExchange(43, "ZCE", "Zhengzhou Commodity Exchange") {
	};

	/**
	 * China Financial Futures Exchange
	 */
	public static final ChinaFuturesExchange CFFEX = new ChinaFuturesExchange(44, "CFFEX",
			"China Financial Futures Exchange") {
	};

	/**
	 * Shanghai International Energy Exchange
	 */
	public static final ChinaFuturesExchange SHINE = new ChinaFuturesExchange(45, "SHINE",
			"Shanghai International Energy Exchange") {
	};

	private ChinaFuturesExchange(int exchangeId, String code, String fullName) {
		super(exchangeId * 10000000, code, CST, fullName);
	}

}
