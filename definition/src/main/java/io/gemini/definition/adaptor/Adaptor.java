package io.gemini.definition.adaptor;

import java.io.Closeable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.eclipse.collections.api.list.ImmutableList;

import io.gemini.definition.account.Account;
import io.gemini.definition.market.instrument.Instrument;
import io.gemini.definition.order.actual.ChildOrder;
import io.mercury.common.fsm.Enable;

public interface Adaptor extends Closeable, Enable<Adaptor> {

	/**
	 * 
	 * @return
	 */
	int adaptorId();

	/**
	 * 
	 * @return
	 */
	String adaptorName();

	/**
	 * 
	 * @return
	 */
	ImmutableList<Account> accounts();

	/**
	 * Adaptor 启动函数
	 * 
	 * @return
	 */
	boolean startup() throws IllegalStateException;

	/**
	 * 
	 * @param command
	 * @return
	 */
	boolean sendCommand(Command command);

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
	 * 发送新订单
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
	 * 发送撤单请求
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
	 * 查询订单
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
	 * 查询持仓
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
	 * 查询余额
	 * 
	 * @param account
	 * @return
	 */
	boolean queryBalance(@Nullable Account account);

}
