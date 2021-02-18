package io.horizon.structure.adaptor;

import static io.mercury.common.collections.ImmutableLists.newImmutableList;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.ImmutableList;

import io.horizon.structure.account.Account;
import io.horizon.structure.account.AccountKeeper;
import io.horizon.structure.event.InboundScheduler;
import io.horizon.structure.event.handler.AdaptorEventHandler;
import io.horizon.structure.event.handler.MarketDataHandler;
import io.horizon.structure.event.handler.OrderReportHandler;
import io.horizon.structure.market.data.MarketData;
import io.horizon.structure.market.instrument.InstrumentKeeper;
import io.mercury.common.annotation.lang.AbstractFunction;
import io.mercury.common.fsm.Enableable;
import io.mercury.common.fsm.EnableableComponent;
import io.mercury.common.util.Assertor;
import lombok.Getter;

public abstract class AbstractAdaptor<M extends MarketData> extends EnableableComponent implements Adaptor, Enableable {

	// Adaptor标识
	@Getter
	private final int adaptorId;

	// Adaptor名称
	@Getter
	private final String adaptorName;

	// 行情处理器
	protected final MarketDataHandler<M> marketDataHandler;

	// 订单回报处理器
	protected final OrderReportHandler orderReportHandler;

	// Adaptor事件处理器
	protected final AdaptorEventHandler adaptorEventHandler;

	// 托管投资账户
	@Getter
	private final ImmutableList<Account> accounts;

	protected AbstractAdaptor(int adaptorId, @Nonnull String adaptorName, @Nonnull InboundScheduler<M> scheduler,
			@Nonnull Account... accounts) {
		this(adaptorId, adaptorName, scheduler, scheduler, scheduler, accounts);
	}

	protected AbstractAdaptor(int adaptorId, @Nonnull String adaptorName,
			@Nonnull MarketDataHandler<M> marketDataHandler, @Nonnull OrderReportHandler orderReportHandler,
			@Nonnull AdaptorEventHandler adaptorEventHandler, @Nonnull Account... accounts) {
		Assertor.nonNull(adaptorName, "adaptorName");
		Assertor.nonNull(marketDataHandler, "marketDataHandler");
		Assertor.nonNull(orderReportHandler, "orderReportHandler");
		Assertor.nonNull(adaptorEventHandler, "adaptorEventHandler");
		Assertor.requiredLength(accounts, 1, "accounts");
		this.adaptorId = adaptorId;
		this.adaptorName = adaptorName;
		this.marketDataHandler = marketDataHandler;
		this.orderReportHandler = orderReportHandler;
		this.adaptorEventHandler = adaptorEventHandler;
		this.accounts = newImmutableList(accounts);
		AdaptorKeeper.putAdaptor(this);
	}

	@Override
	public boolean startup() throws IllegalStateException, AdaptorStartupException {
		if (!AccountKeeper.isInitialized())
			throw new IllegalStateException("Account Keeper uninitialized");
		if (!InstrumentKeeper.isInitialized())
			throw new IllegalStateException("Instrument Manager uninitialized");
		try {
			return startup0();
		} catch (Exception e) {
			throw new AdaptorStartupException(adaptorId, adaptorName, e);
		}
	}

	@AbstractFunction
	protected abstract boolean startup0() throws Exception;

}
