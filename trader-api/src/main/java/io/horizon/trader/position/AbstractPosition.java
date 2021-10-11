package io.horizon.trader.position;

import io.horizon.market.instrument.Instrument;

/**
 * 
 * 持仓对象基础实现
 * 
 * @author yellow013
 *
 * @param <P>
 */

public abstract class AbstractPosition implements Position {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7464979857942714749L;

	private final int accountId;

	private final Instrument instrument;

	private int currentQty;

	protected AbstractPosition(int accountId, Instrument instrument) {
		this.accountId = accountId;
		this.instrument = instrument;
	}

	protected AbstractPosition(int accountId, Instrument instrument, int currentQty) {
		this.accountId = accountId;
		this.instrument = instrument;
		this.currentQty = currentQty;
	}

	public int getAccountId() {
		return accountId;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public int getCurrentQty() {
		return currentQty;
	}

	public void setCurrentQty(int currentQty) {
		this.currentQty = currentQty;
	}

}
