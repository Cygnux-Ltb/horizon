package io.gemini.definition.event;

import io.gemini.definition.order.Order;

@FunctionalInterface
public interface OrderHandler {

	void onOrder(Order order);

}
