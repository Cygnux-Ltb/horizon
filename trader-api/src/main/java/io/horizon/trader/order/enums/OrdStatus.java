package io.horizon.trader.order.enums;

import io.horizon.trader.order.TdxProvider;
import io.horizon.trader.transport.enums.TdxOrdStatus;
import io.mercury.common.log4j2.Log4j2LoggerFactory;
import org.slf4j.Logger;

import java.io.Serial;

public enum OrdStatus implements TdxProvider<TdxOrdStatus> {

    /**
     * 无效
     */
    Invalid(OrdStatusCode.INVALID, TdxOrdStatus.INVALID, true),

    /**
     * 新订单未确认
     */
    PendingNew(OrdStatusCode.PENDING_NEW, TdxOrdStatus.PENDING_NEW, false),
    /**
     * 新订单
     */
    New(OrdStatusCode.NEW, TdxOrdStatus.NEW, false),
    /**
     * 新订单已拒绝
     */
    NewRejected(OrdStatusCode.NEW_REJECTED, TdxOrdStatus.NEW_REJECTED, true),

    /**
     * 部分成交
     */
    PartiallyFilled(OrdStatusCode.PARTIALLY_FILLED, TdxOrdStatus.PARTIALLY_FILLED, false),
    /**
     * 全部成交
     */
    Filled(OrdStatusCode.FILLED, TdxOrdStatus.FILLED, true),

    /**
     * 未确认撤单
     */
    PendingCancel(OrdStatusCode.PENDING_CANCEL, TdxOrdStatus.PENDING_CANCEL, false),
    /**
     * 已撤单
     */
    Canceled(OrdStatusCode.CANCELED, TdxOrdStatus.CANCELED, true),
    /**
     * 撤单已拒绝
     */
    CancelRejected(OrdStatusCode.CANCEL_REJECTED, TdxOrdStatus.CANCEL_REJECTED, true),

    /**
     * 未确认修改订单
     */
    PendingReplace(OrdStatusCode.PENDING_REPLACE, TdxOrdStatus.PENDING_REPLACE, false),

    /**
     * 已修改
     */
    Replaced(OrdStatusCode.REPLACED, TdxOrdStatus.REPLACED, true),
    /**
     * 已暂停
     */
    Suspended(OrdStatusCode.SUSPENDED, TdxOrdStatus.SUSPENDED, false),

    /**
     * 未提供
     */
    Unprovided(OrdStatusCode.UNPROVIDED, TdxOrdStatus.UNPROVIDED, false),

    ;

    private final char code;

    private final TdxOrdStatus tdxValue;

    private final boolean finished;

    private final String str;

    private static final Logger log = Log4j2LoggerFactory.getLogger(OrdStatus.class);

    /**
     * @param code     代码
     * @param tdxValue Tdx状态
     * @param finished 是否为已结束状态
     */
    OrdStatus(char code, TdxOrdStatus tdxValue, boolean finished) {
        this.code = code;
        this.tdxValue = tdxValue;
        this.finished = finished;
        this.str = name() + "[" + code + "-" + (finished ? "Finished" : "Unfinished") + "]";
    }

    public char getCode() {
        return code;
    }

    public boolean isFinished() {
        return finished;
    }


    @Override
    public String toString() {
        return str;
    }

    /**
     * @param code int
     * @return OrdStatus
     */
    public static OrdStatus valueOf(int code) {
        switch (code) {
            // 未确认新订单
            case OrdStatusCode.PENDING_NEW -> {
                return PendingNew;
            }
            // 新订单
            case OrdStatusCode.NEW -> {
                return New;
            }
            // 新订单已拒绝
            case OrdStatusCode.NEW_REJECTED -> {
                return NewRejected;
            }
            // 部分成交
            case OrdStatusCode.PARTIALLY_FILLED -> {
                return PartiallyFilled;
            }
            // 全部成交
            case OrdStatusCode.FILLED -> {
                return Filled;
            }
            // 未确认撤单
            case OrdStatusCode.PENDING_CANCEL -> {
                return PendingCancel;
            }
            // 已撤单
            case OrdStatusCode.CANCELED -> {
                return Canceled;
            }
            // 撤单已拒绝
            case OrdStatusCode.CANCEL_REJECTED -> {
                return CancelRejected;
            }
            // 未确认修改订单
            case OrdStatusCode.PENDING_REPLACE -> {
                return PendingReplace;
            }
            // 已修改
            case OrdStatusCode.REPLACED -> {
                return Replaced;
            }
            // 已暂停
            case OrdStatusCode.SUSPENDED -> {
                return Suspended;
            }
            // 未提供
            case OrdStatusCode.UNPROVIDED -> {
                return Unprovided;
            }
            // 没有匹配项
            default -> {
                log.error("OrdStatus valueOf error, return OrdStatus -> [Invalid], input code==[{}]", code);
                return Invalid;
            }
        }
    }

    /**
     * @param status TOrdStatus
     * @return OrdStatus
     */
    public static OrdStatus valueOf(TdxOrdStatus status) {
        return switch (status) {
            // 未确认新订单
            case PENDING_NEW -> PendingNew;
            // 新订单
            case NEW -> New;
            // 新订单已拒绝
            case NEW_REJECTED -> NewRejected;
            // 部分成交
            case PARTIALLY_FILLED -> PartiallyFilled;
            // 全部成交
            case FILLED -> Filled;
            // 未确认撤单
            case PENDING_CANCEL -> PendingCancel;
            // 已撤单
            case CANCELED -> Canceled;
            // 撤单已拒绝
            case CANCEL_REJECTED -> CancelRejected;
            // 未确认修改订单
            case PENDING_REPLACE -> PendingReplace;
            // 已修改
            case REPLACED -> Replaced;
            // 已暂停
            case SUSPENDED -> Suspended;
            // 未提供
            case UNPROVIDED -> Unprovided;
            // 没有匹配项
            default ->
                // log.error("OrdStatus valueOf error, return OrdStatus -> [Invalid], input TOrdStatus==[{}]", status);
                    Invalid;
        };
    }

    @Override
    public TdxOrdStatus getTdxValue() {
        return tdxValue;
    }

    public interface OrdStatusCode {
        // 无效
        char INVALID = 'I';

        // 新订单未确认
        char PENDING_NEW = 'P';
        // 新订单
        char NEW = 'N';
        // 新订单已拒绝
        char NEW_REJECTED = 'R';

        // 部分成交
        char PARTIALLY_FILLED = 'A';
        // 全部成交
        char FILLED = 'F';

        // 未确认撤单
        char PENDING_CANCEL = 'C';
        // 已撤单
        char CANCELED = 'X';
        // 撤单已拒绝
        char CANCEL_REJECTED = 'Y';

        // 未确认修改订单
        char PENDING_REPLACE = 'Q';
        // 已修改
        char REPLACED = 'E';

        // 已暂停
        char SUSPENDED = 'S';
        // 已停止
        // char STOPPED = '7';
        // 已过期
        // char EXPIRED = 'C';
        // 未提供
        char UNPROVIDED = 'U';

    }

    /**
     * OrdStatusException
     *
     * @author yellow013
     */
    public static class OrdStatusException extends RuntimeException {

        @Serial
        private static final long serialVersionUID = -4772495541311633988L;

        public OrdStatusException(String message) {
            super(message);
        }

    }

}
