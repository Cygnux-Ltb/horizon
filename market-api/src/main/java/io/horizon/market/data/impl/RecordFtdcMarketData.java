package io.horizon.market.data.impl;

import io.mercury.common.serialization.JsonSerializable;
import io.mercury.serialization.json.JsonWrapper;

public final class RecordFtdcMarketData implements JsonSerializable {

	// 交易日
	private String TradingDay;
	// InstrumentID
	private String InstrumentID;
	// 交易所ID
	private String ExchangeID;
	// 最新价
	private long LastPrice;
	// 上次结算价
	private double PreSettlementPrice;
	// 昨收盘
	private double PreClosePrice;
	// 昨持仓量
	private double PreOpenInterest;
	// 开盘价
	private double OpenPrice;
	// 最高价
	private double HighestPrice;
	// 最低价
	private double LowestPrice;
	// 成交量
	private int Volume;
	// 成交金额
	private long Turnover;
	// 持仓量
	private double OpenInterest;
	// 涨停板价
	private double UpperLimitPrice;
	// 跌停板价
	private double LowerLimitPrice;

	/* 五档买价卖价及买量卖量 v */
	private long BidPrice1;
	private int BidVolume1;
	private long AskPrice1;
	private int AskVolume1;
	private long BidPrice2;
	private int BidVolume2;
	private long AskPrice2;
	private int AskVolume2;
	private long BidPrice3;
	private int BidVolume3;
	private long AskPrice3;
	private int AskVolume3;
	private long BidPrice4;
	private int BidVolume4;
	private long AskPrice4;
	private int AskVolume4;
	private long BidPrice5;
	private int BidVolume5;
	private long AskPrice5;
	private int AskVolume5;
	/* 五档买价卖价及买量卖量 ^ */

	// 平均价格
	private long AveragePrice;
	// 更新时间
	private String UpdateTime;
	// 更新毫秒数
	private int UpdateMillisec;

	public String getTradingDay() {
		return TradingDay;
	}

	public String getInstrumentID() {
		return InstrumentID;
	}

	public String getExchangeID() {
		return ExchangeID;
	}

	public long getLastPrice() {
		return LastPrice;
	}

	public double getPreSettlementPrice() {
		return PreSettlementPrice;
	}

	public double getPreClosePrice() {
		return PreClosePrice;
	}

	public double getPreOpenInterest() {
		return PreOpenInterest;
	}

	public double getOpenPrice() {
		return OpenPrice;
	}

	public double getHighestPrice() {
		return HighestPrice;
	}

	public double getLowestPrice() {
		return LowestPrice;
	}

	public int getVolume() {
		return Volume;
	}

	public long getTurnover() {
		return Turnover;
	}

	public double getOpenInterest() {
		return OpenInterest;
	}

	public double getUpperLimitPrice() {
		return UpperLimitPrice;
	}

	public double getLowerLimitPrice() {
		return LowerLimitPrice;
	}

	public long getBidPrice1() {
		return BidPrice1;
	}

	public int getBidVolume1() {
		return BidVolume1;
	}

	public long getAskPrice1() {
		return AskPrice1;
	}

	public int getAskVolume1() {
		return AskVolume1;
	}

	public long getBidPrice2() {
		return BidPrice2;
	}

	public int getBidVolume2() {
		return BidVolume2;
	}

	public long getAskPrice2() {
		return AskPrice2;
	}

	public int getAskVolume2() {
		return AskVolume2;
	}

	public long getBidPrice3() {
		return BidPrice3;
	}

	public int getBidVolume3() {
		return BidVolume3;
	}

	public long getAskPrice3() {
		return AskPrice3;
	}

	public int getAskVolume3() {
		return AskVolume3;
	}

	public long getBidPrice4() {
		return BidPrice4;
	}

	public int getBidVolume4() {
		return BidVolume4;
	}

	public long getAskPrice4() {
		return AskPrice4;
	}

	public int getAskVolume4() {
		return AskVolume4;
	}

	public long getBidPrice5() {
		return BidPrice5;
	}

	public int getBidVolume5() {
		return BidVolume5;
	}

	public long getAskPrice5() {
		return AskPrice5;
	}

	public int getAskVolume5() {
		return AskVolume5;
	}

	public long getAveragePrice() {
		return AveragePrice;
	}

	public String getUpdateTime() {
		return UpdateTime;
	}

	public int getUpdateMillisec() {
		return UpdateMillisec;
	}

	public RecordFtdcMarketData setTradingDay(String tradingDay) {
		TradingDay = tradingDay;
		return this;
	}

	public RecordFtdcMarketData setInstrumentID(String instrumentID) {
		InstrumentID = instrumentID;
		return this;
	}

	public RecordFtdcMarketData setExchangeID(String exchangeID) {
		ExchangeID = exchangeID;
		return this;
	}

	public RecordFtdcMarketData setLastPrice(long lastPrice) {
		LastPrice = lastPrice;
		return this;
	}

