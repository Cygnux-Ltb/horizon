package io.horizon.trader.strategy;

public interface StrategyRule {

	boolean judgement();

	default boolean and(StrategyRule rule) {
		return judgement() && rule.judgement();
	}

	default boolean or(StrategyRule rule) {
		return judgement() || rule.judgement();
	}

	default boolean not() {
		return !judgement();
	}

}
