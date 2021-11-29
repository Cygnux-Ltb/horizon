package io.horizon.market.indicator.impl;

import java.time.Duration;

import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.indicator.IndicatorEvent;
import io.horizon.market.indicator.base.FixedPeriodIndicator;
import io.horizon.market.indicator.base.FixedPeriodPoint;
import io.horizon.market.indicator.impl.MACD.MacdEvent;
import io.horizon.market.indicator.impl.MACD.MacdPoint;
import io.horizon.market.instrument.Instrument;
import io.mercury.common.sequence.TimeWindow;

public final class MACD extends FixedPeriodIndicator<MacdPoint, MacdEvent, BasicMarketData> {

	public MACD(Instrument instrument, Duration duration) {
		super(instrument, duration);
	}

	@Override
	protected void handleMarketData(BasicMarketData marketData) {
		// TODO Auto-generated method stub
	}

	public static interface MacdEvent extends IndicatorEvent {

		@Override
		default String getEventName() {
			return "MacdEvent";
		}

	}

	public final class MacdPoint extends FixedPeriodPoint<BasicMarketData> {

		private MacdPoint(int index, TimeWindow window) {
			super(index, window);
		}

		@Override
		protected void handleMarketData0(BasicMarketData marketData) {

		}

	}

}
