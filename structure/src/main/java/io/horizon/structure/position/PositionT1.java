package io.horizon.structure.position;

import io.horizon.structure.position.Position.PositionBaseImpl;
import io.mercury.serialization.json.JsonWrapper;

public abstract class PositionT1 extends PositionBaseImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6092443026934127689L;

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

	@Override
	public String toString() {
		return JsonWrapper.toJson(this);
	}

}
