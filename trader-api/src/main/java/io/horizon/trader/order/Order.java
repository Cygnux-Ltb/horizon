package io.horizon.trader.order;

import io.horizon.market.instrument.Instrument;
import io.horizon.trader.order.attr.OrdPrice;
import io.horizon.trader.order.attr.OrdQty;
import io.horizon.trader.order.attr.OrdRemark;
import io.horizon.trader.order.attr.OrdTimestamp;
import io.horizon.trader.order.enums.OrdStatus;
import io.horizon.trader.order.enums.OrdType;
import io.horizon.trader.order.enums.OrdValid;
import io.horizon.trader.order.enums.TrdDirection;
import io.mercury.common.sequence.Serial;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * @author yellow013
 */
public interface Order extends Serial<Order>, Serializable {

    /**
     * ordSysId构成, 使用雪花算法实现<br>
     * <br>
     * 策略Id | 时间戳Second | 自增量Number<br>
     * strategyId | epochSecond| increment<br>
     *
     * @return long
     */
    long getOrdSysId();

    /**
     * strategyId
     *
     * @return int
     */
    int getStrategyId();

    /**
     * subAccountId
     *
     * @return int
     */
    int getSubAccountId();

    /**
     * accountId
     *
     * @return int
     */
    int getAccountId();

    /**
     * instrument
     *
     * @return Instrument
     */
    Instrument getInstrument();

    /**
     * OrdQty
     *
     * @return OrdQty
     */
    OrdQty getQty();

    /**
     * OrdPrice
     *
     * @return OrdPrice
     */
    OrdPrice getPrice();

    /**
     * OrdType
     *
     * @return OrdType
     */
    OrdType getType();

    /**
     * OrdValid
     *
     * @return OrdValid
     */
    OrdValid getValid();

    /**
     * TrdDirection
     *
     * @return TrdDirection
     */
    TrdDirection getDirection();

    /**
     * OrdTimestamp
     *
     * @return OrdTimestamp
     */
    OrdTimestamp getTimestamp();

    /**
     * OrdStatus
     *
     * @return current status
     */
    OrdStatus getStatus();

    /**
     * @param status Order
     */
    Order setStatus(@Nonnull OrdStatus status);

    /**
     * remark
     *
     * @return OrdRemark
     */
    OrdRemark getRemark();

    /**
     * @param remark String
     */
    default void addRemark(@Nonnull String remark) {
        getRemark().add(remark);
    }

    /**
     * @return int
     */
    int getOrdLevel();

    /**
     * @param log Logger
     * @param msg String
     */
    void writeLog(Logger log, String msg);

    @Override
    default long serialId() {
        return getOrdSysId();
    }

    @Override
    default int compareTo(Order o) {
        return getOrdLevel() > o.getOrdLevel() ? -1
                : getOrdLevel() < o.getOrdLevel() ? 1
                : Long.compare(getOrdSysId(), o.getOrdSysId());
    }

}
