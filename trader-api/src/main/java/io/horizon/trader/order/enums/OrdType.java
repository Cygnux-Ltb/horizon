package io.horizon.trader.order.enums;

import static io.horizon.trader.order.enums.OrdType.OrdTypeCode.AP;
import static io.horizon.trader.order.enums.OrdType.OrdTypeCode.BP;
import static io.horizon.trader.order.enums.OrdType.OrdTypeCode.FAK;
import static io.horizon.trader.order.enums.OrdType.OrdTypeCode.FOK;
import static io.horizon.trader.order.enums.OrdType.OrdTypeCode.INVALID;
import static io.horizon.trader.order.enums.OrdType.OrdTypeCode.LIMITED;
import static io.horizon.trader.order.enums.OrdType.OrdTypeCode.LIMITED_STOP;
import static io.horizon.trader.order.enums.OrdType.OrdTypeCode.MARKET;
import static io.horizon.trader.order.enums.OrdType.OrdTypeCode.MARKET_STOP;
import static io.horizon.trader.order.enums.OrdType.OrdTypeCode.MTL;
import static io.horizon.trader.order.enums.OrdType.OrdTypeCode.MV;

import io.horizon.trader.transport.enums.TOrdType;

public enum OrdType {

	Invalid(INVALID, TOrdType.INVALID),

	Limited(LIMITED, TOrdType.LIMITED),

	Market(MARKET, TOrdType.MARKET),

	/**
	 * Limited Stop, 在目前的市场价格达到指定的止损价格时, 被激活成为限价单的报单.
	 */
	LimitedStop(LIMITED_STOP, TOrdType.LIMITED_STOP),

	/**
	 * Market Stop, 在目前的市场价格达到指定的止损价格时, 被激活成为市价单的报单.
	 */
	MarketStop(MARKET_STOP, TOrdType.MARKET_STOP),

	/**
	 * Market To Limited, 按照市价报单的方式成交, 不能成交的部分保留在报单队列中, 变成限价单的报单.
	 */
	MarketToLimited(MTL, TOrdType.MTL),

	/**
	 * Best Price, 不带有价格限定, 按照市场中存在的最好价格买入或者卖出的报单.
	 */
	BestPrice(BP, TOrdType.BP),

	/**
	 * Average Price, 限定最终成交平均价格的报单.
	 */
	AveragePrice(AP, TOrdType.AP),

	/**
	 * Fill Or Kill, 表示要求立即全部成交, 否则就全部取消的报单.
	 */
	FillOrKill(FOK, TOrdType.FOK),

	/**
	 * Fill And Kill, 表示要求立即成交, 对于无法满足的部分予以取消的报单.
	 */
	FillAndKill(FAK, TOrdType.FAK),

	/**
	 * Minimum Volume, 要求满足成交量达到这个最小成交量, 否则就取消的报单.
	 */
	MinimumVolume(MV, TOrdType.MV),

	;

	private final char code;

	private final TOrdType type;

	private OrdType(char code, TOrdType type) {
		this.code = code;
		this.type = type;
	}

	public char getCode() {
		return code;
	}

	public TOrdType getTOrdType() {
		return type;
	}

	/**
	 * @return OrdType.Limited
	 */
	public static final OrdType defaultType() {
		return OrdType.Limited;
	}

	/**
	 * 
	 * @author yellow013
	 */
	public static interface OrdTypeCode {

		char INVALID = 'I';

		char LIMITED = 'L';

		char MARKET = 'M';
		/**
		 * Limited Stop, 在目前的市场价格达到指定的止损价格时, 被激活成为限价单的报单.
		 */
		char LIMITED_STOP = 'S';
		/**
		 * Market Stop, 在目前的市场价格达到指定的止损价格时, 被激活成为市价单的报单.
		 */
		char MARKET_STOP = 'P';
		/**
		 * Market To Limited, 按照市价报单的方式成交, 不能成交的部分保留在报单队列中, 变成限价单的报单.
		 */
		char MTL = 'K';
		/**
		 * Best Price, 不带有价格限定, 按照市场中存在的最好价格买入或者卖出的报单.
		 */
		char BP = 'B';
		/**
		 * Average Price, 限定最终成交平均价格的报单.
		 */
		char AP = 'V';
		/**
		 * Fill Or Kill, 表示要求立即全部成交, 否则就全部取消的报单.
		 */
		char FOK = 'O';
		/**
		 * Fill And Kill, 表示要求立即成交, 对于无法满足的部分予以取消的报单.
		 */
		char FAK = 'A';
		/**
		 * Minimum Volume, 要求满足成交量达到这个最小成交量, 否则就取消的报单.
		 */
		char MV = 'M';
	}

}
