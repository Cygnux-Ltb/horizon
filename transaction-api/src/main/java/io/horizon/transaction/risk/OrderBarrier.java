package io.horizon.structure.risk;

import java.util.function.Predicate;

import io.horizon.structure.order.Order;

@FunctionalInterface
public interface OrderBarrier<O extends Order> extends Predicate<O> {

	boolean filter(O order);

	@Override
	default boolean test(O t) {
		return filter(t);
	}

}
