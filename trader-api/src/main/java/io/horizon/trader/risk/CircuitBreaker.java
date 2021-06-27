package io.horizon.trader.risk;

public interface CircuitBreaker {

	/**
	 * 启用账户
	 * 
	 * @param accountId
	 */
	void enableAccount(int accountId);

	/**
	 * 关闭账户
	 * 
	 * @param accountId
	 */
	void disableAccount(int accountId);

	/**
	 * 启用标的
	 * 
	 * @param instrumentId
	 */
	void enableInstrument(int instrumentId);

	/**
	 * 关闭标的
	 * 
	 * @param instrumentId
	 */
	void disableInstrument(int instrumentId);

}
