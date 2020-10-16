package io.gemini.definition.adaptor;

import static io.mercury.common.collections.ImmutableLists.newImmutableList;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.ImmutableList;

import io.gemini.definition.account.Account;
import io.gemini.definition.account.AccountKeeper;
import io.gemini.definition.event.InboundScheduler;
import io.gemini.definition.market.data.api.MarketData;
import io.gemini.definition.market.instrument.InstrumentManager;
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
	public boolean startup() throws IllegalStateException {
		if (!AccountKeeper.isInitialized())
			throw new IllegalStateException("Account Keeper uninitialized");
		if (!InstrumentManager.isInitialized())
			throw new IllegalStateException("Instrument Manager uninitialized");
		return startup0();
	}

	protected abstract boolean startup0();

}
