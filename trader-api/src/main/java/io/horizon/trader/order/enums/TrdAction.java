package io.horizon.trader.order.enums;

import io.horizon.trader.transport.enums.DtoTrdAction;

import static io.horizon.trader.order.enums.TrdAction.TrdActionCode.*;

public enum TrdAction {

    /**
     * 无效
     */
    Invalid(INVALID, DtoTrdAction.INVALID),

    /**
     * 开仓
     */
    Open(OPEN, DtoTrdAction.OPEN),

    /**
     * 平仓
     */
    Close(CLOSE, DtoTrdAction.CLOSE),

    /**
     * 平今仓
     */
    CloseToday(CLOSE_TODAY, DtoTrdAction.CLOSE_TODAY),

    /**
     * 平昨仓
     */
    CloseYesterday(CLOSE_YESTERDAY, DtoTrdAction.CLOSE_YESTERDAY),

    ;

    private final char code;

    private final DtoTrdAction action;

    TrdAction(char code, DtoTrdAction action) {
        this.code = code;
        this.action = action;
    }

    public char getCode() {
        return code;
    }

    public DtoTrdAction getDtoTrdAction() {
        return action;
    }

    /**
     * @param code int
     * @return TrdAction
     */
    public static TrdAction valueOf(int code) {
        return switch (code) {
            case TrdActionCode.OPEN -> Open;
            case TrdActionCode.CLOSE -> Close;
            case TrdActionCode.CLOSE_TODAY -> CloseToday;
            case TrdActionCode.CLOSE_YESTERDAY -> CloseYesterday;
            default -> Invalid;
        };
    }

    public static TrdAction valueOf(DtoTrdAction action) {
        return switch (action) {
            case OPEN -> Open;
            case CLOSE -> Close;
            case CLOSE_TODAY -> CloseToday;
            case CLOSE_YESTERDAY -> CloseYesterday;
            default -> Invalid;
        };
    }

    public interface TrdActionCode {
        // 无效
        char INVALID = 'I';
        // 开仓
        char OPEN = 'O';
        // 平仓
        char CLOSE = 'C';
        // 平今仓
        char CLOSE_TODAY = 'T';
        // 平昨仓
        char CLOSE_YESTERDAY = 'Y';
    }

}