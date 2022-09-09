package io.horizon.trader.order.enums;

import io.horizon.trader.transport.enums.DtoOrdStatus;
import io.mercury.common.log.Log4j2LoggerFactory;
import org.slf4j.Logger;

import java.io.Serial;

import static io.horizon.trader.order.enums.OrdStatus.OrdStatusCode.*;

public enum OrdStatus {

    /**
     * 无效
     */
    Invalid(INVALID, DtoOrdStatus.INVALID, true),

    /**
     * 新订单未确认
     */
    PendingNew(PENDING_NEW, DtoOrdStatus.PENDING_NEW, false),
    /**
     * 新订单
     */
    New(NEW, DtoOrdStatus.NEW, false),
    /**
     * 新订单已拒绝
     */
    NewRejected(NEW_REJECTED, DtoOrdStatus.NEW_REJECTED, true),

    /**
     * 部分成交
     */
    PartiallyFilled(PARTIALLY_FILLED, DtoOrdStatus.PARTIALLY_FILLED, false),
    /**
     * 全部成交
     */
    Filled(FILLED, DtoOrdStatus.FILLED, true),

    /**
     * 未确认撤单
     */
    PendingCancel(PENDING_CANCEL, DtoOrdStatus.PENDING_CANCEL, false),
    /**
     * 已撤单
     */
    Canceled(CANCELED, DtoOrdStatus.CANCELED, true),
    /**
     * 撤单已拒绝
     */
    CancelRejected(CANCEL_REJECTED, DtoOrdStatus.CANCEL_REJECTED, true),

    /**
     * 未确认修改订单
     */
    PendingReplace(PENDING_REPLACE, DtoOrdStatus.PENDING_REPLACE, false),

    /**
     * 已修改
     */
    Replaced(REPLACED, DtoOrdStatus.REPLACED, true),
    /**
     * 已暂停
     */
    Suspended(SUSPENDED, DtoOrdStatus.SUSPENDED, false),

    /**
     * 未提供
     */
    Unprovided(UNPROVIDED, DtoOrdStatus.UNPROVIDED, false),

    ;

    private final char code;

    private final DtoOrdStatus status;

    private final boolean finished;

    private final String str;

    private static final Logger log = Log4j2LoggerFactory.getLogger(OrdStatus.class);

    /**
     * @param code     代码
     * @param status   Avro状态
     * @param finished 是否为已结束状态
     */
    OrdStatus(char code, DtoOrdStatus status, boolean finished) {
        this.code = code;
        this.status = status;
        this.finished = finished;
        this.str = name() + "[" + code + "-" + (finished ? "Finished" : "Unfinished") + "]";
    }

    public char getCode() {
        return code;
    }

    public boolean isFinished() {
        return finished;
    }

    public DtoOrdStatus getTOrdStatus() {
        return status;
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
            case PENDING_NEW:
                return PendingNew;
            // 新订单
            case NEW:
                return New;
            // 新订单已拒绝
            case NEW_REJECTED:
                return NewRejected;
            // 部分成交
            case PARTIALLY_FILLED:
                return PartiallyFilled;
            // 全部成交
            case FILLED:
                return Filled;
            // 未确认撤单
            case PENDING_CANCEL:
                return PendingCancel;
            // 已撤单
            case CANCELED:
                return Canceled;
            // 撤单已拒绝
            case CANCEL_REJECTED:
                return CancelRejected;
            // 未确认修改订单
            case PENDING_REPLACE:
                return PendingReplace;
            // 已修改
            case REPLACED:
                return Replaced;
            // 已暂停
            case SUSPENDED:
                return Suspended;
            // 未提供
            case UNPROVIDED:
                return Unprovided;
            // 没有匹配项
            default:
                log.error("OrdStatus valueOf error, return OrdStatus -> [Invalid], input code==[{}]", code);
                return Invalid;
        }
    }

    /**
     * @param status TOrdStatus
     * @return OrdStatus
     */
    public static OrdStatus valueOf(DtoOrdStatus status) {
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
