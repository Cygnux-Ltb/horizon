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
	long ordId();

	/**
	 * strategyId
	 * 
	 * @return
	 */
	int strategyId();

	/**
	 * subAccountId
	 * 
	 * @return
	 */
	int subAccountId();

	/**
	 * accountId
	 * 
	 * @return
	 */
	int accountId();

	/**
	 * instrument
	 * 
	 * @return
	 */
	Instrument instrument();

	/**
	 * OrdQty
	 * 
	 * @return
	 */
	OrdQty qty();

	/**
	 * OrdPrice
	 * 
	 * @return
	 */
	OrdPrice price();

	/**
	 * OrdType
	 * 
	 * @return
	 */
	OrdType type();

	/**
	 * 
	 * TrdDirection
	 * 
	 * @return
	 */
	TrdDirection direction();

	/**
	 * OrdTimestamp
	 * 
	 * @return
	 */
	OrdTimestamp timestamp();

	/**
	 * OrdStatus
	 * 
	 * @return
	 */
	OrdStatus status();

	/**
	 * 
	 * @param ordStatus
	 */
	Order setStatus(OrdStatus ordStatus);

	/**
	 * remark
	 * 
	 * @return
	 */
	String remark();

	/**
	 * 
	 * @param remark
	 */
	Order setRemark(String remark);

	/**
	 * 
	 * @return
	 */
	int ordLevel();

	/**
	 * 
	 * @return
	 */
	long ownerOrdId();

	/**
	 * 
	 * @param log
	 * @param objName
	 * @param msg
	 */
	void writeLog(Logger log, String objName, String msg);

	@Override
	default int compareTo(Order o) {
		return ordLevel() > o.ordLevel() ? -1
				: ordLevel() < o.ordLevel() ? 1 : ordId() < o.ordId() ? -1 : ordId() > o.ordId() ? 1 : 0;
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
