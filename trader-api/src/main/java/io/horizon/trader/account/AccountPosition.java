package io.horizon.trader.position;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;

import io.horizon.market.instrument.Instrument;
import io.mercury.common.collections.MutableMaps;
import lombok.Getter;

/**
 * 管理一个实际账户的持仓集合
 * 
 * @author yellow013
 * @creation 2018年5月14日
 * @param <T>
 */
public final class AccountPosition<P extends Position> {

	@Getter
	private final int accountId;

	// Map<instrumentId, Position>
	private final MutableIntObjectMap<P> positionMap = MutableMaps.newIntObjectHashMap();

	// 仓位对象生产者
	private final PositionProducer<P> producer;

	AccountPosition(int accountId, PositionProducer<P> producer) {
		this.accountId = accountId;
		this.producer = producer;
	}

	/**
	 * 为指定Instrument分配仓位对象
	 * 
	 * @param instrumentId
	 * @param initQty
	 * @return
	 */
	@Nonnull
	public P acquirePosition(Instrument instrument) {
		return positionMap.getIfAbsentPut(instrument.getInstrumentId(), () -> {
			return producer.produce(accountId, instrument);
		});
	}

}
