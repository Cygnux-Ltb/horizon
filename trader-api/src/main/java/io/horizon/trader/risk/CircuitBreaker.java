package io.horizon.trader.risk;

public interface CircuitBreaker {

	/**
	 * Enable Account
	 * 
	 * @param accountId
	 */
	void enableAccount(int accountId);

	/**
	 * Disable Account
	 * 
	 * @param accountId
	 */
	void disableAccount(int accountId);

	/**
	 * Enable Instrument
	 * 
	 * @param instrumentId
	 */
	void enableInstrument(int instrumentId);

	/**
	 * Disable Instrument
	 * 
	 * @param instrumentId
	 */
	void disableInstrument(int instrumentId);

}
