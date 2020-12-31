package io.horizon.structure.strategy;

public interface StrategyIdConst {

	/*
	 * 系统可允许的最大策略ID
	 */
	int MaxStrategyId = 900;
	
	/*
	 * 接收到非系统报单的订单回报, 统一使用此策略ID, 用于根据订单回报创建订单, 并管理状态.
	 */
	int ProcessExternalOrderStrategyId = 910;

}
