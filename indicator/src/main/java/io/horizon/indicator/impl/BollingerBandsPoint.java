package io.horizon.indicator.impl;

import io.horizon.indicator.impl.base.FixedPeriodPoint;
import io.horizon.structure.market.data.impl.BasicMarketData;
import io.horizon.structure.vector.TimePeriodSerial;

public final class BollingerBandsPoint extends FixedPeriodPoint<BasicMarketData> {

	private BollingerBandsPoint(int index, TimePeriodSerial timePeriod) {
		super(index, timePeriod);
	}

	@Override
	protected void handleMarketData0(BasicMarketData marketData) {

	}

}
