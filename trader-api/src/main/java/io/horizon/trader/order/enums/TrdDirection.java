package io.horizon.trader.order.enums;

import io.horizon.trader.order.TdxProvider;
import io.horizon.trader.transport.enums.TdxTrdDirection;

public enum TrdDirection implements TdxProvider<TdxTrdDirection> {

    Invalid(TrdDirectionCode.INVALID, TdxTrdDirection.INVALID),

    Long(TrdDirectionCode.LONG, TdxTrdDirection.LONG),

    Short(TrdDirectionCode.SHORT, TdxTrdDirection.SHORT),

    ;

    private final char code;

    private final TdxTrdDirection tdxValue;

    TrdDirection(char code, TdxTrdDirection tdxValue) {
        this.code = code;
        this.tdxValue = tdxValue;
    }

    public char getCode() {
        return code;
    }

    /**
     * @param code int
     * @return TrdDirection
     */
    public static TrdDirection valueOf(int code) {
        return switch (code) {
            case TrdDirectionCode.LONG -> TrdDirection.Long;
            case TrdDirectionCode.SHORT -> TrdDirection.Short;
            default -> TrdDirection.Invalid;
        };
    }

    /**
     * @param tdxValue TdxTrdDirection
     * @return TrdDirection
     */
    public static TrdDirection valueOf(TdxTrdDirection tdxValue) {
        return switch (tdxValue) {
            case LONG -> TrdDirection.Long;
            case SHORT -> TrdDirection.Short;
            default -> TrdDirection.Invalid;
        };
    }

    @Override
    public TdxTrdDirection getTdxValue() {
        return tdxValue;
    }

    public interface TrdDirectionCode {
        // 无效
        char INVALID = 'I';
        // 多
        char LONG = 'L';
        // 空
        char SHORT = 'S';
    }

}