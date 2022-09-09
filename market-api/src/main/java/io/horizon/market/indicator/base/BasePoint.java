package io.horizon.market.indicator.base;

import io.horizon.market.data.MarketData;
import io.horizon.market.indicator.Point;
import io.mercury.common.annotation.AbstractFunction;
import io.mercury.common.lang.Asserter;

/**
 * @param <M>
 * @author yellow013
 */
public abstract class BasePoint<M extends MarketData> implements Point {

    protected final int index;

    protected M preMarketData;

    protected BasePoint(int index) {
        Asserter.greaterThan(index, -1, "index");
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public M getPreMarketData() {
        return preMarketData;
    }

    public void handleMarketData(M marketData) {
        handleMarketData0(marketData);
        updatePreMarketData(marketData);
    }

    public void updatePreMarketData(M marketData) {
        this.preMarketData = marketData;
    }

    @AbstractFunction
    protected abstract void handleMarketData0(M marketData);

    @Override
    public int compareTo(Point o) {
        return Integer.compare(index, o.getIndex());
    }

}
