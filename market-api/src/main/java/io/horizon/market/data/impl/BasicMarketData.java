package io.horizon.market.data.impl;

import io.horizon.market.data.MarketData;
import io.horizon.market.instrument.Instrument;
import io.mercury.common.datetime.Timestamp;
import io.mercury.common.serialization.specific.JsonSerializable;
import io.mercury.serialization.json.JsonWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static io.mercury.common.datetime.Timestamp.withDateTime;
import static io.mercury.common.datetime.Timestamp.withEpochMillis;

/**
 * @author yellow013
 * @creation 2019年5月24日
 * @description 价格转换使用对应Instrument的价格乘数
 */
public class BasicMarketData implements MarketData, JsonSerializable {

	// Required
	protected final Instrument instrument;
	// Required
	protected final long epochMillis;

	/** base info **/
	protected Timestamp timestamp;
	protected double lastPrice;
	protected int volume;
	protected long turnover;

	protected final double[] bidPrices;
	protected final int[] bidVolumes;
	protected final double[] askPrices;
	protected final int[] askVolumes;

	protected final int depth;

	protected BasicMarketData(@Nonnull Instrument instrument, long epochMillis, int depth) {
		this(instrument, epochMillis, null, depth);
	}

	protected BasicMarketData(@Nonnull Instrument instrument, long epochMillis, @Nullable Timestamp timestamp,
			int depth) {
		this.instrument = instrument;
		this.epochMillis = epochMillis;
		this.timestamp = timestamp;
		this.depth = depth;
		this.bidPrices = new double[depth];
		this.bidVolumes = new int[depth];
		this.askPrices = new double[depth];
		this.askVolumes = new int[depth];
	}

	/**
	 * 
	 * @param instrument
	 * @param epochMillis
	 * @return
	 */
	public static final BasicMarketData newLevel5(@Nonnull Instrument instrument, @Nonnull long epochMillis) {
		Timestamp timestamp = withEpochMillis(epochMillis);
		return new BasicMarketData(instrument, epochMillis, timestamp, 5);
	}

	/**
	 * 
	 * @param instrument
	 * @param datetime
	 * @return
	 */
	public static final BasicMarketData newLevel5(@Nonnull Instrument instrument, @Nonnull LocalDateTime datetime) {
		Timestamp timestamp = withDateTime(datetime, instrument.getZoneOffset());
		return new BasicMarketData(instrument, timestamp.getEpoch(), timestamp, 5);
	}

	/**
	 * 
	 * @param instrument
	 * @param date
	 * @param time
	 * @return
	 */
	public static final BasicMarketData newLevel5(@Nonnull Instrument instrument, @Nonnull LocalDate date,
			@Nonnull LocalTime time) {
		Timestamp timestamp = withDateTime(date, time, instrument.getZoneOffset());
		return new BasicMarketData(instrument, timestamp.getEpoch(), timestamp, 5);
	}

	/**
	 * 
	 * @param instrument
	 * @param timestamp
	 * @return
	 */
	public static final BasicMarketData newLevel5(@Nonnull Instrument instrument, @Nonnull Timestamp timestamp) {
		return new BasicMarketData(instrument, timestamp.getEpoch(), timestamp, 5);
	}

	/**
	 * 
	 * @param instrument
	 * @param epochMillis
	 * @return
	 */
	public static final BasicMarketData newLevel10(@Nonnull Instrument instrument, @Nonnull long epochMillis) {
		Timestamp timestamp = withEpochMillis(epochMillis);
		return new BasicMarketData(instrument, epochMillis, timestamp, 10);
	}

	/**
	 * 
	 * @param instrument
	 * @param datetime
	 * @return
	 */
	public static final BasicMarketData newLevel10(@Nonnull Instrument instrument, @Nonnull LocalDateTime datetime) {
		Timestamp timestamp = withDateTime(datetime, instrument.getZoneOffset());
		return new BasicMarketData(instrument, timestamp.getEpoch(), timestamp, 10);
	}

	/**
	 * 
	 * @param instrument
	 * @param date
	 * @param time
	 * @return
	 */
	public static final BasicMarketData newLevel10(@Nonnull Instrument instrument, @Nonnull LocalDate date,
			@Nonnull LocalTime time) {
		Timestamp timestamp = withDateTime(date, time, instrument.getZoneOffset());
		return new BasicMarketData(instrument, timestamp.getEpoch(), timestamp, 10);
	}

	/**
	 * 
	 * @param instrument
	 * @param timestamp
	 * @return
	 */
	public static final BasicMarketData newLevel10(@Nonnull Instrument instrument, @Nonnull Timestamp timestamp) {
		return new BasicMarketData(instrument, timestamp.getEpoch(), timestamp, 10);
	}

	@Override
	public int getInstrumentId() {
		return instrument.getInstrumentId();
	}

	@Override
	public String getInstrumentCode() {
		return instrument.getInstrumentCode();
	}

	@Override
	public long getEpochMillis() {
		return epochMillis;
	}

