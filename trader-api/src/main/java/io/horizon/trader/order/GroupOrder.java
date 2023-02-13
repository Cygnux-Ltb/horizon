package io.horizon.trader.order;

import io.horizon.trader.order.attr.OrdTimestamp;
import org.eclipse.collections.api.set.ImmutableSet;

import static io.mercury.common.collections.ImmutableSets.newImmutableSet;

/**
 * @author yellow013
 */
public final class GroupOrder {

    /**
     * 订单组ID
     */
    private final long groupOrdId;

    /**
     * 订单组时间戳
     */
    private final OrdTimestamp groupTimestamp;

    /**
     * 包含的订单
     */
    private final ImmutableSet<ChildOrder> actualOrders;

    public GroupOrder(long groupOrdId, ChildOrder... orders) {
        this.groupOrdId = groupOrdId;
        this.groupTimestamp = OrdTimestamp.now();
        this.actualOrders = newImmutableSet(orders);
    }

    public long getGroupOrdId() {
        return groupOrdId;
    }

    public OrdTimestamp getGroupTimestamp() {
        return groupTimestamp;
    }

    public ImmutableSet<ChildOrder> getActualOrders() {
        return actualOrders;
    }

}
