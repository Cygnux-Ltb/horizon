package io.horizon.trader.order.enums;

import io.horizon.trader.transport.enums.DtoTrdDirection;

public enum TrdDirection {

    Invalid(TrdDirectionCode.INVALID, DtoTrdDirection.INVALID),

    Long(TrdDirectionCode.LONG, DtoTrdDirection.LONG),

    Short(TrdDirectionCode.SHORT, DtoTrdDirection.SHORT),

    ;

    private final char code;

    private final DtoTrdDirection direction;

    TrdDirection(char code, DtoTrdDirection direction) {
        this.code = code;
        this.direction = direction;
    }

    public char getCode() {
        return code;
    }

    public DtoTrdDirection getDtoTrdDirection() {
        return direction;
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
     * @param direction TTrdDirection
     * @return TrdDirection
     */
    public static TrdDirection valueOf(DtoTrdDirection direction) {
        return switch (direction) {
            case LONG -> TrdDirection.Long;
            case SHORT -> TrdDirection.Short;
            default -> TrdDirection.Invalid;
        };
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