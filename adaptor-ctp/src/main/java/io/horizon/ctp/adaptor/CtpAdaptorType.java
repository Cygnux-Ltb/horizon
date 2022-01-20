package io.horizon.ctp.adaptor;

import java.time.LocalTime;

import io.horizon.trader.adaptor.AdaptorType;

public class CtpAdaptorType implements AdaptorType {
	
	public static final AdaptorType INSTANCE = new CtpAdaptorType();

	@Override
	public boolean isRunningAllTime() {
		return false;
	}

	@Override
	public LocalTime[] getStartTimes() {
		return new LocalTime[] { LocalTime.of(8, 55), LocalTime.of(22, 55) };
	}

	@Override
	public LocalTime[] getEndTimes() {
		return new LocalTime[] { LocalTime.of(15, 10), LocalTime.of(2, 40) };
	}

}
