package io.horizon.trader.adaptor;

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.Nonnull;

import io.horizon.trader.account.Account;
import io.horizon.trader.transport.inbound.CancelOrder;
import io.horizon.trader.transport.inbound.NewOrder;
import io.horizon.trader.transport.inbound.QueryBalance;
import io.horizon.trader.transport.inbound.QueryOrder;
import io.horizon.trader.transport.inbound.QueryPositions;
import io.mercury.common.fsm.Enableable;
import io.mercury.common.lang.exception.ComponentStartupException;

public interface TraderAdaptor extends Closeable, Enableable{

	/**
	 * 
	 * @return Adaptor ID
	 */
	@Nonnull
	String getAdaptorId();

	/**
	 * 
	 * @return Account
	 */
	@Nonnull
	Account getAccount();

	/**
	 * Adaptor 启动函数
	 * 
	 * @return
	 * @throws IllegalStateException
	 */
	boolean startup() throws IOException, IllegalStateException, ComponentStartupException;

	/**
	 * 发送新订单
	 * 
	 * @param order
	 * @return
	 */
	boolean newOredr(@Nonnull NewOrder order);

	/**
	 * 发送撤单请求
	 * 
	 * @param order
	 * @return
	 */
	boolean cancelOrder(@Nonnull CancelOrder order);

	/**
	 * 查询订单
	 * 
	 * @param account
	 * @param instrument
	 * @return
	 */
	boolean queryOrder(@Nonnull QueryOrder req);

	/**
	 * 查询持仓
	 * 
	 * @param instrument
	 * @return
	 */
	boolean queryPositions(@Nonnull QueryPositions req);

	/**
	 * 查询余额
	 * 
	 * @return
	 */
	boolean queryBalance(@Nonnull QueryBalance req);

}
