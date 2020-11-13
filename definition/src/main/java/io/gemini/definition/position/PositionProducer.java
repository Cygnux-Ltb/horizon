package io.gemini.definition.position;

@FunctionalInterface
public interface PositionProducer<T extends Position> {

	default T produce(int accountId, int instrumentId) {
		return produce(accountId, instrumentId, 0);
	}

	T produce(int accountId, int instrumentId, int qty);

}
