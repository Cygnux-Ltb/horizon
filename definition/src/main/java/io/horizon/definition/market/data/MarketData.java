package io.horizon.definition.market.data;

import io.mercury.common.datetime.Timestamp;

public interface MarketData {

	String getInstrumentCode();

	default Timestamp getTimestamp() {
		return Timestamp.newWithEpochMillis(getEpochMillis());
	}

	long getEpochMillis();

	long getLastPrice();

	int getVolume();

	long getTurnover();

	long getBidPrice1();

	int getBidVolume1();

	long getAskPrice1();

	int getAskVolume1();

}
