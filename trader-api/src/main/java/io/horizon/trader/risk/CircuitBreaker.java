package io.horizon.trader.risk;

public interface CircuitBreaker {

    /**
     * Enable Account
     *
     * @param accountId int
     */
    void enableAccount(int accountId);

    /**
     * Disable Account
     *
     * @param accountId int
     */
    void disableAccount(int accountId);

    /**
     * Enable Instrument
     *
     * @param instrumentId int
     */
    void enableInstrument(int instrumentId);

    /**
     * Disable Instrument
     *
     * @param instrumentId int
     */
    void disableInstrument(int instrumentId);

}
