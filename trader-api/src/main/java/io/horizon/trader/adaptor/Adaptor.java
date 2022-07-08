package io.horizon.trader.adaptor;

import io.horizon.market.data.MarketDataFeed;

public interface Adaptor extends MarketDataFeed, TraderAdaptor {

    AdaptorType getAdaptorType();

}
