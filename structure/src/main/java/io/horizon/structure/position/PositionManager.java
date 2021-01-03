package io.horizon.structure.position;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;

import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.actual.ChildOrder;
import io.mercury.common.collections.MutableMaps;

/**
 * 
 * 持仓管理接口
 * 
 * @author yellow013
 *
 * @param <P>
 * @param <I>
 */
public interface PositionManager<P extends Position<P>> {

	/**
	 * 获取实际账户持仓集合
	 * 
	 * @param accountId
	 * @return
	 */
	AccountPosition<P> getAccountPosition(int accountId);

	/**
	 * 为实际账户和instrument分配初始持仓
	 * 
	 * @param accountId
	 * @param instrumentId
	 * @return
	 */
	default P acquirePosition(int accountId, Instrument instrument) {
		return getAccountPosition(accountId).acquirePosition(instrument);
	}

	/**
	 * 获取指定实际账户和instrument的持仓, 并以订单回报更新持仓
	 * 
	 * @param accountId
	 * @param instrumentId
	 * @param order
	 */
	default void onChildOrder(int accountId, Instrument instrument, ChildOrder order) {
		acquirePosition(accountId, instrument).updateWithOrder(order);
	}

	public static abstract class PositionManagerBaseImpl<P extends Position<P>> implements PositionManager<P> {

		private final MutableIntObjectMap<AccountPosition<P>> accountPositionMap = MutableMaps.newIntObjectHashMap();

		private final PositionProducer<P> producer;

		protected PositionManagerBaseImpl(@Nonnull PositionProducer<P> producer) {
			this.producer = producer;
		}

		@Override
		public AccountPosition<P> getAccountPosition(int accountId) {
			return accountPositionMap.getIfAbsentPut(accountId, () -> {
				return new AccountPosition<>(accountId, producer);
			});
		}

	}

}
