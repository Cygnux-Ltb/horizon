package io.horizon.market.indicator.base;

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

	// 最终价

	private long last = 0L;

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
		StringBuilder builder = new StringBuilder(80);
		return builder.append(OpenField).append(open).append(HighestField)
				.append(highest == Long.MIN_VALUE ? 0L : highest).append(LowestField)
				.append(lowest == Long.MAX_VALUE ? 0L : lowest).append(LastField).append(last).append(End).toString();
	}

	public static void main(String[] args) {

		Bar bar = new Bar().onPrice(100000).onPrice(100L).onPrice(10000000L);
		System.out.println(JsonWrapper.toJson(bar));
		System.out.println(bar);

	}

	@Override
	public String toJson() {
		return toString();
	}

}
