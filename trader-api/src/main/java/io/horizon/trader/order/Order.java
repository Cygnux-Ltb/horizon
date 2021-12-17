package io.horizon.trader.order;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

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

/**
 * 
 * @author yellow013
 *
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
	 * @return
	 */
	int getStrategyId();

	/**
	 * subAccountId
	 *
	 * @return
	 */
	int getSubAccountId();

	/**
	 * accountId
	 *
	 * @return
	 */
	int getAccountId();

	/**
	 * instrument
	 *
	 * @return
	 */
	Instrument getInstrument();

	/**
	 * OrdQty
	 *
	 * @return
	 */
	OrdQty getQty();

	/**
	 * OrdPrice
	 *
	 * @return
	 */
	OrdPrice getPrice();

	/**
	 * OrdType
	 *
	 * @return
	 */
	OrdType getType();

	/**
	 * OrdValid
	 * 
	 * @return
	 */
	OrdValid getValid();

	/**
	 * TrdDirection
	 *
	 * @return
	 */
	TrdDirection getDirection();

	/**
	 * OrdTimestamp
	 *
	 * @return
	 */
	OrdTimestamp getTimestamp();

	/**
	 * OrdStatus
	 *
	 * @return current status
	 */
	OrdStatus getStatus();

	/**
	 * @param status
	 */
	Order setStatus(@Nonnull OrdStatus status);

	/**
	 * remark
	 *
	 * @return
	 */
	OrdRemark getRemark();

	/**
	 * @param remark
	 */
	default void addRemark(@Nonnull String remark) {
		getRemark().add(remark);
	}

	/**
	 * @return
	 */
	int getOrdLevel();

	/**
	 * @param log
	 * @param msg
	 */
	void writeLog(Logger log, String msg);

	@Override
	default long getSerialId() {
		return getOrdSysId();
	}

	@Override
	default int compareTo(Order o) {
		return getOrdLevel() > o.getOrdLevel() ? -1
				: getOrdLevel() < o.getOrdLevel() ? 1
						: getOrdSysId() < o.getOrdSysId() ? -1 
								: getOrdSysId() > o.getOrdSysId() ? 1 : 0;
	}

}
