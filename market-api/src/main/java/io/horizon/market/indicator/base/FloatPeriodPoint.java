package io.horizon.market.indicator.base;

import io.horizon.market.data.MarketData;
import io.mercury.common.sequence.TimePoint;

public abstract class FloatPeriodPoint<M extends MarketData> extends BasePoint<M> {

	private final TimePoint point;

	protected FloatPeriodPoint(int index, TimePoint point) {
		super(index);
		this.point = point;
	}

	public TimePoint getPoint() {
		return point;
	}

}