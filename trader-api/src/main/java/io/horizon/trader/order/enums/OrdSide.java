package io.horizon.trader.order.attr;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;

public enum OrdSide {

	Invalid(Const.INVALID, TrdDirection.Invalid),

	Buy(Const.BUY, TrdDirection.Long),

	Sell(Const.SELL, TrdDirection.Short),

	MarginBuy(Const.MARGIN_BUY, TrdDirection.Long),

	ShortSell(Const.SHORT_SELL, TrdDirection.Short),

	;

	private final char code;

	private final TrdDirection direction;

	private static final Logger log = CommonLoggerFactory.getLogger(OrdSide.class);

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

	public static OrdSide valueOf(char code) {
		if (code == Const.BUY)
			return Buy;
		if (code == Const.SELL)
			return Sell;
		if (code == Const.MARGIN_BUY)
			return MarginBuy;
		if (code == Const.SHORT_SELL)
			return ShortSell;
		log.error("OrdSide valueOf error, return OrdSide -> [Invalid], param is {}", code);
		return Invalid;
	}

	private final String str;

	@Override
	public String toString() {
		return str;
	}

	private interface Const {
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
