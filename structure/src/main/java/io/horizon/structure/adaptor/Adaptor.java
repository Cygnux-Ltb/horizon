package io.horizon.structure.adaptor;

import java.io.Closeable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.eclipse.collections.api.list.ImmutableList;

import io.horizon.structure.account.Account;
import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.actual.ChildOrder;
import io.mercury.common.fsm.Enable;

public interface Adaptor extends Closeable, Enable<Adaptor> {

	/**
	 * Adaptor ID
	 */
	int getAdaptorId();

	/**
	 * Adaptor Name
	 */
	String getAdaptorName();

	/**
	 * 
	 * @return Account List
	 */
	ImmutableList<Account> getAccounts();

	/**
	 * 
	 * @return
	 */
	default Account getAccount() {
		return getAccounts().getFirst();
	}

	/**
	 * Adaptor 启动函数
	 */
	boolean startup() throws RuntimeException;

	/**
	 * 发送命令
	 * 
	 * @param command
	 * @return
	 */
	boolean sendCommand(@Nonnull Command command);

	/**
	 * 订阅行情
	 * 
	 * @param subscribeMarketData
	 * @return
	 */
	boolean subscribeMarketData(@Nonnull Instrument... instruments);

	/**
	 * 发送新订单
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
	 * @param order
	 * @return
	 */
	boolean newOredr(@Nullable Account account, @Nonnull ChildOrder order);

	/**
	 * 发送撤单请求
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
	 * @param order
	 * @return
	 */
	boolean cancelOrder(@Nullable Account account, @Nonnull ChildOrder order);

	/**
	 * 查询订单
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
	 * @return
	 */
	boolean queryOrder(@Nullable Account account, @Nonnull Instrument instrument);

	/**
	 * 查询持仓
	 * 
	 * @param account
	 * @return
	 */
	default boolean queryPositions(@Nonnull Instrument instrument) {
		return queryPositions(null, instrument);
	}

	/**
	 * 查询指定账户持仓
	 * 
	 * @param account
	 * @return
	 */
	boolean queryPositions(@Nullable Account account, @Nonnull Instrument instrument);

	/**
	 * 查询余额
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

}
