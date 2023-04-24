package io.horizon.trader.order;

import io.mercury.common.sequence.Serial;

public record TradeRecord(//订单编号
                          long ordSysId,
                          //序列
                          int sequence,
                          //Epoch毫秒数
                          long epochMicros,
                          //成交价格
                          double tradePrice,
                          //成交数量
                          int tradeQty) implements Serial<TradeRecord> {

    @Override
    public int compareTo(TradeRecord o) {
        return this.ordSysId < o.ordSysId ? -1
                : this.ordSysId > o.ordSysId ? 1 : Integer.compare(this.sequence, o.sequence);
    }

    @Override
    public long serialId() {
        return epochMicros;
    }

}