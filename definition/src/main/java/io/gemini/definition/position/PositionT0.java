package io.gemini.definition.position;

import io.gemini.definition.position.Position.PositionBaseImpl;

public abstract class PositionT0 extends PositionBaseImpl {

	public PositionT0(int accountId, int instrumentId) {
		super(accountId, instrumentId);
	}

	@Override
	public int tradeableQty() {
		return currentQty;
	}

	@Override
	public void setTradeableQty(int qty) {
		setCurrentQty(qty);
	}

}
