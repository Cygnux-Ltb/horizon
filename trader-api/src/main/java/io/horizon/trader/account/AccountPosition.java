package io.horizon.trader.account;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;

import io.horizon.market.instrument.Instrument;
import io.horizon.trader.position.Position;
import io.horizon.trader.position.PositionProducer;
import io.mercury.common.collections.MutableMaps;

/**
 * 管理一个实际账户的持仓集合
 * 
 * @author yellow013
 * @creation 2018年5月14日
 * @param <T>
 */
public final class AccountPosition<P extends Position> {

	private final int accountId;

	/*
	 * Map<instrumentId, Position>
	 */
	private final MutableIntObjectMap<P> positionMap = MutableMaps.newIntObjectHashMap();

	/*
	 * 仓位对象生产者
	 */
	private final PositionProducer<P> producer;

	/**
	 * 
	 * @param accountId
	 * @param producer
	 */
	public AccountPosition(int accountId, PositionProducer<P> producer) {
		this.accountId = accountId;
		this.producer = producer;
	}

	public int getAccountId() {
		return accountId;
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
