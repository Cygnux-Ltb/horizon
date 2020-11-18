package io.horizon.definition.event.handler;

import io.horizon.definition.order.Order;

@FunctionalInterface
public interface OrderHandler {

	void onOrder(Order order);

}
