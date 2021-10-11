package io.horizon.market.data;

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

	long getEpochMillis();

	Timestamp getTimestamp();

	/**
	 * 最新价
	 * 
	 * @return
	 */
	long getLastPrice();

	/**
	 * 成交量
	 * 
	 * @return
	 */
	int getVolume();

	/**
	 * 成交金额
	 * 
	 * @return
	 */
	long getTurnover();

	/**
	 * 行情深度
	 * 
	 * @return
	 */
	int getDepth();

	/********************** Bid Price ************************/
	long[] getBidPrices();

	long getBidPrice1();

	long getBidPrice2();

	long getBidPrice3();

	long getBidPrice4();

	long getBidPrice5();

	/********************** Bid Volume ************************/
	int[] getBidVolumes();

	int getBidVolume1();

	int getBidVolume2();

	int getBidVolume3();

	int getBidVolume4();

	int getBidVolume5();

	/********************** Ask Price ************************/
	long[] getAskPrices();

	long getAskPrice1();

	long getAskPrice2();

	long getAskPrice3();

	long getAskPrice4();

	long getAskPrice5();

	/********************** Ask Volume ************************/
	int[] getAskVolumes();

	int getAskVolume1();

	int getAskVolume2();

	int getAskVolume3();

	int getAskVolume4();

	int getAskVolume5();

}
