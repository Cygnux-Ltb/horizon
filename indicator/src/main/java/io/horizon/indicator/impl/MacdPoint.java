package io.horizon.indicator.impl;

import io.horizon.definition.market.data.impl.BasicMarketData;
import io.horizon.definition.market.vector.TimePeriodSerial;
import io.horizon.indicator.impl.base.FixedPeriodPoint;

public final class MacdPoint extends FixedPeriodPoint<BasicMarketData> {

	private MacdPoint(int index, TimePeriodSerial timePeriod) {
		super(index, timePeriod);
	}

	@Override
	protected void handleMarketData0(BasicMarketData marketData) {
		// TODO Auto-generated method stub

	}

}
