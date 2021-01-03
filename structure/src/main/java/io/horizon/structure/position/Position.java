package io.horizon.structure.position;

import java.io.Serializable;

import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.Order;
import lombok.Getter;

public interface Position<P extends Position<P>> extends Comparable<Position<P>>, Serializable {

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
	P setCurrentQty(int qty);

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
	P setTradeableQty(int qty);

	/**
	 * 使用订单更新仓位
	 * 
	 * @param order
	 */
	void updateWithOrder(Order order);

	@Override
	default int compareTo(Position<P> o) {
		return this.getAccountId() < o.getAccountId() ? -1
				: this.getAccountId() > o.getAccountId() ? 1
						: this.getInstrument().instrumentId() < o.getInstrument().instrumentId() ? -1
								: this.getInstrument().instrumentId() > o.getInstrument().instrumentId() ? 1 : 0;
	}

	/**
	 * 
	 * 持仓对象基础实现
	 * 
	 * @author yellow013
	 *
	 * @param <I>
	 */
	public static abstract class PositionBaseImpl<P extends Position<P>> implements Position<P> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7464979857942714749L;

		@Getter
		private final int accountId;
		@Getter
		private final Instrument instrument;
		@Getter
		private int currentQty;

		public PositionBaseImpl(int accountId, Instrument instrument) {
			this.accountId = accountId;
			this.instrument = instrument;
		}

		@Override
		public final P setCurrentQty(int qty) {
			this.currentQty = qty;
			return returnSelf();
		}
		
		protected abstract P returnSelf();

	}

}
