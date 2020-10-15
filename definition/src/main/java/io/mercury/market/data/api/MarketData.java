package io.mercury.market.data.api;

public interface MarketData {

	String getInstrumentCode();

	long getEpochMillis();

	long getLastPrice();

	int getVolume();

	long getTurnover();

	long getBidPrice1();

	int getBidVolume1();

	long getAskPrice1();

	int getAskVolume1();

}
