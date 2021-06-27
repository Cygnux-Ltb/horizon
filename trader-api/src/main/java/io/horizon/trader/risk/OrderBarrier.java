package io.horizon.trader.risk;

import java.util.function.Predicate;

import io.horizon.trader.order.Order;

@FunctionalInterface
public interface OrderBarrier<O extends Order> extends Predicate<O> {

	boolean filter(O order);

	@Override
	default boolean test(O t) {
		return filter(t);
	}

}
