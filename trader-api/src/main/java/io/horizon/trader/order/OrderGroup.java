package io.horizon.trader.order;

import static io.mercury.common.collections.ImmutableSets.newImmutableSet;

import org.eclipse.collections.api.set.ImmutableSet;

import io.horizon.trader.order.attr.OrdTimestamp;

public final class OrderGroup {

	/*
	 * 订单组ID
	 */

	private final long groupOrdId;

	/*
	 * 订单组时间戳
	 */

	private final OrdTimestamp groupTimestamp;

	/*
	 * 包含的订单
	 */

	private final ImmutableSet<Order> actualOrders;

	public OrderGroup(long groupOrdId, ChildOrder... orders) {
		this.groupOrdId = groupOrdId;
		this.groupTimestamp = OrdTimestamp.withNow();
		this.actualOrders = newImmutableSet(orders);
	}

	public long getGroupOrdId() {
		return groupOrdId;
	}

	public OrdTimestamp getGroupTimestamp() {
		return groupTimestamp;
	}

	public ImmutableSet<Order> getActualOrders() {
		return actualOrders;
	}

}
