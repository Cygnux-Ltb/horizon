package io.horizon.trader.adaptor;

import io.horizon.market.adaptor.MarketAdaptor;

public interface Adaptor extends MarketAdaptor, TraderAdaptor {

	public AdaptorType getAdaptorType();

}
