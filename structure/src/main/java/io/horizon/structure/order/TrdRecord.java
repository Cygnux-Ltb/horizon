package io.horizon.definition.order;

/**
 * tradePrice fix use {@link MarketConstant#PriceMultiplier}
 */
public class TrdRecord implements Comparable<TrdRecord> {

	private int serial;
	private long ordId;
	private long epochTime;

	private long trdPrice;
	private int trdQty;

	public TrdRecord(int serial, long ordId, long epochTime, long trdPrice, int trdQty) {
		this.serial = serial;
		this.ordId = ordId;
		this.epochTime = epochTime;
		this.trdPrice = trdPrice;
		this.trdQty = trdQty;
	}

	public long ordId() {
		return ordId;
	}

	public int serial() {
		return serial;
	}

	public long epochTime() {
		return epochTime;
	}

	public long trdPrice() {
		return trdPrice;
	}

	public int trdQty() {
		return trdQty;
	}

	@Override
	public int compareTo(TrdRecord o) {
		return this.ordId < o.ordId ? -1
				: this.ordId > o.ordId ? 1 
						: this.serial < o.serial ? -1 
								: this.serial > o.serial ? 1 
										: 0;
	}

}