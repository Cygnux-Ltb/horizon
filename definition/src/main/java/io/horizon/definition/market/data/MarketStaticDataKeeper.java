package io.horizon.definition.market.data;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public final class MarketStaticDataKeeper {

	// TODO
	// Map

	@Setter
	@Getter
	@Accessors(chain = true)
	public static final class MarketStaticData {
		// 交易日
		private final String tradingDay;
		// instrumentID
		private final int instrumentID;
		// instrumentCode
		private final String instrumentCode;
		// 昨结算价
		private long preSettlementPrice;
		// 昨收盘价
		private long preClosePrice;
		// 昨持仓量
		private long preOpenInterest;
		// 今日开盘价
		private long openPrice;
		// 涨停板价
		private long upperLimitPrice;
		// 跌停板价
		private long lowerLimitPrice;
		// 昨Delta
		private long preDelta;

		public MarketStaticData(String tradingDay, int instrumentID, String instrumentCode) {
			this.tradingDay = tradingDay;
			this.instrumentID = instrumentID;
			this.instrumentCode = instrumentCode;
		}

	}
}
