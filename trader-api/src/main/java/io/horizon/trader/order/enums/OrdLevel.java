package io.horizon.trader.order.enums;

import io.horizon.trader.order.TdxProvider;
import io.horizon.trader.transport.enums.TdxOrdLevel;

public enum OrdLevel implements TdxProvider<TdxOrdLevel> {

    /**
     * 子订单
     */
    Child('C', TdxOrdLevel.CHILD),

    /**
     * 父订单
     */
    Parent('P', TdxOrdLevel.PARENT),

    /**
     * 策略订单
     */
    Strategy('S', TdxOrdLevel.STRATEGY),

    /**
     * 组订单
     */
    Group('G', TdxOrdLevel.GROUP);

    private final char code;
    private final TdxOrdLevel tdxValue;

    OrdLevel(char code, TdxOrdLevel tdxValue) {
        this.code = code;
        this.tdxValue = tdxValue;
    }

    public char getCode() {
        return code;
    }

    @Override
    public TdxOrdLevel getTdxValue() {
        return tdxValue;
    }
}