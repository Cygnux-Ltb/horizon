package io.horizon.market.indicator.impl;

import java.time.Duration;

import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.indicator.IndicatorEvent;
import io.horizon.market.indicator.base.FixedPeriodIndicator;
import io.horizon.market.indicator.impl.EMA.EmaEvent;
import io.horizon.market.indicator.impl.EMA.EmaPoint;
import io.horizon.market.instrument.Instrument;
import io.mercury.common.collections.list.LongSlidingWindow;
import io.mercury.common.sequence.TimeWindow;

public final class EMA extends FixedPeriodIndicator<EmaPoint, EmaEvent, BasicMarketData> {

	public EMA(Instrument instrument, Duration duration, int cycle) {
		super(instrument, duration);
	}

	@Override
	protected void handleMarketData(BasicMarketData marketData) {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 * @author yellow013
	 *
	 */
	public interface EmaEvent extends IndicatorEvent {

		@Override
		default String getEventName() {
			return "EmaEvent";
		}

		void onCurrentEmaPointAvgPriceChanged(EmaPoint point);

		void onStartEmaPoint(EmaPoint point);

		void onEndEmaPoint(EmaPoint point);

	}

	/**
	 * 
	 * @author yellow013
	 *
	 */
	public final class EmaPoint extends MaPoint {

		protected EmaPoint(int index, Instrument instrument, Duration duration, TimeWindow timePeriod,
				LongSlidingWindow historyPriceWindow) {
			super(index, instrument, duration, timePeriod, historyPriceWindow);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void handleMarketData0(BasicMarketData preMarketData) {
			// TODO Auto-generated method stub

		}

	}

}
