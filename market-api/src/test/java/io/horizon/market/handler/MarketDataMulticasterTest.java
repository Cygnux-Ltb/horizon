package io.horizon.market.handler;

import org.junit.Test;

public class MarketDataMulticasterTest {

	@Test
	public void test() {

//		private final MarketDataMulticaster<FtdcDepthMarketData, FastMarketDataBridge> multicaster = new MarketDataMulticaster<>(
//		getAdaptorId(), FastMarketDataBridge::newInstance, (marketData, sequence, ftdcMarketData) -> {
//			Instrument instrument = InstrumentKeeper.getInstrument(ftdcMarketData.getInstrumentID());
//			marketData.setInstrument(instrument);
//			var multiplier = instrument.getSymbol().getMultiplier();
//			var fastMarketData = marketData.getFastMarketData();
//			// TODO
//			fastMarketData.setLastPrice(multiplier.toLong(ftdcMarketData.getLastPrice()));
//			marketData.updated();
//		});

	}

}
