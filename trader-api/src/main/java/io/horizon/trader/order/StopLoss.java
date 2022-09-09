package io.horizon.trader.order;

import io.horizon.market.data.MarketData;
import io.horizon.trader.order.enums.TrdDirection;
import io.mercury.common.sequence.Serial;

public class StopLoss implements Serial<StopLoss> {

    /**
     * ordSysId
     */
    private final long ordSysId;

    /**
     * direction
     */
    private final TrdDirection direction;

    /**
     * 止损价
     */
    private double stopPrice;

    /**
     * @param order ChildOrder
     */
    public StopLoss(ChildOrder order) {
        this.ordSysId = order.getOrdSysId();
        this.direction = order.getDirection();
    }

    /**
     * @param order      ChildOrder
     * @param offsetTick int
     */
    public StopLoss(ChildOrder order, int offsetTick) {
        this.ordSysId = order.getOrdSysId();
        this.direction = order.getDirection();
        switch (direction) {
            case Long -> stopPrice = order.getPrice().getAvgTradePrice() - offsetTick;
            case Short -> stopPrice = order.getPrice().getAvgTradePrice() + offsetTick;
            default -> throw new IllegalStateException("direction error -> direction == [" + direction + "]");
        }
    }

    public StopLoss(long ordSysId, TrdDirection direction, double stopPrice) {
        this.ordSysId = ordSysId;
        this.direction = direction;
        this.stopPrice = stopPrice;
    }

    public StopLoss(long ordSysId, TrdDirection direction) {
        this.ordSysId = ordSysId;
        this.direction = direction;
        switch (direction) {
            case Long -> stopPrice = Long.MIN_VALUE;
            case Short -> stopPrice = Long.MAX_VALUE;
            default -> throw new IllegalStateException("direction error -> direction == [" + direction + "]");
        }
    }

    @Override
    public long getSerialId() {
        return ordSysId;
    }

    public final boolean isStopLoss(MarketData marketData) {
        return switch (direction) {
            case Long -> stopPrice < marketData.getAskPrice1();
            case Short -> stopPrice > marketData.getBidPrice1();
            default -> throw new IllegalStateException("direction error -> direction == [" + direction + "]");
        };
    }

    public long getOrdSysId() {
        return ordSysId;
    }

    public TrdDirection getDirection() {
        return direction;
    }

    public double getStopPrice() {
        return stopPrice;
    }

    public StopLoss setStopPrice(double stopPrice) {
        this.stopPrice = stopPrice;
        return this;
    }

}
