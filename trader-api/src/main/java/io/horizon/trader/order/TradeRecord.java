package io.horizon.trader.order;

import io.mercury.common.sequence.Serial;

public record TradeRecord(long ordSysId,
                          int sequence,
                          long epochMicros,
                          double tradePrice,
                          int tradeQty) implements Serial<TradeRecord> {

    @Override
    public int compareTo(TradeRecord o) {
        return this.ordSysId < o.ordSysId ? -1
                : this.ordSysId > o.ordSysId ? 1 : Integer.compare(this.sequence, o.sequence);
    }

    @Override
    public long getSerialId() {
        return epochMicros;
    }

}