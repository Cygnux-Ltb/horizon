package io.horizon.trader.adaptor;

import java.io.Closeable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.eclipse.collections.api.list.ImmutableList;

import io.horizon.market.instrument.Instrument;
import io.horizon.trader.account.Account;
import io.horizon.trader.order.ChildOrder;
import io.mercury.common.collections.ImmutableLists;
import io.mercury.common.fsm.Enableable;

public interface Adaptor extends Closeable, Enableable {

	/**
	 * 
	 * @return Adaptor ID
	 */
	String getAdaptorId();

	/**
	 * 
	 * @return Account List
	 */
	@Nonnull
	ImmutableList<Account> getAccounts();

	/**
	 * 
	 * @return Account
	 */
	@Nonnull
	default Account getAccount() {
		final ImmutableList<Account> accounts = getAccounts();
		if (ImmutableLists.isNullOrEmpty(accounts))
			throw new NullPointerException("accounts cannot be null or empty");
		return accounts.getFirst();
	}

	/**
	 * Adaptor 启动函数
	 * 
	 * @return
	 * @throws IllegalStateException
	 * @throws AdaptorStartupException
	 */
	boolean startup() throws IllegalStateException, AdaptorStartupException;

	/**
	 * 订阅行情
	 * 
	 * @param subscribeMarketData
	 * @return
	 */
	boolean subscribeMarketData(@Nonnull Instrument... instruments);

	/**
	 * 使用默认账户发送新订单
	 * 
	 * @param order
	 * @return
	 */
	default boolean newOredr(@Nonnull ChildOrder order) {
		return newOredr(null, order);
	}

	/**
	 * 发送指定账户新订单
	 * 
	 * @param account
	 * @param order
	 * @return
	 */
	boolean newOredr(@Nullable Account account, @Nonnull ChildOrder order);

	/**
	 * 使用默认账户发送撤单请求
	 * 
	 * @param order
	 * @return
	 */
	default boolean cancelOrder(@Nonnull ChildOrder order) {
		return cancelOrder(null, order);
	}

	/**
	 * 发送指定账户撤单请求
	 * 
	 * @param account
	 * @param order
	 * @return
	 */
	boolean cancelOrder(@Nullable Account account, @Nonnull ChildOrder order);

	/**
	 * 使用默认账户查询订单
	 * 
	 * @param account
	 * @return
	 */
	default boolean queryOrder(@Nonnull Instrument instrument) {
		return queryOrder(null, instrument);
	}

	/**
	 * 查询指定账户订单
	 * 
	 * @param account
	 * @param instrument
	 * @return
	 */
	boolean queryOrder(@Nullable Account account, @Nonnull Instrument instrument);

	/**
	 * 使用默认账户查询持仓
	 * 
	 * @param instrument
	 * @return
	 */
	default boolean queryPositions(@Nonnull Instrument instrument) {
		return queryPositions(null, instrument);
	}

	/**
	 * 查询指定账户持仓
	 * 
	 * @param account
	 * @param instrument
	 * @return
	 */
	boolean queryPositions(@Nullable Account account, @Nonnull Instrument instrument);

	/**
	 * 使用默认账户查询余额
	 * 
	 * @return
	 */
	default boolean queryBalance() {
		return queryBalance(null);
	}

	/**
	 * 查询指定账户余额
	 * 
	 * @param account
	 * @return
	 */
	boolean queryBalance(@Nullable Account account);

	/**
	 * 
	 * @author yellow013
	 *
	 */
	public static class AdaptorStartupException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = -336810140547285727L;

		public AdaptorStartupException(String adaptorId, Throwable throwable) {
			super("Adaptor startup exception, adaptorId -> " + adaptorId, throwable);
		}

	}

}
