package io.horizon.trader.order;

import io.horizon.market.data.MarketData;
import io.horizon.trader.order.enums.TrdDirection;
import io.mercury.common.sequence.Serial;

public class StopLoss implements Serial<StopLoss> {

	/**
	 * ordSysId
	 */
	private final long ordSysId;

	/**
	 * direction
	 */
	private final TrdDirection direction;

	/**
	 * 
	 */
	private long stopPrice;

	/**
	 * @param order
	 */
	public StopLoss(ChildOrder order) {
		this.ordSysId = order.getOrdSysId();
		this.direction = order.getDirection();
	}

	/**
	 * @param order
	 * @param stopLossTick
	 */
	public StopLoss(ChildOrder order, long stopLossTick) {
		this.ordSysId = order.getOrdSysId();
		this.direction = order.getDirection();
		switch (direction) {
		case Long:
			stopPrice = order.getPrice().getAvgTradePrice() - stopLossTick;
			break;
		case Short:
			stopPrice = order.getPrice().getAvgTradePrice() + stopLossTick;
			break;
		default:
			throw new IllegalStateException("direction error -> direction == [" + direction + "]");
		}
	}

	public StopLoss(long ordSysId, TrdDirection direction, long stopPrice) {
		this.ordSysId = ordSysId;
		this.direction = direction;
		this.stopPrice = stopPrice;
	}

	public StopLoss(long ordSysId, TrdDirection direction) {
		this.ordSysId = ordSysId;
		this.direction = direction;
		switch (direction) {
		case Long:
			stopPrice = Long.MIN_VALUE;
			break;
		case Short:
			stopPrice = Long.MAX_VALUE;
			break;
		default:
			throw new IllegalStateException("direction error -> direction == [" + direction + "]");
		}
	}

	@Override
	public long getSerialId() {
		return ordSysId;
	}

	public final boolean isStopLoss(MarketData marketData) {
		switch (direction) {
		case Long:
			return stopPrice < marketData.getAskPrice1();
		case Short:
			return stopPrice > marketData.getBidPrice1();
		default:
			throw new IllegalStateException("direction error -> direction == [" + direction + "]");
		}
	}

	public long getOrdSysId() {
		return ordSysId;
	}

	public TrdDirection getDirection() {
		return direction;
	}

	public long getStopPrice() {
		return stopPrice;
	}

	public StopLoss setStopPrice(long stopPrice) {
		this.stopPrice = stopPrice;
		return this;
	}

}
