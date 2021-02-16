package io.horizon.structure.order;

import io.horizon.structure.order.enums.TrdDirection;
import io.mercury.common.sequence.Serial;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 
 * @author yellow013
 *
 */
public final class OrderStopLoss implements Serial<OrderStopLoss> {

	@Getter
	private final long ordSysId;

	@Getter
	@Setter
	@Accessors(chain = true)
	private long stopLossPrice;

	public OrderStopLoss(long ordSysId, TrdDirection direction) {
		super();
		this.ordSysId = ordSysId;
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

	@Override
	public int compareTo(OrderStopLoss o) {
		return ordSysId < o.ordSysId ? -1 : ordSysId > o.ordSysId ? 1 : 0;
	}

	@Override
	public long getSerialId() {
		return ordSysId;
	}

}
