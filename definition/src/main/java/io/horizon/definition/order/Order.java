package io.horizon.definition.order;

import java.io.Serializable;

import org.slf4j.Logger;

import io.horizon.definition.market.instrument.Instrument;
import io.horizon.definition.order.enums.OrdStatus;
import io.horizon.definition.order.enums.OrdType;
import io.horizon.definition.order.enums.TrdDirection;
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

	OrdQty qty();

	OrdPrice price();

	OrdType type();

	TrdDirection direction();

	OrdTimestamp timestamp();

	OrdStatus status();

	void setStatus(OrdStatus ordStatus);

	String remark();

	void setRemark(String remark);

	int ordLevel();

	long ownerOrdId();

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
