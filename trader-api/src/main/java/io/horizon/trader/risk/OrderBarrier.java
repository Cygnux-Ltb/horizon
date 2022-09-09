package io.horizon.trader.risk;

import io.horizon.trader.order.ChildOrder;

import java.util.function.Predicate;

@FunctionalInterface
public interface OrderBarrier extends Predicate<ChildOrder> {

	boolean filter(ChildOrder order);

	@Override
	default boolean test(ChildOrder t) {
		return filter(t);
	}

}
