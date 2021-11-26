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

	@Override
	public String toString() {
		return JsonWrapper.toPrettyJsonHasNulls(internalMap);
	}

}
