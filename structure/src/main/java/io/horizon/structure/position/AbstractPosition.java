package io.horizon.structure.position;

import io.horizon.structure.market.instrument.Instrument;
import lombok.Getter;

/**
 * 
 * 持仓对象基础实现
 * 
 * @author yellow013
 *
 * @param <P>
 */
public abstract class AbstractPosition<P extends Position<P>> implements Position<P> {

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

	public AbstractPosition(int accountId, Instrument instrument) {
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
