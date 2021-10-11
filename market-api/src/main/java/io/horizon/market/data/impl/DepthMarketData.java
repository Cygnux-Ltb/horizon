package io.horizon.market.data.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.horizon.market.instrument.Instrument;
import io.mercury.common.datetime.Timestamp;

public final class DepthMarketData extends BasicMarketData {

	public DepthMarketData(@Nonnull Instrument instrument, long epochMillis, int depth) {
		super(instrument, epochMillis, depth);
	}

	public DepthMarketData(@Nonnull Instrument instrument, long epochMillis, @Nullable Timestamp timestamp, int depth) {
		super(instrument, epochMillis, timestamp, depth);
	}

	public long getBidPrice(int level) {
		return bidPrices[level];
	}

	public int getBidVolume(int level) {
		return bidVolumes[level];
	}

	public long getAskPrice(int level) {
		return askPrices[level];
	}

	public int getAskVolume(int level) {
		return askVolumes[level];
	}

}
