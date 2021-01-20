package io.horizon.structure.order;

import io.horizon.structure.order.enums.TrdDirection;
import io.mercury.common.sequence.Serial;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public final class OrdStopLoss implements Serial<OrdStopLoss> {

	@Getter
	private long ordId;

	@Getter
	@Setter
	@Accessors(chain = true)
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

	@Override
	public int compareTo(OrdStopLoss o) {
		return ordId < o.ordId ? -1 : ordId > o.ordId ? 1 : 0;
	}

	@Override
	public long serialId() {
		return ordId;
	}

}
