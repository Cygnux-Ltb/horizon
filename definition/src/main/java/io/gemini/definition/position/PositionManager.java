package io.gemini.definition.position;

import io.gemini.definition.order.actual.ChildOrder;

public interface PositionManager<T extends Position> {

	void putPosition(T position);

	T getPosition(int accountId, int instrumentId);

	default void onChildOrder(int accountId, int instrumentId, ChildOrder order) {
		getPosition(accountId, instrumentId).updatePosition(order);
	}

}
