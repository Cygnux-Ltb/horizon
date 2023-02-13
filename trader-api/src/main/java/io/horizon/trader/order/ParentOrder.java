package io.horizon.trader.order;

import io.horizon.market.instrument.Instrument;
import io.horizon.trader.order.attr.OrdPrice;
import io.horizon.trader.order.attr.OrdQty;
import io.horizon.trader.order.enums.OrdType;
import io.horizon.trader.order.enums.TrdDirection;
import org.eclipse.collections.api.list.MutableList;
import org.slf4j.Logger;

import java.io.Serial;
import java.util.List;
import java.util.function.Function;

/**
 * TODO 暂时无用,
 * <p>
 * 一个实际需要执行的订单, 在具体执行时可以被拆分为多个子订单
 *
 * @author yellow013
 * @creation 2018-07-09
 */
public class ParentOrder extends AbstractOrder {

    @Serial
    private static final long serialVersionUID = -5096106824571703291L;

    /**
     * @param ordSysId     long
     * @param strategyId   int
     * @param subAccountId int
     * @param accountId    int
     * @param instrument   Instrument
     * @param qty          OrdQty
     * @param price        OrdPrice
     * @param type         OrdType
     * @param direction    TrdDirection
     * @param childOrders  MutableList<ChildOrder>
     */
    protected ParentOrder(long ordSysId, int strategyId, int subAccountId, int accountId,
                          Instrument instrument, OrdQty qty, OrdPrice price, OrdType type,
                          TrdDirection direction, MutableList<ChildOrder> childOrders) {
        super(ordSysId, strategyId, subAccountId, accountId, instrument, qty, price, type, direction);
        this.childOrders = childOrders;
    }


    /**
     * 所属子订单
     */
    private final MutableList<ChildOrder> childOrders;

    /**
     * 由外部传入拆分为多个订单的逻辑
     *
     * @param splitFunc Function<ParentOrder, Collection<ChildOrder>>
     * @return MutableList<ChildOrder>
     */
    public MutableList<ChildOrder> splitChildOrder(Function<ParentOrder, List<ChildOrder>> splitFunc) {
        this.childOrders.addAll(splitFunc.apply(this));
        return this.childOrders;
    }

    public MutableList<ChildOrder> getChildOrders() {
        return childOrders;
    }

    @Override
    public int getOrdLevel() {
        return 1;
    }

    private static final String ParentOrderTemplate = "Msg : {}, ParentOrder attr : ordSysId==[{}], "
            + "status==[{}], direction==[{}], action==[{}], type==[{}], "
            + "instrument -> {}, price -> {}, qty -> {}, timestamp -> {}";

    @Override
    public void writeLog(Logger log, String msg) {
        log.info(ParentOrderTemplate, msg, ordSysId, status, direction, null, type, instrument, price, qty, timestamp);
    }

}
