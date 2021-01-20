package io.horizon.structure.position;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;

import io.mercury.common.collections.MutableMaps;

public abstract class AbstractPositionManager<P extends Position<P>> implements PositionManager<P> {

	private final MutableIntObjectMap<AccountPosition<P>> accountPositionMap = MutableMaps.newIntObjectHashMap();

	private final PositionProducer<P> producer;

	protected AbstractPositionManager(@Nonnull PositionProducer<P> producer) {
		this.producer = producer;
	}

	@Override
	public AccountPosition<P> getAccountPosition(int accountId) {
		return accountPositionMap.getIfAbsentPut(accountId, () -> {
			return new AccountPosition<>(accountId, producer);
		});
	}

}
