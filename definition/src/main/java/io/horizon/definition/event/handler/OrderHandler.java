package io.horizon.definition.event.handler;

import javax.annotation.Nonnull;

import io.horizon.definition.order.Order;

@FunctionalInterface
public interface OrderHandler {

	void onOrder(@Nonnull final Order order);

}
