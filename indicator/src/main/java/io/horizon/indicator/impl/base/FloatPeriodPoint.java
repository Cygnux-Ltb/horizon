package io.horizon.indicator.impl.base;

import java.time.ZonedDateTime;

import io.horizon.structure.market.data.MarketData;
import io.horizon.structure.vector.TimePointSerial;

public abstract class FloatPeriodPoint<M extends MarketData> extends BasePoint<TimePointSerial, M> {

	protected FloatPeriodPoint(int index, TimePointSerial serial) {
		super(index, serial);
	}

	public ZonedDateTime timePoint() {
		return serial.timePoint();
	}

}