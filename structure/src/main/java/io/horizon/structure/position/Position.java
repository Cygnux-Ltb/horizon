package io.horizon.structure.position;

import java.io.Serializable;

import io.horizon.structure.order.Order;
import lombok.Getter;

public interface Position extends Comparable<Position>, Serializable {

	int getAccountId();

	int getInstrumentId();

	int getCurrentQty();

	int getTradeableQty();

	void setCurrentQty(int qty);

	void setTradeableQty(int qty);

	void updateWithOrder(Order order);

	static abstract class PositionBaseImpl implements Position {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7464979857942714749L;

		@Getter
		protected final int accountId;
		@Getter
		protected final int instrumentId;
		@Getter
		protected int currentQty;

		public PositionBaseImpl(int accountId, int instrumentId) {
			this.accountId = accountId;
			this.instrumentId = instrumentId;
		}

		public PositionBaseImpl(int accountId, int instrumentId, int currentQty) {
			this.accountId = accountId;
			this.instrumentId = instrumentId;
			this.currentQty = currentQty;
		}

		@Override
		public final void setCurrentQty(int qty) {
			this.currentQty = qty;
		}

		@Override
		public int compareTo(Position position) {
			return this.accountId < position.getAccountId() ? -1
					: this.accountId > position.getAccountId() ? 1
							: this.instrumentId < position.getInstrumentId() ? -1
									: this.instrumentId > position.getInstrumentId() ? 1 : 0;
		}
	}

}
