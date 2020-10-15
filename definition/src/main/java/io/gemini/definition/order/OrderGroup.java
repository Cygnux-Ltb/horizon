package io.gemini.definition.order;

import org.eclipse.collections.api.set.ImmutableSet;

import io.gemini.definition.order.structure.OrdTimestamp;
import io.mercury.common.collections.ImmutableSets;

public final class OrderGroup {

	private long groupUniqueId;
	private OrdTimestamp ordTimestamp;

	private ImmutableSet<Order> orderSet;

	public OrderGroup(Order... orders) {
		this.orderSet = ImmutableSets.newImmutableSet(orders);
		this.ordTimestamp = OrdTimestamp.generate();
	}

	public long groupUniqueId() {
		return groupUniqueId;
	}

	public ImmutableSet<Order> getOrderSet() {
		return orderSet;
	}

	public OrdTimestamp ordTimestamp() {
		return ordTimestamp;
	}

}
