package io.horizon.structure.order;

import org.eclipse.collections.api.set.ImmutableSet;

import io.mercury.common.collections.ImmutableSets;

public final class OrderGroup {

	//
	private final long groupOrdId;
	
	//
	private OrdTimestamp groupTimestamp;

	//
	private ImmutableSet<Order> orderSet;

	public OrderGroup(long groupOrdId, Order... orders) {
		this.groupOrdId = groupOrdId;
		this.groupTimestamp = OrdTimestamp.generate();
		this.orderSet = ImmutableSets.newImmutableSet(orders);
	}

	public long groupOrdId() {
		return groupOrdId;
	}

	public OrdTimestamp groupTimestamp() {
		return groupTimestamp;
	}

	public ImmutableSet<Order> getOrderSet() {
		return orderSet;
	}

}
