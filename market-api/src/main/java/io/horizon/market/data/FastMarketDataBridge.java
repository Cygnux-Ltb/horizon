package io.horizon.market.data;

import io.horizon.market.data.avro.FastMarketData;
import io.horizon.market.instrument.Instrument;
import io.mercury.common.datetime.Timestamp;

public final class FastMarketDataBridge implements MarketData {

	private final FastMarketData fastMarkteData;

	private Instrument instrument;

	private final long[] bidPrices = new long[5];
	private final int[] bidVolumes = new int[5];
	private final long[] askPrices = new long[5];
	private final int[] askVolumes = new int[5];

	private FastMarketDataBridge() {
		this.fastMarkteData = FastMarketData
				// call -> new builder
				.newBuilder()
				// set -> timestamp, instrumentId, instrumentCode
				.setTimestamp(0L).setInstrumentId(0).setInstrumentCode("")
				// set -> last price, volume, turnover
				.setLastPrice(0L).setVolume(0).setTurnover(0L)
				// set -> level5 bid prices
				.setBidPrices1(0L).setBidPrices2(0L).setBidPrices3(0L).setBidPrices4(0L).setBidPrices5(0L)
				// set -> level5 bid volumes
				.setBidVolumes1(0).setBidVolumes2(0).setBidVolumes3(0).setBidVolumes4(0).setBidVolumes5(0)
				// set -> level5 ask prices
				.setAskPrices1(0L).setAskPrices2(0L).setAskPrices3(0L).setAskPrices4(0L).setAskPrices5(0L)
				// set -> level5 ask volumes
				.setAskVolumes1(0).setAskVolumes2(0).setAskVolumes3(0).setAskVolumes4(0).setAskVolumes5(0)
				// call -> build
				.build();
	}

	public static FastMarketDataBridge newInstance() {
		return new FastMarketDataBridge();
	}

	public FastMarketData getFastMarketData() {
		return fastMarkteData;
	}

	public FastMarketDataBridge setInstrument(Instrument instrument) {
		this.instrument = instrument;
		this.fastMarkteData.setInstrumentId(instrument.getInstrumentId());
		this.fastMarkteData.setInstrumentCode(instrument.getInstrumentCode());
		return this;
	}

	@Override
	public Instrument getInstrument() {
		return instrument;
	}

	@Override
	public int getInstrumentId() {
		return fastMarkteData.getInstrumentId();
	}

	@Override
	public String getInstrumentCode() {
		return fastMarkteData.getInstrumentCode();
	}

	@Override
	public long getEpochMillis() {
		return fastMarkteData.getTimestamp();
	}

	@Override
	public Timestamp getTimestamp() {
		return Timestamp.withEpochMillis(fastMarkteData.getTimestamp());
	}

	@Override
	public long getLastPrice() {
		return fastMarkteData.getLastPrice();
	}

	@Override
	public int getVolume() {
		return fastMarkteData.getVolume();
	}

	@Override
	public long getTurnover() {
		return fastMarkteData.getTurnover();
	}

	@Override
	public int getDepth() {
		return 5;
	}

	@Override
	public long[] getBidPrices() {
		return bidPrices;
	}

	@Override
	public long getBidPrice1() {
		return fastMarkteData.getBidPrices1();
	}

	@Override
	public long getBidPrice2() {
		return fastMarkteData.getBidPrices2();
	}

	@Override
	public long getBidPrice3() {
		return fastMarkteData.getBidPrices3();
	}

	@Override
	public long getBidPrice4() {
		return fastMarkteData.getBidPrices4();
	}

	@Override
	public long getBidPrice5() {
		return fastMarkteData.getBidPrices5();
	}

	@Override
	public int[] getBidVolumes() {
		return bidVolumes;
	}

	@Override
	public int getBidVolume1() {
		return fastMarkteData.getBidVolumes1();
	}

	@Override
	public int getBidVolume2() {
		return fastMarkteData.getBidVolumes2();
	}

	@Override
	public int getBidVolume3() {
		return fastMarkteData.getBidVolumes3();
	}

	@Override
	public int getBidVolume4() {
		return fastMarkteData.getBidVolumes4();
	}

	@Override
	public int getBidVolume5() {
		return fastMarkteData.getBidVolumes5();
	}

	@Override
	public long[] getAskPrices() {
		return askPrices;
	}

	@Override
	public long getAskPrice1() {
		return fastMarkteData.getAskPrices1();
	}

	@Override
	public long getAskPrice2() {
		return fastMarkteData.getAskPrices2();
	}

	@Override
	public long getAskPrice3() {
		return fastMarkteData.getAskPrices3();
	}

	@Override
	public long getAskPrice4() {
		return fastMarkteData.getAskPrices4();
	}

	@Override
	public long getAskPrice5() {
		return fastMarkteData.getAskPrices5();
	}

	@Override
	public int[] getAskVolumes() {
		return askVolumes;
	}

	@Override
	public int getAskVolume1() {
		return fastMarkteData.getAskVolumes1();

	}

	@Override
	public int getAskVolume2() {
		return fastMarkteData.getAskVolumes2();
	}

	@Override
	public int getAskVolume3() {
		return fastMarkteData.getAskVolumes3();
	}

	@Override
	public int getAskVolume4() {
		return fastMarkteData.getAskVolumes4();
	}

	@Override
	public int getAskVolume5() {
		return fastMarkteData.getAskVolumes5();
	}

	@Override
	public void updated() {
		this.bidPrices[0] = fastMarkteData.getBidPrices1();
		this.bidPrices[1] = fastMarkteData.getBidPrices2();
		this.bidPrices[2] = fastMarkteData.getBidPrices3();
		this.bidPrices[3] = fastMarkteData.getBidPrices4();
		this.bidPrices[4] = fastMarkteData.getBidPrices5();
		this.bidVolumes[0] = fastMarkteData.getBidVolumes1();
		this.bidVolumes[1] = fastMarkteData.getBidVolumes2();
		this.bidVolumes[2] = fastMarkteData.getBidVolumes3();
		this.bidVolumes[3] = fastMarkteData.getBidVolumes4();
		this.bidVolumes[4] = fastMarkteData.getBidVolumes5();
		this.askPrices[0] = fastMarkteData.getAskPrices1();
		this.askPrices[1] = fastMarkteData.getAskPrices2();
		this.askPrices[2] = fastMarkteData.getAskPrices3();
		this.askPrices[3] = fastMarkteData.getAskPrices4();
		this.askPrices[4] = fastMarkteData.getAskPrices5();
		this.askVolumes[0] = fastMarkteData.getAskVolumes1();
		this.askVolumes[1] = fastMarkteData.getAskVolumes2();
		this.askVolumes[2] = fastMarkteData.getAskVolumes3();
		this.askVolumes[3] = fastMarkteData.getAskVolumes4();
		this.askVolumes[4] = fastMarkteData.getAskVolumes5();
	}

}
