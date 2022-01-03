package io.horizon.trader.adaptor;

import javax.annotation.Nonnull;

import io.horizon.market.instrument.InstrumentKeeper;
import io.horizon.trader.account.Account;
import io.mercury.common.annotation.AbstractFunction;
import io.mercury.common.fsm.Enableable;
import io.mercury.common.fsm.EnableableComponent;
import io.mercury.common.lang.Assertor;

/**
 * 
 * @author yellow013
 *
 * @param <M>
 */
public abstract class AbstractAdaptor extends EnableableComponent implements Adaptor, Enableable {

	/**
	 * Adaptor标识
	 */
	protected final String adaptorId;

	/**
	 * 托管投资账户
	 */
	protected final Account account;

	/**
	 * 
	 * @param prefix
	 * @param account
	 */
	protected AbstractAdaptor(@Nonnull String prefix, @Nonnull Account account) {
		Assertor.nonNull(prefix, "prefix");
		Assertor.nonNull(account, "account");
		this.account = account;
		this.adaptorId = prefix + "-" + account.getBrokerName() + "-" + account.getInvestorId();
		AdaptorKeeper.putAdaptor(this);
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
	public boolean startup() throws IllegalStateException, AdaptorStartupException {
		if (!InstrumentKeeper.isInitialized())
			throw new IllegalStateException("Instrument Manager uninitialized");
		try {
			return startup0();
		} catch (Exception e) {
			throw new AdaptorStartupException(adaptorId, e);
		}
	}

	@AbstractFunction
	protected abstract boolean startup0() throws Exception;

}
