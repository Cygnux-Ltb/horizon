package io.horizon.trader.order.enums;

public enum OrdType {

	Invalid('I'),

	Limited('L'),

	Market('M'),

	/**
	 * Limited Stop, 在目前的市场价格达到指定的止损价格时, 被激活成为限价单的报单.
	 */
	LimitedStop('S'),

	/**
	 * Market Stop, 在目前的市场价格达到指定的止损价格时, 被激活成为市价单的报单.
	 */
	MarketStop('P'),

	/**
	 * Market To Limited, 按照市价报单的方式成交, 不能成交的部分保留在报单队列中, 变成限价单的报单.
	 */
	MTL('K'),

	/**
	 * Best Price, 不带有价格限定, 按照市场中存在的最好价格买入或者卖出的报单.
	 */
	BP('B'),

	/**
	 * Average Price, 限定最终成交平均价格的报单.
	 */
	AP('V'),

	/**
	 * Kill Or Kill, 表示要求立即全部成交, 否则就全部取消的报单.
	 */
	FOK('O'),

	/**
	 * Fill And Kill, 表示要求立即成交, 对于无法满足的部分予以取消的报单.
	 */
	FAK('A'),

	/**
	 * Minimum Volume, 要求满足成交量达到这个最小成交量, 否则就取消的报单.
	 */
	MV('M'),

	;

	private final char code;

	private OrdType(char code) {
		this.code = code;
	}

	public char getCode() {
		return code;
	}

	/**
	 * @return OrdType.Limited
	 */
	public static final OrdType useDefault() {
		return OrdType.Limited;
	}

}
