package io.horizon.structure.market.data;

import java.time.LocalDateTime;

import io.mercury.common.datetime.Timestamp;

public interface MarketData {

	int getInstrumentId();
	
	String getInstrumentCode();

	default Timestamp getTimestamp() {
		return Timestamp.newWithEpochMillis(getEpochMillis());
	}

	LocalDateTime getDatetime();

	long getEpochMillis();

	long getLastPrice();

	int getVolume();

	long getTurnover();

	long getBidPrice1();

	int getBidVolume1();

	long getAskPrice1();

	int getAskVolume1();

}
