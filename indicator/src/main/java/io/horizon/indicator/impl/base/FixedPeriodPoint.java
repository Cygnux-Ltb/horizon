package io.horizon.indicator.impl.base;

import java.time.ZonedDateTime;

import io.horizon.structure.market.data.MarketData;
import io.horizon.structure.vector.TimePeriodSerial;

public abstract class FixedPeriodPoint<M extends MarketData> extends BasePoint<TimePeriodSerial, M> {

	protected FixedPeriodPoint(int index, TimePeriodSerial serial) {
		super(index, serial);
	}

	public ZonedDateTime startTime() {
		return serial.startTime();
	}

	public ZonedDateTime endTime() {
		return serial.endTime();
	}

}