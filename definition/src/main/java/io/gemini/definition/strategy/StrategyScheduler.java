package io.gemini.definition.strategy;

import io.gemini.definition.event.EventScheduler;
import io.gemini.definition.market.data.api.MarketData;

/**
 * 
 * @author yellow013
 *
 * @param <M>
 */
public interface StrategyScheduler<M extends MarketData> extends EventScheduler<M> {

	void addStrategy(Strategy<M> strategy);

}
