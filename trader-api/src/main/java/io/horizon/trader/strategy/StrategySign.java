package io.horizon.trader.strategy;

import javax.annotation.Nonnull;

public interface StrategySign {

	int getStrategyId();

	@Nonnull
	String getStrategyName();

}
