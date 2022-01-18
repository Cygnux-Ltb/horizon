package io.horizon.market.indicator.impl;

import java.time.Duration;

import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.indicator.base.FixedPeriodPoint;
import io.horizon.market.instrument.Instrument;
import io.mercury.common.collections.window.LongRingWindow;
import io.mercury.common.sequence.TimeWindow;

public abstract class MaPoint extends FixedPeriodPoint<BasicMarketData> {

	protected LongRingWindow historyPriceWindow;

	protected double avgPrice;

	protected double lastPrice;

	protected MaPoint(int index, Instrument instrument, Duration duration, TimeWindow timePeriod,
			LongRingWindow historyPriceWindow) {
		super(index, timePeriod);
		this.historyPriceWindow = historyPriceWindow;
	}

	public LongRingWindow getHistoryPriceWindow() {
		return historyPriceWindow;
	}

	public double getAvgPrice() {
		return avgPrice;
	}

	public double getLastPrice() {
		return lastPrice;
	}

}
