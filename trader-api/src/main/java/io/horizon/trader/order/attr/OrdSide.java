package io.horizon.trader.order.attr;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;
import lombok.Getter;

public enum OrdSide {

	Invalid(OrdSideConstant.INVALID, TrdDirection.Invalid),

	Buy(OrdSideConstant.BUY, TrdDirection.Long),

	Sell(OrdSideConstant.SELL, TrdDirection.Short),

	MarginBuy(OrdSideConstant.MARGIN_BUY, TrdDirection.Long),

	ShortSell(OrdSideConstant.SHORT_SELL, TrdDirection.Short),

	;

	private OrdSide(int code, TrdDirection direction) {
		this.code = code;
		this.direction = direction;
		this.str = name() + "[" + code + "-" + direction + "]";
	}

	@Getter
	private final int code;

	@Getter
	private final TrdDirection direction;

	private static final Logger log = CommonLoggerFactory.getLogger(OrdSide.class);

	public static OrdSide valueOf(int code) {
		switch (code) {
		case OrdSideConstant.BUY:
			return Buy;
		case OrdSideConstant.SELL:
			return Sell;
		case OrdSideConstant.MARGIN_BUY:
			return MarginBuy;
		case OrdSideConstant.SHORT_SELL:
			return ShortSell;
		default:
			log.error("OrdSide valueOf error, return OrdSide -> [Invalid], param is {}", code);
			return Invalid;
		}
	}

	private final String str;

	@Override
	public String toString() {
		return str;
	}

	private interface OrdSideConstant {
		// 无效
		int INVALID = -1;

		// 买
		int BUY = 1;
		// 卖
		int SELL = 2;
		// 融资买入
		int MARGIN_BUY = 4;
		// 融券卖出
		int SHORT_SELL = 8;
	}

}
