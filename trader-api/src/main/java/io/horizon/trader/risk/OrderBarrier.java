package io.horizon.trader.risk;

import java.util.function.Predicate;

import io.horizon.trader.order.ChildOrder;

@FunctionalInterface
public interface OrderBarrier extends Predicate<ChildOrder> {

	boolean filter(ChildOrder order);

	@Override
	default boolean test(ChildOrder t) {
		return filter(t);
	}

}
