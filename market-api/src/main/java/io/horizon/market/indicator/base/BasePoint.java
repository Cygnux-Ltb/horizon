package io.horizon.market.indicator.base;

import io.horizon.market.data.MarketData;
import io.horizon.market.indicator.Point;
import io.mercury.common.annotation.AbstractFunction;
import io.mercury.common.util.Assertor;

/**
 * 
 * @author yellow013
 *
 * @param <S>
 * @param <M>
 */
public abstract class BasePoint<M extends MarketData> implements Point {

	protected final int index;

	protected M preMarketData;

	protected BasePoint(int index) {
		Assertor.greaterThan(index, -1, "index");
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
		return index < o.getIndex() ? -1 : index > o.getIndex() ? 1 : 0;
	}

}