	@Override
	public Timestamp getTimestamp() {
		if (timestamp == null)
			this.timestamp = withEpochMillis(epochMillis);
		return timestamp;
	}

	@Override
	public double getLastPrice() {
		return lastPrice;
	}

	@Override
	public int getVolume() {
		return volume;
	}

	@Override
	public long getTurnover() {
		return turnover;
	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public double[] getBidPrices() {
		return bidPrices;
	}

	@Override
	public double getBidPrice1() {
		return bidPrices[0];
	}

	@Override
	public double getBidPrice2() {
		return bidPrices[1];
	}

	@Override
	public double getBidPrice3() {
		return bidPrices[2];
	}

	@Override
	public double getBidPrice4() {
		return bidPrices[3];
	}

	@Override
	public double getBidPrice5() {
		return bidPrices[4];
	}

	@Override
	public int[] getBidVolumes() {
		return bidVolumes;
	}

	@Override
	public int getBidVolume1() {
		return bidVolumes[0];
	}

	@Override
	public int getBidVolume2() {
		return bidVolumes[1];
	}

	@Override
	public int getBidVolume3() {
		return bidVolumes[2];
	}

	@Override
	public int getBidVolume4() {
		return bidVolumes[3];
	}

	@Override
	public int getBidVolume5() {
		return bidVolumes[4];
	}

	@Override
	public double[] getAskPrices() {
		return askPrices;
	}

	@Override
	public double getAskPrice1() {
		return askPrices[0];
	}

	@Override
	public double getAskPrice2() {
		return askPrices[1];
	}

	@Override
	public double getAskPrice3() {
		return askPrices[2];
	}

	@Override
	public double getAskPrice4() {
		return askPrices[3];
	}

	@Override
	public double getAskPrice5() {
		return askPrices[4];
	}

	@Override
	public int[] getAskVolumes() {
		return askVolumes;
	}

	@Override
	public int getAskVolume1() {
		return askVolumes[0];
	}

	@Override
	public int getAskVolume2() {
		return askVolumes[1];
	}

	@Override
	public int getAskVolume3() {
		return askVolumes[2];
	}

	@Override
	public int getAskVolume4() {
		return askVolumes[3];
	}

	@Override
	public int getAskVolume5() {
		return askVolumes[4];
	}

	/*********************** Setter *************************/

	public BasicMarketData setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
		return this;
	}

	public BasicMarketData setVolume(int volume) {
		this.volume = volume;
		return this;
	}

	public BasicMarketData setTurnover(long turnover) {
		this.turnover = turnover;
		return this;
	}

	public BasicMarketData setBidPrice1(double price) {
		this.bidPrices[0] = price;
		return this;
	}

	public BasicMarketData setBidPrice2(double price) {
		this.bidPrices[1] = price;
		return this;
	}

	public BasicMarketData setBidPrice3(double price) {
		this.bidPrices[2] = price;
		return this;
	}

	public BasicMarketData setBidPrice4(double price) {
		this.bidPrices[3] = price;
		return this;
	}

	public BasicMarketData setBidPrice5(double price) {
		this.bidPrices[4] = price;
		return this;
	}

	public BasicMarketData setBidVolume1(int volume) {
		this.bidVolumes[0] = volume;
		return this;
	}

	public BasicMarketData setBidVolume2(int volume) {
		this.bidVolumes[1] = volume;
		return this;
	}

	public BasicMarketData setBidVolume3(int volume) {
		this.bidVolumes[2] = volume;
		return this;
	}

	public BasicMarketData setBidVolume4(int volume) {
		this.bidVolumes[3] = volume;
		return this;
	}

	public BasicMarketData setBidVolume5(int volume) {
		this.bidVolumes[4] = volume;
		return this;
	}

	public BasicMarketData setAskPrice1(double price) {
		this.askPrices[0] = price;
		return this;
	}

	public BasicMarketData setAskPrice2(double price) {
		this.askPrices[1] = price;
		return this;
	}

	public BasicMarketData setAskPrice3(double price) {
		this.askPrices[2] = price;
		return this;
	}

	public BasicMarketData setAskPrice4(double price) {
		this.askPrices[3] = price;
		return this;
	}

	public BasicMarketData setAskPrice5(double price) {
		this.askPrices[4] = price;
		return this;
	}

	public BasicMarketData setAskVolume1(int volume) {
		this.askVolumes[0] = volume;
		return this;
	}

	public BasicMarketData setAskVolume2(int volume) {
		this.askVolumes[1] = volume;
		return this;
	}

	public BasicMarketData setAskVolume3(int volume) {
		this.askVolumes[2] = volume;
		return this;
	}

	public BasicMarketData setAskVolume4(int volume) {
		this.askVolumes[3] = volume;
		return this;
	}

	public BasicMarketData setAskVolume5(int volume) {
		this.askVolumes[4] = volume;
		return this;
	}

	@Override
	public String toString() {
		return JsonWrapper.toJsonHasNulls(this);
	}

	@Override
	public String toJson() {
		return this.toString();
	}

}
