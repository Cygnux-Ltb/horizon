package io.horizon.trader.order.enums;

import io.horizon.trader.transport.enums.DtoOrdValid;

import static io.horizon.trader.order.enums.OrdValid.OrdValidCode.*;

public enum OrdValid {

    /**
     * Good Till Cancel, 将一直有效, 直到交易员取消这个报单, 或者该合约本身到期的报单.
     */
    GoodTillCancel(GTC, DtoOrdValid.GTC),

    /**
     * Good Till Date, 将一直有效, 直到指定日期或交易员取消这个报单, 或者该合约本身到期的报单.
     */
    GoodTillDate(GTD, DtoOrdValid.GTD),

    /**
     * Good For Day, 只在当日的交易时段有效, 一旦当前交易时段结束, 自动取消的报单.
     */
    GoodForDay(GFD, DtoOrdValid.GFD),

    ;

    private final char code;

    private final DtoOrdValid valid;

    OrdValid(char code, DtoOrdValid valid) {
        this.code = code;
        this.valid = valid;
    }

    public char getCode() {
        return code;
    }

    public DtoOrdValid getDtoOrdValid() {
        return valid;
    }

    /**
     * @return OrdValid.GoodTillCancel
     */
    public static OrdValid defaultValid() {
        return OrdValid.GoodTillCancel;
    }

    public interface OrdValidCode {
        // Good Till Cancel
        char GTC = 'C';
        // Good Till Date
        char GTD = 'D';
        // Good For Day
        char GFD = 'G';
    }

}
