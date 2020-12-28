package io.horizon.indicator.impl;

import java.time.Duration;

import io.horizon.indicator.IndicatorEvent;
import io.horizon.indicator.impl.BollingerBandsIndicator.BollingerBandsEvent;
import io.horizon.indicator.impl.base.FixedPeriodIndicator;
import io.horizon.structure.market.data.impl.BasicMarketData;
import io.horizon.structure.market.instrument.Instrument;

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
