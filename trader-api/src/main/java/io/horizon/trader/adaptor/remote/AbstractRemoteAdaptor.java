package io.horizon.trader.adaptor.remote;

import static io.mercury.common.collections.ImmutableLists.newImmutableList;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.ImmutableList;

import io.horizon.market.data.MarketData;
import io.horizon.market.handler.MarketDataHandler;
import io.horizon.market.instrument.InstrumentKeeper;
import io.horizon.trader.account.Account;
import io.horizon.trader.account.AccountKeeper;
import io.horizon.trader.adaptor.Adaptor;
import io.horizon.trader.adaptor.AdaptorKeeper;
import io.horizon.trader.handler.AdaptorEventHandler;
import io.horizon.trader.handler.InboundScheduler;
import io.horizon.trader.handler.OrderReportHandler;
import io.mercury.common.annotation.lang.AbstractFunction;
import io.mercury.common.fsm.Enableable;
import io.mercury.common.fsm.EnableableComponent;
import io.mercury.common.util.Assertor;
import lombok.Getter;

public abstract class AbstractRemoteAdaptor<M extends MarketData> extends EnableableComponent
		implements Enableable, Adaptor, RemoteAdaptor {

	// Adaptor标识
	@Getter
	private final String adaptorId;

	// 行情处理器
	protected final MarketDataHandler<M> marketDataHandler;

	// 订单回报处理器
	protected final OrderReportHandler orderReportHandler;

	// Adaptor事件处理器
	protected final AdaptorEventHandler adaptorEventHandler;

	// 托管投资账户
	@Getter
	private final ImmutableList<Account> accounts;

	protected AbstractRemoteAdaptor(@Nonnull String prefix, @Nonnull InboundScheduler<M> scheduler,
			@Nonnull Account... accounts) {
		this(prefix, scheduler, scheduler, scheduler, accounts);
	}

	protected AbstractRemoteAdaptor(@Nonnull String prefix, @Nonnull MarketDataHandler<M> marketDataHandler,
			@Nonnull OrderReportHandler orderReportHandler, @Nonnull AdaptorEventHandler adaptorEventHandler,
			@Nonnull Account... accounts) {
		Assertor.nonNull(prefix, "prefix");
		Assertor.nonNull(marketDataHandler, "marketDataHandler");
		Assertor.nonNull(orderReportHandler, "orderReportHandler");
		Assertor.nonNull(adaptorEventHandler, "adaptorEventHandler");
		Assertor.requiredLength(accounts, 1, "accounts");
		this.accounts = newImmutableList(accounts);
		Account account = getAccount();
		this.adaptorId = prefix + "-[" + account.getBrokerName() + "]-[" + account.getInvestorId() + "]";
		this.marketDataHandler = marketDataHandler;
		this.orderReportHandler = orderReportHandler;
		this.adaptorEventHandler = adaptorEventHandler;
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
			throw new AdaptorStartupException(adaptorId, e);
		}
	}

	@AbstractFunction
	protected abstract boolean startup0() throws Exception;

}
