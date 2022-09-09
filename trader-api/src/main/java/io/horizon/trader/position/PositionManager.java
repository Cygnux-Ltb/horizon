package io.horizon.trader.position;

import io.horizon.market.instrument.Instrument;
import io.horizon.trader.account.AccountPosition;
import io.horizon.trader.order.ChildOrder;

/**
 * 持仓管理接口
 *
 * @param <P>
 * @author yellow013
 */
public interface PositionManager<P extends Position> {

    /**
     * 获取实际账户持仓集合
     *
     * @param accountId int
     * @return AccountPosition<P>
     */
    AccountPosition<P> getAccountPosition(int accountId);

    /**
     * 为实际账户和instrument分配初始持仓
     *
     * @param accountId  int
     * @param instrument Instrument
     * @return P
     */
    default P acquirePosition(int accountId, Instrument instrument) {
        return getAccountPosition(accountId).acquirePosition(instrument);
    }

    /**
     * 获取指定实际账户和instrument的持仓, 并以订单回报更新持仓
     *
     * @param accountId  int
     * @param instrument Instrument
     * @param order      ChildOrder
     */
    default void onChildOrder(int accountId, Instrument instrument, ChildOrder order) {
        acquirePosition(accountId, instrument).updateWithOrder(order);
    }

}
