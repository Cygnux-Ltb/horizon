package io.horizon.structure.market.instrument.impl;

import io.horizon.structure.market.instrument.api.Futures;

public final class JapanFutures extends Futures {

	public JapanFutures(JapanFuturesSymbol symbol, int term) {
		// TODO 设置id和code
		super(0, "", symbol);
	}

}
