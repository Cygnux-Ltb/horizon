package io.horizon.structure.order.enums;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrdSide {

	Invalid(-1, TrdDirection.Invalid),

	Buy(1, TrdDirection.Long),

	Sell(2, TrdDirection.Short),

	MarginBuy(4, TrdDirection.Long),

	ShortSell(8, TrdDirection.Short),

	;

	@Getter
	private final int code;

	@Getter
	private final TrdDirection direction;

	private static final Logger log = CommonLoggerFactory.getLogger(OrdSide.class);

	public static OrdSide valueOf(int code) {
		switch (code) {
		case 1:
			return Buy;
		case 2:
			return Sell;
		case 4:
			return MarginBuy;
		case 8:
			return ShortSell;
		default:
			log.error("OrdSide.valueOf(code=={}) -> is no matches, return OrdSide.Invalid", code);
			return Invalid;
		}
	}

}
