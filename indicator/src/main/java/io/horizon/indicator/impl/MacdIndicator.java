package io.horizon.indicator.impl;

import java.time.Duration;

import io.horizon.definition.market.data.impl.BasicMarketData;
import io.horizon.definition.market.instrument.Instrument;
import io.horizon.indicator.IndicatorEvent;
import io.horizon.indicator.impl.MacdIndicator.MacdEvent;
import io.horizon.indicator.impl.base.FixedPeriodIndicator;

public final class MacdIndicator extends FixedPeriodIndicator<MacdPoint, MacdEvent, BasicMarketData> {

	public MacdIndicator(Instrument instrument, Duration duration) {
		super(instrument, duration);
	}

	@Override
	protected void handleMarketData(BasicMarketData marketData) {
		// TODO Auto-generated method stub
	}

	public static interface MacdEvent extends IndicatorEvent {

		@Override
		default String eventName() {
			return "MacdEvent";
		}

	}

}
