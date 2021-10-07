package io.horizon.market.data;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.slf4j.Logger;

import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.InstrumentKeeper;
import io.mercury.common.collections.MutableMaps;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.serialization.json.JsonWrapper;

/**
 * 管理当前最新行情<br>
 * 
 * 仅在初始化时使用InstrumentKeeper加载一次Instrument<br>
 * 
 * 无论修改最新行情或查询最新行情都使用GetLast方法获取对象<br>
 * 对象使用原子类型保证
 * 
 * @creation 2019年4月16日
 * 
 * @author yellow013
 */
@ThreadSafe
public final class MarketDataKeeper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2145644316828652275L;

	// Logger
	private static final Logger log = CommonLoggerFactory.getLogger(MarketDataKeeper.class);

	// LastMarkerDataMap
	private final ImmutableMap<String, MarketDataSnapshot> internalMap;

	private final static MarketDataKeeper StaticInstance = new MarketDataKeeper();

	private MarketDataKeeper() {
		MutableMap<String, MarketDataSnapshot> tempMap = MutableMaps.newUnifiedMap();
		ImmutableList<Instrument> instruments = InstrumentKeeper.getInstruments();
		if (instruments.isEmpty())
			throw new IllegalStateException("InstrumentKeeper is uninitialized");
		instruments.each(instrument -> {
			tempMap.put(instrument.getInstrumentCode(), new MarketDataSnapshot());
			log.info("Add instrument, instrumentId==[{}], instrument -> {}", instrument.getInstrumentId(), instrument);
		});
		internalMap = tempMap.toImmutable();
	}

	/**
	 * 
	 * @param marketData
	 */
	public static void onMarketDate(@Nonnull final MarketData marketData) {
		String instrumentCode = marketData.getInstrumentCode();
		MarketDataSnapshot snapshot = getSnapshot(instrumentCode);
		if (snapshot == null) {
			log.warn("Instrument unregistered, instrumentCode -> {}", instrumentCode);
		} else {
			snapshot.askPrice1 = marketData.getAskPrice1();
			snapshot.askVolume1 = marketData.getAskVolume1();
			snapshot.bidPrice1 = marketData.getBidPrice1();
			snapshot.bidVolume1 = marketData.getBidVolume1();
		}
	}

	/**
	 * 
	 * @param instrument
	 * @return
	 */
	public static MarketDataSnapshot getSnapshot(Instrument instrument) {
		return getSnapshot(instrument.getInstrumentCode());
	}

	/**
	 * 
	 * @param instrumentCode
	 * @return
	 */
	public static MarketDataSnapshot getSnapshot(String instrumentCode) {
		return StaticInstance.internalMap.get(instrumentCode);
	}

	/**
	 * 
	 * @author yellow013
	 *
	 */
	public static class MarketDataSnapshot {

		private MarketDataSnapshot() {
		}

		/**
		 * 五档卖价
		 */
		private volatile long askPrice1;
		private volatile int askVolume1;
		private volatile long askPrice2;
		private volatile int askVolume2;
		private volatile long askPrice3;
		private volatile int askVolume3;
		private volatile long askPrice4;
		private volatile int askVolume4;
		private volatile long askPrice5;
		private volatile int askVolume5;

		/**
		 * 五档买价
		 */
		private volatile long bidPrice1;
		private volatile int bidVolume1;
		private volatile long bidPrice2;
		private volatile int bidVolume2;
		private volatile long bidPrice3;
		private volatile int bidVolume3;
		private volatile long bidPrice4;
		private volatile int bidVolume4;
		private volatile long bidPrice5;
		private volatile int bidVolume5;
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

	@Override
	public String toString() {
		return JsonWrapper.toPrettyJsonHasNulls(internalMap);
	}

}
