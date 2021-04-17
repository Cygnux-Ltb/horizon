package io.horizon.structure.market.data;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.slf4j.Logger;

import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.market.instrument.InstrumentKeeper;
import io.mercury.common.collections.MutableMaps;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.serialization.json.JsonWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
public final class MarkerDataKeeper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2145644316828652275L;

	// Logger
	private static final Logger log = CommonLoggerFactory.getLogger(MarkerDataKeeper.class);

	// LastMarkerDataMap
	private final ImmutableMap<String, LastMarkerData> InternalMap;

	private final static MarkerDataKeeper StaticInstance = new MarkerDataKeeper();

	private MarkerDataKeeper() {
		MutableMap<String, LastMarkerData> tempMap = MutableMaps.newUnifiedMap();
		ImmutableList<Instrument> instruments = InstrumentKeeper.getInstruments();
		if (instruments.isEmpty())
			throw new IllegalStateException("InstrumentKeeper is uninitialized");
		instruments.each(instrument -> {
			tempMap.put(instrument.getInstrumentCode(), new LastMarkerData());
			log.info("Add instrument, instrumentId==[{}], instrument -> {}", instrument.getInstrumentId(), instrument);
		});
		InternalMap = tempMap.toImmutable();
	}

	/**
	 * 
	 * @param marketData
	 */
	public static void onMarketDate(@Nonnull final MarketData marketData) {
		String instrumentCode = marketData.getInstrumentCode();
		LastMarkerData lastMarkerData = getLastMarkerData(instrumentCode);
		if (lastMarkerData == null) {
			log.warn("Instrument unregistered, instrumentCode -> {}", instrumentCode);
		} else {
			lastMarkerData.setAskPrice1(marketData.getAskPrice1()).setAskVolume1(marketData.getAskVolume1())
					.setBidPrice1(marketData.getBidPrice1()).setBidVolume1(marketData.getBidVolume1());
		}
	}

	/**
	 * 
	 * @param instrument
	 * @return
	 */
	public static LastMarkerData getLastMarkerData(Instrument instrument) {
		return getLastMarkerData(instrument.getInstrumentCode());
	}

	/**
	 * 
	 * @param instrumentCode
	 * @return
	 */
	public static LastMarkerData getLastMarkerData(String instrumentCode) {
		return StaticInstance.InternalMap.get(instrumentCode);
	}

	/**
	 * 
	 * @author yellow013
	 *
	 */
	@Getter
	@Setter
	@Accessors(chain = true)
	public static class LastMarkerData {

		private volatile long askPrice1;
		private volatile int askVolume1;
		private volatile long bidPrice1;
		private volatile int bidVolume1;

	}

	@Override
	public String toString() {
		return JsonWrapper.toPrettyJsonHasNulls(InternalMap);
	}

}
