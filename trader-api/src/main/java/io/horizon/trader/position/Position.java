package io.horizon.trader.position;

import java.io.Serializable;

import io.horizon.market.instrument.Instrument;
import io.horizon.trader.order.Order;

public interface Position extends Comparable<Position>, Serializable {

	/**
	 * 投资者账户ID
	 * 
	 * @return
	 */
	int getAccountId();

	/**
	 * 获取Instrument
	 * 
	 * @return
	 */
	Instrument getInstrument();

	/**
	 * 获取当前仓位
	 * 
	 * @return
	 */
	int getCurrentQty();

	/**
	 * 设置当前仓位
	 * 
	 * @param qty
	 */
	void setCurrentQty(int qty);

	/**
	 * 获取可用仓位
	 * 
	 * @return
	 */
	int getTradeableQty();

	/**
	 * 设置可用仓位
	 * 
	 * @param qty
	 */
	void setTradeableQty(int qty);

	/**
	 * 使用订单更新仓位
	 * 
	 * @param order
	 */
	void updateWithOrder(Order order);

	@Override
	default int compareTo(Position o) {
		return this.getAccountId() < o.getAccountId() ? -1
				: this.getAccountId() > o.getAccountId() ? 1
						: this.getInstrument().getInstrumentId() < o.getInstrument().getInstrumentId() ? -1
								: this.getInstrument().getInstrumentId() > o.getInstrument().getInstrumentId() ? 1 : 0;
	}

}
