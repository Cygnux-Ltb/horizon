package io.gemini.definition.event.handler;

import io.gemini.definition.order.Order;

@FunctionalInterface
public interface OrderHandler {

	void onOrder(Order order);

}
