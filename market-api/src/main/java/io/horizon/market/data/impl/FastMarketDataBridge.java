package io.horizon.market.data.impl;

import io.horizon.market.data.MarketData;
import io.horizon.market.instrument.Instrument;
import io.horizon.market.transport.outbound.FastMarketData;
import io.mercury.common.datetime.Timestamp;

public final class FastMarketDataBridge implements MarketData {

	private final FastMarketData markteData;

	private final double[] bidPrices = new double[5];
	private final int[] bidVolumes = new int[5];
	private final double[] askPrices = new double[5];
	private final int[] askVolumes = new int[5];

	private FastMarketDataBridge() {
		this.markteData = FastMarketData
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

	/**
	 * 
	 * @return
	 */
	public static FastMarketDataBridge newInstance() {
		return new FastMarketDataBridge();
	}

	public FastMarketData getFastMarketData() {
		return markteData;
	}

	public FastMarketDataBridge setInstrument(Instrument instrument) {
		this.markteData.setInstrumentId(instrument.getInstrumentId());
		this.markteData.setInstrumentCode(instrument.getInstrumentCode());
		return this;
	}

	@Override
	public int getInstrumentId() {
		return markteData.getInstrumentId();
	}

	@Override
	public String getInstrumentCode() {
		return markteData.getInstrumentCode();
	}

	@Override
	public long getEpochMillis() {
		return markteData.getTimestamp();
	}

	@Override
	public Timestamp getTimestamp() {
		return Timestamp.withEpochMillis(markteData.getTimestamp());
	}

	@Override
	public double getLastPrice() {
		return markteData.getLastPrice();
	}

	@Override
	public int getVolume() {
		return markteData.getVolume();
	}

	@Override
	public long getTurnover() {
		return markteData.getTurnover();
	}

	@Override
	public int getDepth() {
		return 5;
	}

	@Override
	public double[] getBidPrices() {
		return bidPrices;
	}

	@Override
	public double getBidPrice1() {
		return markteData.getBidPrices1();
	}

	@Override
	public double getBidPrice2() {
		return markteData.getBidPrices2();
	}

	@Override
	public double getBidPrice3() {
		return markteData.getBidPrices3();
	}

	@Override
	public double getBidPrice4() {
		return markteData.getBidPrices4();
	}

	@Override
	public double getBidPrice5() {
		return markteData.getBidPrices5();
	}

	@Override
	public int[] getBidVolumes() {
		return bidVolumes;
	}

	@Override
	public int getBidVolume1() {
		return markteData.getBidVolumes1();
	}

	@Override
	public int getBidVolume2() {
		return markteData.getBidVolumes2();
	}

	@Override
	public int getBidVolume3() {
		return markteData.getBidVolumes3();
	}

	@Override
	public int getBidVolume4() {
		return markteData.getBidVolumes4();
	}

	@Override
	public int getBidVolume5() {
		return markteData.getBidVolumes5();
	}

	@Override
	public double[] getAskPrices() {
		return askPrices;
	}

	@Override
	public double getAskPrice1() {
		return markteData.getAskPrices1();
	}

	@Override
	public double getAskPrice2() {
		return markteData.getAskPrices2();
	}

	@Override
	public double getAskPrice3() {
		return markteData.getAskPrices3();
	}

	@Override
	public double getAskPrice4() {
		return markteData.getAskPrices4();
	}

	@Override
	public double getAskPrice5() {
		return markteData.getAskPrices5();
	}

	@Override
	public int[] getAskVolumes() {
		return askVolumes;
	}

	@Override
	public int getAskVolume1() {
		return markteData.getAskVolumes1();

	}

	@Override
	public int getAskVolume2() {
		return markteData.getAskVolumes2();
	}

	@Override
	public int getAskVolume3() {
		return markteData.getAskVolumes3();
	}

	@Override
	public int getAskVolume4() {
		return markteData.getAskVolumes4();
	}

	@Override
	public int getAskVolume5() {
		return markteData.getAskVolumes5();
	}

	@Override
	public void updated() {
		this.bidPrices[0] = markteData.getBidPrices1();
		this.bidPrices[1] = markteData.getBidPrices2();
		this.bidPrices[2] = markteData.getBidPrices3();
		this.bidPrices[3] = markteData.getBidPrices4();
		this.bidPrices[4] = markteData.getBidPrices5();
		this.bidVolumes[0] = markteData.getBidVolumes1();
		this.bidVolumes[1] = markteData.getBidVolumes2();
		this.bidVolumes[2] = markteData.getBidVolumes3();
		this.bidVolumes[3] = markteData.getBidVolumes4();
		this.bidVolumes[4] = markteData.getBidVolumes5();
		this.askPrices[0] = markteData.getAskPrices1();
		this.askPrices[1] = markteData.getAskPrices2();
		this.askPrices[2] = markteData.getAskPrices3();
		this.askPrices[3] = markteData.getAskPrices4();
		this.askPrices[4] = markteData.getAskPrices5();
		this.askVolumes[0] = markteData.getAskVolumes1();
		this.askVolumes[1] = markteData.getAskVolumes2();
		this.askVolumes[2] = markteData.getAskVolumes3();
		this.askVolumes[3] = markteData.getAskVolumes4();
		this.askVolumes[4] = markteData.getAskVolumes5();
	}

}
