package io.horizon.structure.order;

import java.io.Serializable;

import org.slf4j.Logger;

import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.enums.OrdStatus;
import io.horizon.structure.order.enums.OrdType;
import io.horizon.structure.order.enums.TrdDirection;
import io.mercury.common.thread.Threads;

public interface Order extends Comparable<Order>, Serializable {

	/**
	 * uniqueId构成<br>
	 * 策略Id | 时间戳Second | 自增量Number<br>
	 * strategyId | epochSecond| increment<br>
	 * 922 | 3372036854 | 775807<br>
	 * 
	 * TODO 使用雪花算法实现
	 * 
	 * @return long
	 */
	long getOrdId();

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
	 * @return
	 */
	long getOwnerOrdId();

	/**
	 * 
	 * @param log
	 * @param objName
	 * @param msg
	 */
	void writeLog(Logger log, String objName, String msg);

	@Override
	default int compareTo(Order o) {
		return getOrdLevel() > o.getOrdLevel() ? -1
				: getOrdLevel() < o.getOrdLevel() ? 1 : getOrdId() < o.getOrdId() ? -1 : getOrdId() > o.getOrdId() ? 1 : 0;
	}

	public static void main(String[] args) {

		System.out.println(Long.MAX_VALUE);

		long seed = System.nanoTime();
		long seed1 = System.currentTimeMillis();
		System.out.println(seed);
		System.out.println(seed1);
		for (;;) {
			System.out.println((System.nanoTime() - seed) / 100000);
			Threads.sleep(20);
		}

	}

}
