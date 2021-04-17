package io.horizon.transaction.order;

import java.io.Serializable;

import org.slf4j.Logger;

import io.horizon.market.instrument.Instrument;
import io.horizon.transaction.order.OrdEnum.OrdStatus;
import io.horizon.transaction.order.OrdEnum.OrdType;
import io.horizon.transaction.order.OrdEnum.TrdDirection;

public interface Order extends Comparable<Order>, Serializable {

	/**
	 * ordSysId构成<br>
	 * 策略Id | 时间戳Second | 自增量Number<br>
	 * strategyId | epochSecond| increment<br>
	 * 922 | 3372036854 | 775807<br>
	 * 
	 * TODO 使用雪花算法实现
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
	 * 
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
	 * 
	 * @param ordStatus
	 */
	Order setStatus(OrdStatus status);

	/**
	 * remark
	 * 
	 * @return
	 */
	String getRemark();

	/**
	 * 
	 * @param remark
	 */
	Order setRemark(String remark);

	/**
	 * 
	 * @return
	 */
	int getOrdLevel();

	/**
	 * 
	 * @param log
	 * @param objName
	 * @param msg
	 */
	void writeLog(Logger log, String msg);

	@Override
	default int compareTo(Order o) {
		return getOrdLevel() > o.getOrdLevel() ? -1
				: getOrdLevel() < o.getOrdLevel() ? 1
						: getOrdSysId() < o.getOrdSysId() ? -1 : getOrdSysId() > o.getOrdSysId() ? 1 : 0;
	}

}
