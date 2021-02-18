package io.horizon.structure.position;

import io.horizon.structure.market.instrument.Instrument;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 
 * 持仓对象基础实现
 * 
 * @author yellow013
 *
 * @param <P>
 */
@Getter
@RequiredArgsConstructor
public abstract class AbstractPosition implements Position {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7464979857942714749L;

	private final int accountId;

	private final Instrument instrument;

	@Setter
	private int currentQty;

}
