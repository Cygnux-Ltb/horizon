package io.horizon.trader.order;

import io.mercury.common.sequence.Serial;

/**
 * tradePrice fix use {@link MarketConstant#PriceMultiplier}
 */

public class TradeRecord implements Serial<TradeRecord> {

	private final long ordSysId;

	private final int sequence;

	private final long epochMicros;

	private final double tradePrice;

	private final int tradeQty;

	public TradeRecord(long ordSysId, int sequence, long epochMicros, double tradePrice, int tradeQty) {
		this.ordSysId = ordSysId;
		this.sequence = sequence;
		this.epochMicros = epochMicros;
		this.tradePrice = tradePrice;
		this.tradeQty = tradeQty;
	}

	public long getOrdSysId() {
		return ordSysId;
	}

	public int getSequence() {
		return sequence;
	}

	public long getEpochMicros() {
		return epochMicros;
	}

	public double getTradePrice() {
		return tradePrice;
	}

	public int getTradeQty() {
		return tradeQty;
	}

	@Override
	public int compareTo(TradeRecord o) {
		return this.ordSysId < o.ordSysId ? -1
				: this.ordSysId > o.ordSysId ? 1 : this.sequence < o.sequence ? -1 : this.sequence > o.sequence ? 1 : 0;
	}

	@Override
	public long getSerialId() {
		return epochMicros;
	}

}