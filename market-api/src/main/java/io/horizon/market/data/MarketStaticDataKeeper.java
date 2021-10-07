package io.horizon.market.data;

public final class MarketStaticDataKeeper {

	// TODO
	// Map

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
			super();
			this.tradingDay = tradingDay;
			this.instrumentID = instrumentID;
			this.instrumentCode = instrumentCode;
		}

		public String getTradingDay() {
			return tradingDay;
		}

		public int getInstrumentID() {
			return instrumentID;
		}

		public String getInstrumentCode() {
			return instrumentCode;
		}

		public long getPreSettlementPrice() {
			return preSettlementPrice;
		}

		public long getPreClosePrice() {
			return preClosePrice;
		}

		public long getPreOpenInterest() {
			return preOpenInterest;
		}

		public long getOpenPrice() {
			return openPrice;
		}

		public long getUpperLimitPrice() {
			return upperLimitPrice;
		}

		public long getLowerLimitPrice() {
			return lowerLimitPrice;
		}

		public long getPreDelta() {
			return preDelta;
		}

		public MarketStaticData setPreSettlementPrice(long preSettlementPrice) {
			this.preSettlementPrice = preSettlementPrice;
			return this;
		}

		public MarketStaticData setPreClosePrice(long preClosePrice) {
			this.preClosePrice = preClosePrice;
			return this;
		}

		public MarketStaticData setPreOpenInterest(long preOpenInterest) {
			this.preOpenInterest = preOpenInterest;
			return this;
		}

		public MarketStaticData setOpenPrice(long openPrice) {
			this.openPrice = openPrice;
			return this;
		}

		public MarketStaticData setUpperLimitPrice(long upperLimitPrice) {
			this.upperLimitPrice = upperLimitPrice;
			return this;
		}

		public MarketStaticData setLowerLimitPrice(long lowerLimitPrice) {
			this.lowerLimitPrice = lowerLimitPrice;
			return this;
		}

		public MarketStaticData setPreDelta(long preDelta) {
			this.preDelta = preDelta;
			return this;
		}

	}

}
