package io.horizon.market.data;

import io.mercury.common.datetime.Timestamp;

public interface MarketData {

	int getInstrumentId();

	String getInstrumentCode();

	long getEpochMillis();

	Timestamp getTimestamp();

	/**
	 * 最新价
	 * 
	 * @return
	 */
	double getLastPrice();

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
	double[] getBidPrices();

	double getBidPrice1();

	double getBidPrice2();

	double getBidPrice3();

	double getBidPrice4();

	double getBidPrice5();

	/********************** Bid Volume ************************/
	int[] getBidVolumes();

	int getBidVolume1();

	int getBidVolume2();

	int getBidVolume3();

	int getBidVolume4();

	int getBidVolume5();

	/********************** Ask Price ************************/
	double[] getAskPrices();

	double getAskPrice1();

	double getAskPrice2();

	double getAskPrice3();

	double getAskPrice4();

	double getAskPrice5();

	/********************** Ask Volume ************************/
	int[] getAskVolumes();

	int getAskVolume1();

	int getAskVolume2();

	int getAskVolume3();

	int getAskVolume4();

	int getAskVolume5();

	/**
	 * 用于触发后续事件
	 */
	default void updated() {

	}

}
