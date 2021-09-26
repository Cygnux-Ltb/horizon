package io.horizon.market.data;

import java.time.Instant;
import java.time.LocalDateTime;

import io.horizon.market.instrument.Instrument;
import io.mercury.common.datetime.Timestamp;

public interface MarketData {

	Instrument getInstrument();

	default int getInstrumentId() {
		return getInstrument().getInstrumentId();
	}

	default String getInstrumentCode() {
		return getInstrument().getInstrumentCode();
	}

	default Timestamp getTimestamp() {
		return Timestamp.newWithEpochMillis(getEpochMillis());
	}

	default LocalDateTime getDatetime() {
		return getTimestamp().getZonedDateTime().toLocalDateTime();
	}

	Instant getInstant();

	long getEpochMillis();

	long getLastPrice();

	int getVolume();

	long getTurnover();

	/********************** Bid Price ************************/
	long[] getBidPrice();

	default long getBidPrice1() {
		return getBidPrice()[0];
	}

	default long getBidPrice2() {
		return getBidPrice().length > 1 ? getBidPrice()[1] : 0L;
	}

	default long getBidPrice3() {
		return getBidPrice().length > 2 ? getBidPrice()[2] : 0L;
	}

	default long getBidPrice4() {
		return getBidPrice().length > 3 ? getBidPrice()[3] : 0L;
	}

	default long getBidPrice5() {
		return getBidPrice().length > 4 ? getBidPrice()[4] : 0L;
	}

	/********************** Bid Volume ************************/
	int[] getBidVolume();

	default int getBidVolume1() {
		return getBidVolume()[0];
	}

	default int getBidVolume2() {
		return getBidVolume().length > 1 ? getBidVolume()[1] : 0;
	}

	default int getBidVolume3() {
		return getBidVolume().length > 2 ? getBidVolume()[2] : 0;
	}

	default int getBidVolume4() {
		return getBidVolume().length > 3 ? getBidVolume()[3] : 0;
	}

	default int getBidVolume5() {
		return getBidVolume().length > 4 ? getBidVolume()[4] : 0;
	}

	/********************** Ask Price ************************/
	long[] getAskPrice();

	default long getAskPrice1() {
		return getAskPrice()[0];
	}

	default long getAskPrice2() {
		return getAskPrice().length > 1 ? getAskPrice()[1] : 0L;
	}

	default long getAskPrice3() {
		return getAskPrice().length > 2 ? getAskPrice()[2] : 0L;
	}

	default long getAskPrice4() {
		return getAskPrice().length > 3 ? getAskPrice()[3] : 0L;
	}

	default long getAskPrice5() {
		return getAskPrice().length > 4 ? getAskPrice()[4] : 0L;
	}

	/********************** Ask Volume ************************/
	int[] getAskVolume();

	default int getAskVolume1() {
		return getAskVolume()[0];
	}

	default int getAskVolume2() {
		return getAskVolume().length > 1 ? getAskVolume()[1] : 0;
	}

	default int getAskVolume3() {
		return getAskVolume().length > 2 ? getAskVolume()[2] : 0;
	}

	default int getAskVolume4() {
		return getAskVolume().length > 3 ? getAskVolume()[3] : 0;
	}

	default int getAskVolume5() {
		return getAskVolume().length > 4 ? getAskVolume()[4] : 0;
	}

}
