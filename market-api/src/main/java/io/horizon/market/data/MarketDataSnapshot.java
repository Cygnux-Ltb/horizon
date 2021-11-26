package io.horizon.market.data;

public class MarketDataSnapshot {

	MarketDataSnapshot() {
	}

	/**
	 * 五档卖价
	 */
	volatile long askPrice1;
	volatile int askVolume1;
	volatile long askPrice2;
	volatile int askVolume2;
	volatile long askPrice3;
	volatile int askVolume3;
	volatile long askPrice4;
	volatile int askVolume4;
	volatile long askPrice5;
	volatile int askVolume5;

	/**
	 * 五档买价
	 */
	volatile long bidPrice1;
	volatile int bidVolume1;
	volatile long bidPrice2;
	volatile int bidVolume2;
	volatile long bidPrice3;
	volatile int bidVolume3;
	volatile long bidPrice4;
	volatile int bidVolume4;
	volatile long bidPrice5;
	volatile int bidVolume5;

	public long getAskPrice1() {
		return askPrice1;
	}

	public int getAskVolume1() {
		return askVolume1;
	}

	public long getAskPrice2() {
		return askPrice2;
	}

	public int getAskVolume2() {
		return askVolume2;
	}

	public long getAskPrice3() {
		return askPrice3;
	}

	public int getAskVolume3() {
		return askVolume3;
	}

	public long getAskPrice4() {
		return askPrice4;
	}

	public int getAskVolume4() {
		return askVolume4;
	}

	public long getAskPrice5() {
		return askPrice5;
	}

	public int getAskVolume5() {
		return askVolume5;
	}

	public long getBidPrice1() {
		return bidPrice1;
	}

	public int getBidVolume1() {
		return bidVolume1;
	}

	public long getBidPrice2() {
		return bidPrice2;
	}

	public int getBidVolume2() {
		return bidVolume2;
	}

	public long getBidPrice3() {
		return bidPrice3;
	}

	public int getBidVolume3() {
		return bidVolume3;
	}

	public long getBidPrice4() {
		return bidPrice4;
	}

	public int getBidVolume4() {
		return bidVolume4;
	}

	public long getBidPrice5() {
		return bidPrice5;
	}

	public int getBidVolume5() {
		return bidVolume5;
	}

	public MarketDataSnapshot setAskPrice1(long askPrice1) {
		this.askPrice1 = askPrice1;
		return this;
	}

	public MarketDataSnapshot setAskVolume1(int askVolume1) {
		this.askVolume1 = askVolume1;
		return this;
	}

	public MarketDataSnapshot setAskPrice2(long askPrice2) {
		this.askPrice2 = askPrice2;
		return this;
	}

	public MarketDataSnapshot setAskVolume2(int askVolume2) {
		this.askVolume2 = askVolume2;
		return this;
	}

	public MarketDataSnapshot setAskPrice3(long askPrice3) {
		this.askPrice3 = askPrice3;
		return this;
	}

	public MarketDataSnapshot setAskVolume3(int askVolume3) {
		this.askVolume3 = askVolume3;
		return this;
	}

	public MarketDataSnapshot setAskPrice4(long askPrice4) {
		this.askPrice4 = askPrice4;
		return this;
	}

	public MarketDataSnapshot setAskVolume4(int askVolume4) {
		this.askVolume4 = askVolume4;
		return this;
	}

	public MarketDataSnapshot setAskPrice5(long askPrice5) {
		this.askPrice5 = askPrice5;
		return this;
	}

	public MarketDataSnapshot setAskVolume5(int askVolume5) {
		this.askVolume5 = askVolume5;
		return this;
	}

	public MarketDataSnapshot setBidPrice1(long bidPrice1) {
		this.bidPrice1 = bidPrice1;
		return this;
	}

	public MarketDataSnapshot setBidVolume1(int bidVolume1) {
		this.bidVolume1 = bidVolume1;
		return this;
	}

	public MarketDataSnapshot setBidPrice2(long bidPrice2) {
		this.bidPrice2 = bidPrice2;
		return this;
	}

	public MarketDataSnapshot setBidVolume2(int bidVolume2) {
		this.bidVolume2 = bidVolume2;
		return this;
	}

	public MarketDataSnapshot setBidPrice3(long bidPrice3) {
		this.bidPrice3 = bidPrice3;
		return this;
	}

	public MarketDataSnapshot setBidVolume3(int bidVolume3) {
		this.bidVolume3 = bidVolume3;
		return this;
	}

	public MarketDataSnapshot setBidPrice4(long bidPrice4) {
		this.bidPrice4 = bidPrice4;
		return this;
	}

	public MarketDataSnapshot setBidVolume4(int bidVolume4) {
		this.bidVolume4 = bidVolume4;
		return this;
	}

	public MarketDataSnapshot setBidPrice5(long bidPrice5) {
		this.bidPrice5 = bidPrice5;
		return this;
	}

	public MarketDataSnapshot setBidVolume5(int bidVolume5) {
		this.bidVolume5 = bidVolume5;
		return this;
	}

}
