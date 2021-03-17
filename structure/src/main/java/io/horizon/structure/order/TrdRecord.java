package io.horizon.structure.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * tradePrice fix use {@link MarketConstant#PriceMultiplier}
 */
@RequiredArgsConstructor
public class TrdRecord implements Comparable<TrdRecord> {

	@Getter
	private final int sequence;

	@Getter
	private final long ordSysId;

	@Getter
	private final long timestamp;

	@Getter
	private final long trdPrice;

	@Getter
	private final int trdQty;

	@Override
	public int compareTo(TrdRecord o) {
		return this.ordSysId < o.ordSysId ? -1
				: this.ordSysId > o.ordSysId ? 1 : this.sequence < o.sequence ? -1 : this.sequence > o.sequence ? 1 : 0;
	}

}