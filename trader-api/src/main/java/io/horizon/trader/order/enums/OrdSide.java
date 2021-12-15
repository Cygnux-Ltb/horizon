package io.horizon.trader.order.enums;

import org.slf4j.Logger;

import io.mercury.common.log.Log4j2LoggerFactory;

public enum OrdSide {

	/**
	 * 无效
	 */
	Invalid(Code.INVALID, TrdDirection.Invalid),

	/**
	 * 买
	 */
	Buy(Code.BUY, TrdDirection.Long),

	/**
	 * 卖
	 */
	Sell(Code.SELL, TrdDirection.Short),

	/**
	 * 融资买入
	 */
	MarginBuy(Code.MARGIN_BUY, TrdDirection.Long),

	/**
	 * 融券卖出
	 */
	ShortSell(Code.SHORT_SELL, TrdDirection.Short),

	;

	private final char code;

	private final TrdDirection direction;

	private final String str;

	private static final Logger log = Log4j2LoggerFactory.getLogger(OrdSide.class);

	private OrdSide(char code, TrdDirection direction) {
		this.code = code;
		this.direction = direction;
		this.str = name() + "[" + code + "-" + direction + "]";
	}

	public char getCode() {
		return code;
	}

	public TrdDirection getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return str;
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public static OrdSide valueOf(int code) {
		switch (code) {
		case Code.BUY:
			return Buy;
		case Code.SELL:
			return Sell;
		case Code.MARGIN_BUY:
			return MarginBuy;
		case Code.SHORT_SELL:
			return ShortSell;
		default:
			log.error("OrdSide valueOf error, return OrdSide -> [Invalid], input code==[{}]", code);
			return Invalid;
		}
	}

	private interface Code {
		// 无效
		char INVALID = 'I';
		// 买
		char BUY = 'B';
		// 卖
		char SELL = 'S';
		// 融资买入
		char MARGIN_BUY = 'M';
		// 融券卖出
		char SHORT_SELL = 'T';
	}

}
