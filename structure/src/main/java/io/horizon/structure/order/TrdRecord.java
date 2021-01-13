package io.horizon.structure.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * tradePrice fix use {@link MarketConstant#PriceMultiplier}
 */
@RequiredArgsConstructor
@Getter
public class TrdRecord implements Comparable<TrdRecord> {

	private final int serial;
	private final long ordId;
	private final long timestamp;

	private final long trdPrice;
	private final int trdQty;

	@Override
	public int compareTo(TrdRecord o) {
		return this.ordId < o.ordId ? -1
				: this.ordId > o.ordId ? 1 
						: this.serial < o.serial ? -1 
								: this.serial > o.serial ? 1 
										: 0;
	}



}