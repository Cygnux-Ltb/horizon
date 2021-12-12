package io.horizon.trader.adaptor.remote;

import javax.annotation.Nonnull;

import io.horizon.market.data.MarketData;
import io.horizon.market.handler.MarketDataHandler;
import io.horizon.market.instrument.InstrumentKeeper;
import io.horizon.trader.account.Account;
import io.horizon.trader.account.AccountKeeper;
import io.horizon.trader.adaptor.Adaptor;
import io.horizon.trader.adaptor.AdaptorKeeper;
import io.horizon.trader.handler.AdaptorReportHandler;
import io.horizon.trader.handler.InboundScheduler;
import io.horizon.trader.handler.OrderReportHandler;
import io.mercury.common.annotation.AbstractFunction;
import io.mercury.common.fsm.Enableable;
import io.mercury.common.fsm.EnableableComponent;
import io.mercury.common.lang.Assertor;

public abstract class AbstractRemoteAdaptor<M extends MarketData> extends EnableableComponent
		implements Enableable, Adaptor, RemoteAdaptor {

	// Adaptor标识
	private final String adaptorId;

	// 行情处理器
	protected final MarketDataHandler<M> marketDataHandler;

	// 订单回报处理器
	protected final OrderReportHandler orderReportHandler;

	// Adaptor事件处理器
	protected final AdaptorReportHandler adaptorReportHandler;

	// 托管投资账户

	private final Account account;

	protected AbstractRemoteAdaptor(@Nonnull String prefix, @Nonnull Account account,
			@Nonnull InboundScheduler<M> scheduler) {
		this(prefix, account, scheduler, scheduler, scheduler);
	}

	protected AbstractRemoteAdaptor(@Nonnull String prefix, @Nonnull Account account,
			@Nonnull MarketDataHandler<M> marketDataHandler, @Nonnull OrderReportHandler orderReportHandler,
			@Nonnull AdaptorReportHandler adaptorReportHandler) {
		Assertor.nonNull(prefix, "prefix");
		Assertor.nonNull(account, "account");
		Assertor.nonNull(marketDataHandler, "marketDataHandler");
		Assertor.nonNull(orderReportHandler, "orderReportHandler");
		Assertor.nonNull(adaptorReportHandler, "adaptorReportHandler");
		this.account = account;
		this.adaptorId = prefix + "-" + account.getBrokerName() + "-" + account.getInvestorId() + "";
		this.marketDataHandler = marketDataHandler;
		this.orderReportHandler = orderReportHandler;
		this.adaptorReportHandler = adaptorReportHandler;
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
