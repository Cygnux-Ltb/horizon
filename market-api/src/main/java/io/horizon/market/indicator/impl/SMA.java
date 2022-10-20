package io.horizon.market.indicator.impl;

import java.time.Duration;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.indicator.IndicatorEvent;
import io.horizon.market.indicator.base.FixedPeriodIndicator;
import io.horizon.market.indicator.impl.SMA.SmaEvent;
import io.horizon.market.instrument.Instrument;
import io.horizon.market.pool.TimeWindowPool;
import io.mercury.common.collections.window.LongRingWindow;
import io.mercury.common.sequence.TimeWindow;

public final class SMA extends FixedPeriodIndicator<SmaPoint, SmaEvent, BasicMarketData> {

	private final LongRingWindow historyPriceWindow;

	public SMA(Instrument instrument, Duration duration, int cycle) {
		super(instrument, duration, cycle);
		this.historyPriceWindow = new LongRingWindow(cycle);
		ImmutableSortedSet<TimeWindow> timePeriodSet = TimeWindowPool.Singleton.getTimePeriodSet(instrument,
				duration);
		int i = -1;
		for (TimeWindow timePeriod : timePeriodSet)
			pointSet.add(SmaPoint.with(++i, instrument, duration, timePeriod, cycle, historyPriceWindow));
		currentPoint = pointSet.getFirst();

	}

	public static SMA with(Instrument instrument, Duration duration, int cycle) {
		return new SMA(instrument, duration, cycle);
	}

	@Override
	protected void handleMarketData(BasicMarketData marketData) {

	}

	public interface SmaEvent extends IndicatorEvent {

		default String eventName() {
			return "SmaEvent";
		}

		void onCurrentPointAvgPriceChanged(SmaPoint point);

		void onStartSmaPoint(SmaPoint point);

		void onEndSmaPoint(SmaPoint point);

	}

}
