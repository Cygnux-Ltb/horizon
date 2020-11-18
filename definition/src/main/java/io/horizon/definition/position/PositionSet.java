package io.horizon.definition.position;

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;

import io.mercury.common.collections.MutableMaps;

/**
 * 实际账户的持仓集合
 * 
 * @author yellow013
 * @creation 2018年5月14日
 * @param <T>
 */
public final class PositionSet<T extends Position> {

	private final int accountId;

	// Map<InstrumentId, Position>
	private final MutableIntObjectMap<T> positionMap = MutableMaps.newIntObjectHashMap();

	//
	private final PositionProducer<T> producer;

	public PositionSet(int accountId, PositionProducer<T> producer) {
		this.accountId = accountId;
		this.producer = producer;
	}

	public int getAccountId() {
		return accountId;
	}

	public void putPosition(T position) {
		positionMap.put(position.instrumentId(), position);
	}

	public T getPosition(int instrumentId) {
		return getPosition(instrumentId, 0);
	}

	public T getPosition(int instrumentId, int qty) {
		T position = positionMap.get(instrumentId);
		if (position == null) {
			position = producer.produce(accountId, instrumentId, qty);
			positionMap.put(instrumentId, position);
		}
		return position;
	}

}
