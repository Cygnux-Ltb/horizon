package io.horizon.trader.adaptor;

import io.horizon.trader.account.Account;
import io.horizon.trader.transport.inbound.TdxCancelOrder;
import io.horizon.trader.transport.inbound.TdxNewOrder;
import io.horizon.trader.transport.inbound.TdxQueryBalance;
import io.horizon.trader.transport.inbound.TdxQueryOrder;
import io.horizon.trader.transport.inbound.TdxQueryPositions;
import io.mercury.common.fsm.Enableable;
import io.mercury.common.lang.exception.ComponentStartupException;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;

public interface TraderAdaptor extends Closeable, Enableable {

    /**
     * @return Adaptor ID
     */
    @Nonnull
    String getAdaptorId();

    /**
     * @return Account
     */
    @Nonnull
    Account getAccount();

    /**
     * Adaptor 启动函数
     *
     * @return boolean
     */
    boolean startup() throws IOException, IllegalStateException, ComponentStartupException;

    /**
     * 发送新订单
     *
     * @param order NewOrder
     * @return boolean
     */
    boolean newOrder(@Nonnull TdxNewOrder order);

    /**
     * 发送撤单请求
     *
     * @param order CancelOrder
     * @return boolean
     */
    boolean cancelOrder(@Nonnull TdxCancelOrder order);

    /**
     * 查询订单
     *
     * @param query QueryOrder
     * @return boolean
     */
    boolean queryOrder(@Nonnull TdxQueryOrder query);

    /**
     * 查询持仓
     *
     * @param query QueryPositions
     * @return boolean
     */
    boolean queryPositions(@Nonnull TdxQueryPositions query);

    /**
     * 查询余额
     *
     * @return boolean
     */
    boolean queryBalance(@Nonnull TdxQueryBalance query);

}
