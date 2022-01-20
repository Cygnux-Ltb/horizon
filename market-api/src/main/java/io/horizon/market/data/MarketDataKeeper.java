package io.horizon.market.data;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import org.eclipse.collections.api.map.ImmutableMap;
import org.slf4j.Logger;

import io.horizon.market.api.MarketData;
import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.InstrumentKeeper;
import io.mercury.common.collections.MutableMaps;
import io.mercury.common.log.Log4j2LoggerFactory;
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
	private static final Logger log = Log4j2LoggerFactory.getLogger(MarketDataKeeper.class);

	// LastMarkerDataMap
	private final ImmutableMap<String, MarketDataSnapshot> map;

	private final static MarketDataKeeper Instance = new MarketDataKeeper();

	private MarketDataKeeper() {
		var map = MutableMaps.<String, MarketDataSnapshot>newUnifiedMap();
		var instruments = InstrumentKeeper.getInstruments();
		if (instruments.isEmpty())
			throw new IllegalStateException("InstrumentKeeper is uninitialized");
		instruments.each(e -> {
			map.put(e.getInstrumentCode(), new MarketDataSnapshot());
			log.info("Add instrument, instrumentId==[{}], instrument -> {}", e.getInstrumentId(), e);
		});
		this.map = map.toImmutable();
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
		return Instance.map.get(instrumentCode);
	}

	/**
	 * 
	 * @author yellow013
	 *
	 */

	@Override
	public String toString() {
		return JsonWrapper.toPrettyJsonHasNulls(map);
	}

}
