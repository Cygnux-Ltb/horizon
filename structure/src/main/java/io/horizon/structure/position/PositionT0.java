package io.horizon.structure.position;

import io.horizon.structure.position.Position.PositionBaseImpl;
import io.mercury.serialization.json.JsonWrapper;

public abstract class PositionT0 extends PositionBaseImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1903510017988374887L;

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

	@Override
	public String toString() {
		return JsonWrapper.toJson(this);
	}

}