	public RecordFtdcMarketData setPreSettlementPrice(double preSettlementPrice) {
		PreSettlementPrice = preSettlementPrice;
		return this;
	}

	public RecordFtdcMarketData setPreClosePrice(double preClosePrice) {
		PreClosePrice = preClosePrice;
		return this;
	}

	public RecordFtdcMarketData setPreOpenInterest(double preOpenInterest) {
		PreOpenInterest = preOpenInterest;
		return this;
	}

	public RecordFtdcMarketData setOpenPrice(double openPrice) {
		OpenPrice = openPrice;
		return this;
	}

	public RecordFtdcMarketData setHighestPrice(double highestPrice) {
		HighestPrice = highestPrice;
		return this;
	}

	public RecordFtdcMarketData setLowestPrice(double lowestPrice) {
		LowestPrice = lowestPrice;
		return this;
	}

	public RecordFtdcMarketData setVolume(int volume) {
		Volume = volume;
		return this;
	}

	public RecordFtdcMarketData setTurnover(long turnover) {
		Turnover = turnover;
		return this;
	}

	public RecordFtdcMarketData setOpenInterest(double openInterest) {
		OpenInterest = openInterest;
		return this;
	}

	public RecordFtdcMarketData setUpperLimitPrice(double upperLimitPrice) {
		UpperLimitPrice = upperLimitPrice;
		return this;
	}

	public RecordFtdcMarketData setLowerLimitPrice(double lowerLimitPrice) {
		LowerLimitPrice = lowerLimitPrice;
		return this;
	}

	public RecordFtdcMarketData setBidPrice1(long bidPrice1) {
		BidPrice1 = bidPrice1;
		return this;
	}

	public RecordFtdcMarketData setBidVolume1(int bidVolume1) {
		BidVolume1 = bidVolume1;
		return this;
	}

	public RecordFtdcMarketData setAskPrice1(long askPrice1) {
		AskPrice1 = askPrice1;
		return this;
	}

	public RecordFtdcMarketData setAskVolume1(int askVolume1) {
		AskVolume1 = askVolume1;
		return this;
	}

	public RecordFtdcMarketData setBidPrice2(long bidPrice2) {
		BidPrice2 = bidPrice2;
		return this;
	}

	public RecordFtdcMarketData setBidVolume2(int bidVolume2) {
		BidVolume2 = bidVolume2;
		return this;
	}

	public RecordFtdcMarketData setAskPrice2(long askPrice2) {
		AskPrice2 = askPrice2;
		return this;
	}

	public RecordFtdcMarketData setAskVolume2(int askVolume2) {
		AskVolume2 = askVolume2;
		return this;
	}

	public RecordFtdcMarketData setBidPrice3(long bidPrice3) {
		BidPrice3 = bidPrice3;
		return this;
	}

	public RecordFtdcMarketData setBidVolume3(int bidVolume3) {
		BidVolume3 = bidVolume3;
		return this;
	}

	public RecordFtdcMarketData setAskPrice3(long askPrice3) {
		AskPrice3 = askPrice3;
		return this;
	}

	public RecordFtdcMarketData setAskVolume3(int askVolume3) {
		AskVolume3 = askVolume3;
		return this;
	}

	public RecordFtdcMarketData setBidPrice4(long bidPrice4) {
		BidPrice4 = bidPrice4;
		return this;
	}

	public RecordFtdcMarketData setBidVolume4(int bidVolume4) {
		BidVolume4 = bidVolume4;
		return this;
	}

	public RecordFtdcMarketData setAskPrice4(long askPrice4) {
		AskPrice4 = askPrice4;
		return this;
	}

	public RecordFtdcMarketData setAskVolume4(int askVolume4) {
		AskVolume4 = askVolume4;
		return this;
	}

	public RecordFtdcMarketData setBidPrice5(long bidPrice5) {
		BidPrice5 = bidPrice5;
		return this;
	}

	public RecordFtdcMarketData setBidVolume5(int bidVolume5) {
		BidVolume5 = bidVolume5;
		return this;
	}

	public RecordFtdcMarketData setAskPrice5(long askPrice5) {
		AskPrice5 = askPrice5;
		return this;
	}

	public RecordFtdcMarketData setAskVolume5(int askVolume5) {
		AskVolume5 = askVolume5;
		return this;
	}

	public RecordFtdcMarketData setAveragePrice(long averagePrice) {
		AveragePrice = averagePrice;
		return this;
	}

	public RecordFtdcMarketData setUpdateTime(String updateTime) {
		UpdateTime = updateTime;
		return this;
	}

	public RecordFtdcMarketData setUpdateMillisec(int updateMillisec) {
		UpdateMillisec = updateMillisec;
		return this;
	}

	@Override
	public String toString() {
		return JsonWrapper.toJsonHasNulls(this);
	}

	@Override
	public String toJson() {
		return this.toString();
	}

}
