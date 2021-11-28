package io.horizon.market.indicator;

import java.time.temporal.TemporalAdjuster;

public interface TimeEvent<T extends TemporalAdjuster> {

	void onTime(T time);

}
