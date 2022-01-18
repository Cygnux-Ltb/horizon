package io.horizon.trader.order.enums;

import static io.horizon.trader.order.enums.OrdSide.OrdSideCode.BUY;
import static io.horizon.trader.order.enums.OrdSide.OrdSideCode.INVALID;
import static io.horizon.trader.order.enums.OrdSide.OrdSideCode.MARGIN_BUY;
import static io.horizon.trader.order.enums.OrdSide.OrdSideCode.SELL;
import static io.horizon.trader.order.enums.OrdSide.OrdSideCode.SHORT_SELL;

import org.slf4j.Logger;

import io.mercury.common.log.Log4j2LoggerFactory;

public enum OrdSide {

	/**
	 * 无效
	 */
	Invalid(INVALID, TrdDirection.Invalid),

	/**
	 * 买
	 */
	Buy(BUY, TrdDirection.Long),

	/**
	 * 卖
	 */
	Sell(SELL, TrdDirection.Short),

	/**
	 * 融资买入
	 */
	MarginBuy(MARGIN_BUY, TrdDirection.Long),

	/**
	 * 融券卖出
	 */
	ShortSell(SHORT_SELL, TrdDirection.Short),

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
		case BUY:
			return Buy;
		case SELL:
			return Sell;
		case MARGIN_BUY:
			return MarginBuy;
		case SHORT_SELL:
			return ShortSell;
		default:
			log.error("OrdSide valueOf error, return OrdSide -> [Invalid], input code==[{}]", code);
			return Invalid;
		}
	}

	public static interface OrdSideCode {
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
