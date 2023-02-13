package io.horizon.trader.order.enums;

import io.horizon.trader.order.TdxProvider;
import io.horizon.trader.transport.enums.TdxTrdAction;

public enum TrdAction implements TdxProvider<TdxTrdAction> {

    /**
     * 无效
     */
    Invalid(TrdActionCode.INVALID, TdxTrdAction.INVALID),

    /**
     * 开仓
     */
    Open(TrdActionCode.OPEN, TdxTrdAction.OPEN),

    /**
     * 平仓
     */
    Close(TrdActionCode.CLOSE, TdxTrdAction.CLOSE),

    /**
     * 平今仓
     */
    CloseToday(TrdActionCode.CLOSE_TODAY, TdxTrdAction.CLOSE_TODAY),

    /**
     * 平昨仓
     */
    CloseYesterday(TrdActionCode.CLOSE_YESTERDAY, TdxTrdAction.CLOSE_YESTERDAY),

    ;

    private final char code;

    private final TdxTrdAction tdxValue;

    TrdAction(char code, TdxTrdAction tdxValue) {
        this.code = code;
        this.tdxValue = tdxValue;
    }

    public char getCode() {
        return code;
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

    public static TrdAction valueOf(TdxTrdAction action) {
        return switch (action) {
            case OPEN -> Open;
            case CLOSE -> Close;
            case CLOSE_TODAY -> CloseToday;
            case CLOSE_YESTERDAY -> CloseYesterday;
            default -> Invalid;
        };
    }

    @Override
    public TdxTrdAction getTdxValue() {
        return tdxValue;
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