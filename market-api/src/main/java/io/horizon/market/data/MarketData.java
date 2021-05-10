package io.horizon.market.data;

import java.time.LocalDateTime;

import io.mercury.common.datetime.Timestamp;

public interface MarketData {

	int getInstrumentId();

	String getInstrumentCode();

	default Timestamp getTimestamp() {
		return Timestamp.newWithEpochMillis(getEpochMillis());
	}

	default LocalDateTime getDatetime() {
		return getTimestamp().getZonedDateTime().toLocalDateTime();
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
