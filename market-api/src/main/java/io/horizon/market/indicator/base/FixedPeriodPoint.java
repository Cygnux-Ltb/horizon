package io.horizon.market.indicator.base;

import io.horizon.market.data.MarketData;
import io.mercury.common.sequence.TimeWindow;

/**
 * @param <M>
 * @author yellow013
 */
public abstract class FixedPeriodPoint<M extends MarketData> extends BasePoint<M> {

    protected final TimeWindow window;

    protected FixedPeriodPoint(int index, TimeWindow window) {
        super(index);
        this.window = window;
    }

    public TimeWindow getWindow() {
        return window;
    }

    @Override
    public long serialId() {
        return window.serialId();
    }

}
