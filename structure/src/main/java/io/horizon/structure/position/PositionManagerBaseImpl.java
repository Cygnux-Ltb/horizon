package io.horizon.structure.position;

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;

import io.mercury.common.collections.MutableMaps;

public abstract class PositionManagerBaseImpl<T extends Position> implements PositionManager<T> {

	private final MutableIntObjectMap<PositionSet<T>> positionSetMap = MutableMaps.newIntObjectHashMap();

	private final PositionProducer<T> producer;

	protected PositionManagerBaseImpl(PositionProducer<T> producer) {
		this.producer = producer;
	}

	@Override
	public void putPosition(T position) {
		getPositionSet(position.accountId()).putPosition(position);
	}

	@Override
	public T getPosition(int accountId, int instrumentId) {
		return getPositionSet(accountId).getPosition(instrumentId);
	}

	private PositionSet<T> getPositionSet(int accountId) {
		PositionSet<T> positionSet = positionSetMap.get(accountId);
		if (positionSet == null) {
			positionSet = new PositionSet<>(accountId, producer);
			positionSetMap.put(accountId, positionSet);
		}
		return positionSet;
	}

}
