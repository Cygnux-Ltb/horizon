package io.horizon.indicator.impl;

import java.time.Duration;

import io.horizon.definition.market.data.impl.BasicMarketData;
import io.horizon.definition.market.instrument.Instrument;
import io.horizon.indicator.IndicatorEvent;
import io.horizon.indicator.impl.BollingerBandsIndicator.BollingerBandsEvent;
import io.horizon.indicator.impl.base.FixedPeriodIndicator;

public final class BollingerBandsIndicator extends FixedPeriodIndicator<BollingerBandsPoint, BollingerBandsEvent, BasicMarketData> {

	public BollingerBandsIndicator(Instrument instrument, Duration duration, int cycle) {
		super(instrument, duration, cycle);
	}

	@Override
	protected void handleMarketData(BasicMarketData marketData) {

	}
	
	public static interface BollingerBandsEvent extends IndicatorEvent {

		@Override
		default String eventName() {
			return "BollingerBandsEvent";
		}

	}


}
