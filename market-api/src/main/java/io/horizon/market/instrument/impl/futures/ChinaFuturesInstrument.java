package io.horizon.market.instrument.impl.futures;

import io.horizon.market.instrument.PriorityCloseType;
import io.horizon.market.instrument.impl.AbstractFutures;

/**
 * 
 * @author yellow013
 * 
 */
public final class ChinaFuturesInstrument extends AbstractFutures {

	private final PriorityCloseType priorityCloseType;

	/**
	 * 
	 * @param symbol
	 * @param term
	 */
	public ChinaFuturesInstrument(ChinaFuturesSymbol symbol, int term) {
		this(symbol, term, String.valueOf(term));
	}

	/**
	 * 
	 * @param symbol
	 * @param term
	 * @param codeTail
	 */
	public ChinaFuturesInstrument(ChinaFuturesSymbol symbol, int term, String codeTail) {
		super(symbol.acquireInstrumentId(term), symbol.getSymbolCode() + codeTail, symbol, symbol.getExchange());
		this.priorityCloseType = symbol.getPriorityCloseType();
	}

	@Override
	public PriorityCloseType getPriorityCloseType() {
		return priorityCloseType;
	}

	@Override
	public boolean isAvailableImmediately() {
		return true;
	}

	@Override
	public int getTickSize() {
		return 1;
	}

}
