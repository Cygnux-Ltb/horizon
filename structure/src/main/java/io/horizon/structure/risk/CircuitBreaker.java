package io.horizon.structure.risk;

public interface CircuitBreaker {

	void enableAccount(int accountId);

	void disableAccount(int accountId);

	void enableInstrument(int instrumentId);

	void disableInstrument(int instrumentId);

}
