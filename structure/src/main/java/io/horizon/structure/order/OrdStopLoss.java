package io.horizon.structure.order;

import io.horizon.structure.order.enums.TrdDirection;

public final class OrdStopLoss implements Comparable<OrdStopLoss> {

	private long ordId;
	private long stopLossPrice;

	public OrdStopLoss(long ordId, TrdDirection direction) {
		super();
		this.ordId = ordId;
		switch (direction) {
		case Long:
			stopLossPrice = Long.MAX_VALUE;
			break;
		case Short:
			stopLossPrice = Long.MIN_VALUE;
			break;
		default:
			throw new RuntimeException("direction error");
		}
	}

	public long ordId() {
		return ordId;
	}

	public long stopLossPrice() {
		return stopLossPrice;
	}

	public OrdStopLoss stopLossPrice(long stopLossPrice) {
		this.stopLossPrice = stopLossPrice;
		return this;
	}

	@Override
	public int compareTo(OrdStopLoss o) {
		return ordId < o.ordId ? -1 : ordId > o.ordId ? 1 : 0;
	}

}
