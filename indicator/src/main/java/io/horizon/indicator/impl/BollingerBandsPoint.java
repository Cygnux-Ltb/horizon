package io.horizon.indicator.impl;

import io.horizon.definition.market.data.impl.BasicMarketData;
import io.horizon.definition.market.vector.TimePeriodSerial;
import io.horizon.indicator.impl.base.FixedPeriodPoint;

public final class BollingerBandsPoint extends FixedPeriodPoint<BasicMarketData> {

	private BollingerBandsPoint(int index, TimePeriodSerial timePeriod) {
		super(index, timePeriod);
	}

	@Override
	protected void handleMarketData0(BasicMarketData marketData) {

	}

}
