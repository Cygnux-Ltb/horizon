package io.horizon.trader.order;

/**
 * tradePrice fix use {@link MarketConstant#PriceMultiplier}
 */

public class TrdRecord implements Comparable<TrdRecord> {

	private final long ordSysId;

	private final int sequence;

	private final long timestamp;

	private final long trdPrice;

	private final int trdQty;

	public TrdRecord(long ordSysId, int sequence, long timestamp, long trdPrice, int trdQty) {
		this.ordSysId = ordSysId;
		this.sequence = sequence;
		this.timestamp = timestamp;
		this.trdPrice = trdPrice;
		this.trdQty = trdQty;
	}

	public long getOrdSysId() {
		return ordSysId;
	}

	public int getSequence() {
		return sequence;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public long getTrdPrice() {
		return trdPrice;
	}

	public int getTrdQty() {
		return trdQty;
	}

	@Override
	public int compareTo(TrdRecord o) {
		return this.ordSysId < o.ordSysId ? -1
				: this.ordSysId > o.ordSysId ? 1 : this.sequence < o.sequence ? -1 : this.sequence > o.sequence ? 1 : 0;
	}

}