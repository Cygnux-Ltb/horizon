package io.horizon.market.indicator.base;

import io.horizon.market.data.MarketData;
import io.horizon.market.indicator.IndicatorEvent;
import io.horizon.market.instrument.Instrument;

import java.time.Duration;

public abstract class FixedPeriodIndicator<P extends FixedPeriodPoint<M>,
        E extends IndicatorEvent, M extends MarketData> extends BaseIndicator<P, E, M> {

    protected final Duration duration;

    protected final int cycle;

    /**
     * @param instrument Instrument
     * @param duration   Duration
     */
    public FixedPeriodIndicator(Instrument instrument, Duration duration) {
        this(instrument, duration, 0);
    }

    /**
     * @param instrument Instrument
     * @param duration   Duration
     * @param cycle      int
     */
    public FixedPeriodIndicator(Instrument instrument, Duration duration, int cycle) {
        super(instrument);
        this.duration = duration;
        this.cycle = cycle;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getCycle() {
        return cycle;
    }

}
