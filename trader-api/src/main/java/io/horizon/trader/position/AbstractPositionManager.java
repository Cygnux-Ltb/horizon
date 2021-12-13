package io.horizon.trader.position;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;

import io.horizon.trader.account.AccountPosition;
import io.mercury.common.collections.MutableMaps;

@NotThreadSafe
public abstract class AbstractPositionManager<P extends Position> implements PositionManager<P> {

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
