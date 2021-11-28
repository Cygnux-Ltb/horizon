package io.horizon.market.indicator.structure;

import io.mercury.common.serialization.JsonSerializable;
import io.mercury.serialization.json.JsonWrapper;

/**
 * 
 * @author yellow013
 */
public final class Bar implements JsonSerializable {

	// 开盘价
	private long open = 0L;

	// 最高价
	private long highest = Long.MIN_VALUE;

	// 最低价
	private long lowest = Long.MAX_VALUE;

	// 最新价
	private long last = 0L;

	/**
	 * 
	 * @param price
	 * @return
	 */
	public Bar onPrice(long price) {
		if (open == 0L)
			open = price;
		if (price > highest)
			highest = price;
		if (price < lowest)
			lowest = price;
		last = price;
		return this;
	}

	public long getOpen() {
		return open;
	}

	public long getHighest() {
		return highest;
	}

	public long getLowest() {
		return lowest;
	}

	public long getLast() {
		return last;
	}

	private static final String OpenField = "{\"open\":";
	private static final String HighestField = ",\"highest\":";
	private static final String LowestField = ",\"lowest\":";
	private static final String LastField = ",\"last\":";
	private static final String End = "}";

	@Override
	public String toString() {
		return new StringBuilder(80)
				// 开盘价
				.append(OpenField).append(open)
				// 最高价
				.append(HighestField).append(highest == Long.MIN_VALUE ? 0L : highest)
				// 最低价
				.append(LowestField).append(lowest == Long.MAX_VALUE ? 0L : lowest)
				// 最新价
				.append(LastField).append(last).append(End).toString();
	}

	@Override
	public String toJson() {
		return toString();
	}

	public static void main(String[] args) {

		Bar bar = new Bar().onPrice(100000).onPrice(100L).onPrice(10000000L);
		System.out.println(JsonWrapper.toJson(bar));
		System.out.println(bar);

	}

}
