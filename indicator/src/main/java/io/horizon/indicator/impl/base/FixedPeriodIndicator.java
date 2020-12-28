package io.horizon.indicator.impl.base;

import java.time.Duration;

import io.horizon.indicator.IndicatorEvent;
import io.horizon.structure.market.data.MarketData;
import io.horizon.structure.market.instrument.Instrument;

public abstract class FixedPeriodIndicator<P extends FixedPeriodPoint<M>, E extends IndicatorEvent, M extends MarketData>
		extends BaseIndicator<P, E, M> {

	protected Duration duration;
	protected int cycle;

	/**
	 * 
	 * @param instrument
	 * @param period
	 */
	public FixedPeriodIndicator(Instrument instrument, Duration duration) {
		this(instrument, duration, 1);
	}

	/**
	 * 
	 * @param instrument
	 * @param period
	 * @param cycle
	 */
	public FixedPeriodIndicator(Instrument instrument, Duration duration, int cycle) {
		super(instrument);
		this.duration = duration;
		this.cycle = cycle;

	}

}
