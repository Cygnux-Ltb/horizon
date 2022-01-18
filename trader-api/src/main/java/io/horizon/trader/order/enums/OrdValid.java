package io.horizon.trader.order.enums;

import static io.horizon.trader.order.enums.OrdValid.OrdValidCode.GFD;
import static io.horizon.trader.order.enums.OrdValid.OrdValidCode.GTC;
import static io.horizon.trader.order.enums.OrdValid.OrdValidCode.GTD;

import io.horizon.trader.transport.enums.TOrdValid;

public enum OrdValid {

	/**
	 * Good Till Cancel, 将一直有效, 直到交易员取消这个报单, 或者该合约本身到期的报单.
	 */
	GoodTillCancel(GTC, TOrdValid.GTC),

	/**
	 * Good Till Date, 将一直有效, 直到指定日期或交易员取消这个报单, 或者该合约本身到期的报单.
	 */
	GoodTillDate(GTD, TOrdValid.GTD),

	/**
	 * Good For Day, 只在当日的交易时段有效, 一旦当前交易时段结束, 自动取消的报单.
	 */
	GoodForDay(GFD, TOrdValid.GFD),

	;

	private final char code;

	private final TOrdValid valid;

	private OrdValid(char code, TOrdValid valid) {
		this.code = code;
		this.valid = valid;
	}

	public char getCode() {
		return code;
	}

	public TOrdValid getTOrdValid() {
		return valid;
	}

	/**
	 * @return OrdValid.GoodTillCancel
	 */
	public static final OrdValid defaultValid() {
		return OrdValid.GoodTillCancel;
	}

	public static interface OrdValidCode {
		// Good Till Cancel
		char GTC = 'C';
		// Good Till Date
		char GTD = 'D';
		// Good For Day
		char GFD = 'G';
	}

}
