package io.horizon.structure.event.handler;

import javax.annotation.Nonnull;

import io.horizon.structure.order.Order;

@FunctionalInterface
public interface OrderHandler {

	void onOrder(@Nonnull final Order order);

}
