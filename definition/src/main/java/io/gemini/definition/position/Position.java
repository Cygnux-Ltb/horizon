package io.gemini.definition.position;

import io.gemini.definition.order.Order;

public interface Position extends Comparable<Position> {

	int accountId();

	int instrumentId();

	int currentQty();

	void setCurrentQty(int qty);

	int tradeableQty();

	void setTradeableQty(int qty);

	void updatePosition(Order order);

	static abstract class PositionBaseImpl implements Position {

		protected int accountId;
		protected int instrumentId;
		protected int currentQty;

		public PositionBaseImpl(int accountId, int instrumentId) {
			this.instrumentId = instrumentId;
		}

		@Override
		public final int accountId() {
			return accountId;
		}

		@Override
		public final int instrumentId() {
			return instrumentId;
		}

		@Override
		public final int currentQty() {
			return currentQty;
		}

		@Override
		public final void setCurrentQty(int qty) {
			this.currentQty = qty;
		}

		@Override
		public int compareTo(Position position) {
			return this.accountId < position.accountId() ? -1
					: this.accountId > position.accountId() ? 1
							: this.instrumentId < position.instrumentId() ? -1
									: this.instrumentId > position.instrumentId() ? 1 : 0;
		}
	}

}
