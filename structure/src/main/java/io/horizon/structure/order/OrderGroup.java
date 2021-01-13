package io.horizon.structure.order;

import static io.mercury.common.collections.ImmutableSets.newImmutableSet;

import org.eclipse.collections.api.set.ImmutableSet;

import io.horizon.structure.order.actual.ActualOrder;
import lombok.Getter;

public final class OrderGroup {

	// 订单组ID
	@Getter
	private final long groupOrdId;

	// 订单组时间戳
	@Getter
	private final OrdTimestamp groupTimestamp;

	// 包含的订单
	@Getter
	private final ImmutableSet<Order> actualOrders;

	public OrderGroup(long groupOrdId, ActualOrder... orders) {
		this.groupOrdId = groupOrdId;
		this.groupTimestamp = OrdTimestamp.generate();
		this.actualOrders = newImmutableSet(orders);
	}

}
