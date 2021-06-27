package io.horizon.transaction.order;

import io.horizon.market.data.MarketData;
import io.horizon.transaction.order.OrdEnum.TrdDirection;
import io.mercury.common.sequence.Serial;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class StopLoss implements Serial {

	@Getter
	private final long ordSysId;

	@Getter
	private final TrdDirection direction;

	@Getter
	@Setter
	@Accessors(chain = true)
	private long stopPrice;

	public StopLoss(ChildOrder order) {
		this.ordSysId = order.getOrdSysId();
		this.direction = order.getDirection();
	}

	public StopLoss(ChildOrder order, long offsetTick) {
		this.ordSysId = order.getOrdSysId();
		this.direction = order.getDirection();
		switch (direction) {
		case Long:
			stopPrice = order.getPrice().getAvgTradePrice() - offsetTick;
			break;
		case Short:
			stopPrice = order.getPrice().getAvgTradePrice() + offsetTick;
			break;
		default:
			throw new IllegalStateException("direction error -> direction == " + direction);
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
			throw new IllegalStateException("direction error -> direction == " + direction);
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
			throw new IllegalStateException("direction error -> direction == " + direction);
		}
	}

}
