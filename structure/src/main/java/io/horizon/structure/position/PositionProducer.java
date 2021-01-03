package io.horizon.structure.position;

import io.horizon.structure.market.instrument.Instrument;

@FunctionalInterface
public interface PositionProducer<P extends Position<P>> {

	P produce(int accountId, Instrument instrument);

}
