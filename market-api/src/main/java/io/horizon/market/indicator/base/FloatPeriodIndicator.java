package io.horizon.market.indicator.base;

import io.horizon.market.api.MarketData;
import io.horizon.market.indicator.IndicatorEvent;
import io.horizon.market.instrument.Instrument;

public abstract class FloatPeriodIndicator<P extends FloatPeriodPoint<M>, E extends IndicatorEvent, M extends MarketData>
		extends BaseIndicator<P, E, M> {

	protected FloatPeriodIndicator(Instrument instrument) {
		super(instrument);
	}

}
