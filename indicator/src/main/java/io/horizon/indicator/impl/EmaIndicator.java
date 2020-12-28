package io.horizon.indicator.impl;

import java.time.Duration;

import io.horizon.indicator.IndicatorEvent;
import io.horizon.indicator.impl.EmaIndicator.EmaEvent;
import io.horizon.indicator.impl.base.FixedPeriodIndicator;
import io.horizon.structure.market.data.impl.BasicMarketData;
import io.horizon.structure.market.instrument.Instrument;

public final class EmaIndicator extends FixedPeriodIndicator<EmaPoint, EmaEvent, BasicMarketData> {

	public EmaIndicator(Instrument instrument, Duration duration, int cycle) {
		super(instrument, duration);
	}

	@Override
	protected void handleMarketData(BasicMarketData marketData) {
		// TODO Auto-generated method stub
	}
	
	public interface EmaEvent extends IndicatorEvent {

		@Override
		default String eventName() {
			return "EmaEvent";
		}

		void onCurrentEmaPointAvgPriceChanged(EmaPoint point);

		void onStartEmaPoint(EmaPoint point);

		void onEndEmaPoint(EmaPoint point);

	}


}
