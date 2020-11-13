package io.gemini.definition.position;

import io.gemini.definition.position.Position.PositionBaseImpl;

public abstract class PositionT1 extends PositionBaseImpl {

	private int tradeableQty;

	public PositionT1(int accountId, int instrumentId, int tradeableQty) {
		super(accountId, instrumentId);
		this.tradeableQty = tradeableQty;
	}

	@Override
	public int tradeableQty() {
		return tradeableQty;
	}

	@Override
	public void setTradeableQty(int tradeableQty) {
		this.tradeableQty = tradeableQty;
	}

}
