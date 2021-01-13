package io.horizon.structure.order;

import static io.mercury.common.collections.ImmutableSets.newImmutableSet;

import org.eclipse.collections.api.set.ImmutableSet;

public final class GroupOrder {

	// 订单组ID
	private final long groupOrdId;

	// 订单组时间戳
	private OrdTimestamp groupTimestamp;

	// 包含的订单
	private ImmutableSet<Order> includedOrders;

	public GroupOrder(long groupOrdId, Order... orders) {
		this.groupOrdId = groupOrdId;
		this.groupTimestamp = OrdTimestamp.generate();
		this.includedOrders = newImmutableSet(orders);
	}

	public long groupOrdId() {
		return groupOrdId;
	}

	public OrdTimestamp groupTimestamp() {
		return groupTimestamp;
	}

	public ImmutableSet<Order> includedOrders() {
		return includedOrders;
	}

}
