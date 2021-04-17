package io.horizon.transaction.position;

import io.horizon.market.instrument.Instrument;

@FunctionalInterface
public interface PositionProducer<P extends Position> {

	P produce(int accountId, Instrument instrument);

}
