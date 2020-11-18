package io.horizon.definition.adaptor;

import static io.mercury.common.collections.ImmutableLists.newImmutableList;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.ImmutableList;

import io.horizon.definition.account.Account;
import io.horizon.definition.account.AccountKeeper;
import io.horizon.definition.event.InboundScheduler;
import io.horizon.definition.market.data.MarketData;
import io.horizon.definition.market.instrument.InstrumentManager;
import io.mercury.common.fsm.EnableComponent;
import io.mercury.common.util.Assertor;

public abstract class AdaptorBaseImpl<M extends MarketData> extends EnableComponent<Adaptor> implements Adaptor {

	private final int adaptorId;
	private final String adaptorName;
	private final ImmutableList<Account> accounts;

	protected final InboundScheduler<M> scheduler;

	protected AdaptorBaseImpl(int adaptorId, @Nonnull String adaptorName, @Nonnull InboundScheduler<M> scheduler,
			@Nonnull Account... accounts) {
		Assertor.requiredLength(accounts, 1, "accounts");
		this.adaptorId = adaptorId;
		this.adaptorName = adaptorName;
		this.scheduler = scheduler;
		this.accounts = newImmutableList(accounts);
		AdaptorKeeper.putAdaptor(this);
	}

	@Override
	public int adaptorId() {
		return adaptorId;
	}

	@Override
	public String adaptorName() {
		return adaptorName;
	}

	@Override
	public ImmutableList<Account> accounts() {
		return accounts;
	}

	@Override
	protected Adaptor returnThis() {
		return this;
	}

	@Override
	public boolean startup() throws RuntimeException {
		if (!AccountKeeper.isInitialized())
			throw new IllegalStateException("Account Keeper uninitialized");
		if (!InstrumentManager.isInitialized())
			throw new IllegalStateException("Instrument Manager uninitialized");
		try {
			return startup0();
		} catch (Exception e) {
			throw new RuntimeException("Adaptor startup throw RuntimeException, adaptorName -> " + adaptorName, e);
		}
	}

	protected abstract boolean startup0();

}
