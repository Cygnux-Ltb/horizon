package io.horizon.structure.adaptor;

import static io.mercury.common.collections.ImmutableLists.newImmutableList;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.ImmutableList;

import io.horizon.structure.account.Account;
import io.horizon.structure.account.AccountManager;
import io.horizon.structure.event.InboundScheduler;
import io.horizon.structure.market.data.MarketData;
import io.horizon.structure.market.instrument.InstrumentManager;
import io.mercury.common.fsm.EnableComponent;
import io.mercury.common.util.Assertor;
import lombok.Getter;

public abstract class AdaptorBaseImpl<M extends MarketData> extends EnableComponent<Adaptor> implements Adaptor {

	// Adaptor标识
	@Getter
	private final int adaptorId;

	// Adaptor名称
	@Getter
	private final String adaptorName;

	// 入站信息调度器
	protected final InboundScheduler<M> scheduler;

	// 托管投资账户
	@Getter
	private final ImmutableList<Account> accounts;

	protected AdaptorBaseImpl(int adaptorId, @Nonnull String adaptorName, @Nonnull InboundScheduler<M> scheduler,
			@Nonnull Account... accounts) {
		Assertor.nonNull(adaptorName, "adaptorName");
		Assertor.nonNull(scheduler, "scheduler");
		Assertor.requiredLength(accounts, 1, "accounts");
		this.adaptorId = adaptorId;
		this.adaptorName = adaptorName;
		this.scheduler = scheduler;
		this.accounts = newImmutableList(accounts);
		AdaptorKeeper.putAdaptor(this);
	}

	@Override
	protected Adaptor returnThis() {
		return this;
	}

	@Override
	public boolean startup() throws RuntimeException {
		if (!AccountManager.isInitialized())
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
