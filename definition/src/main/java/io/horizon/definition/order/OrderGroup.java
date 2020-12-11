package io.horizon.definition.order;

import org.eclipse.collections.api.set.ImmutableSet;

import io.mercury.common.collections.ImmutableSets;

public final class OrderGroup {

	private final long groupOrdId;
	private OrdTimestamp ordTimestamp;

	private ImmutableSet<Order> orderSet;

	public OrderGroup(long groupOrdId, Order... orders) {
		this.groupOrdId = groupOrdId;
		this.ordTimestamp = OrdTimestamp.generate();
		this.orderSet = ImmutableSets.newImmutableSet(orders);
	}

	public long groupOrdId() {
		return groupOrdId;
	}

	public ImmutableSet<Order> getOrderSet() {
		return orderSet;
	}

	public OrdTimestamp ordTimestamp() {
		return ordTimestamp;
	}

}
