package io.horizon.trader.adaptor;

import java.io.IOException;

import javax.annotation.Nonnull;

import io.horizon.market.instrument.InstrumentKeeper;
import io.horizon.trader.account.Account;
import io.mercury.common.annotation.AbstractFunction;
import io.mercury.common.fsm.EnableableComponent;
import io.mercury.common.lang.Asserter;
import io.mercury.common.lang.exception.ComponentStartupException;

/**
 * 
 * @author yellow013
 *
 * @param <M>
 */
public abstract class AbstractAdaptor extends EnableableComponent implements Adaptor {

	/**
	 * Adaptor标识
	 */
	protected final String adaptorId;

	/**
	 * 托管投资账户
	 */
	protected final Account account;

	protected AdaptorRunMode mode = AdaptorRunMode.Normal;

	/**
	 * 
	 * @param prefix
	 * @param account
	 */
	protected AbstractAdaptor(@Nonnull String prefix, @Nonnull Account account) {
		Asserter.nonNull(prefix, "prefix");
		Asserter.nonNull(account, "account");
		this.account = account;
		this.adaptorId = prefix + "[" + account.getBrokerName() + ":" + account.getInvestorId() + "]";
		AdaptorFinder.putAdaptor(this);
	}

	@Override
	public String getAdaptorId() {
		return adaptorId;
	}

	@Override
	public Account getAccount() {
		return account;
	}

	@Override
	public boolean startup() throws IOException, IllegalStateException, ComponentStartupException {
		if (!InstrumentKeeper.isInitialized())
			throw new IllegalStateException("Instrument Keeper uninitialized");
		try {
			return startup0();
		} catch (IOException ioe) {
			throw ioe;
		} catch (Exception e) {
			throw new ComponentStartupException("Adaptor[" + adaptorId + "] -> " + e.getMessage(), e);
		}
	}

	@AbstractFunction
	protected abstract boolean startup0() throws Exception;

}
