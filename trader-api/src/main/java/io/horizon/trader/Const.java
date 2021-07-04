package io.horizon.trader;

public interface Const {

	/**
	 * 系统可允许的最大策略ID
	 */
	int MaxStrategyId = 1023;

	/**
	 * 接收到非系统报单的订单回报, 统一使用此策略ID, 用于根据订单回报创建订单, 并管理订单状态.
	 */
	int ExternalOrderStrategyId = 0;

}