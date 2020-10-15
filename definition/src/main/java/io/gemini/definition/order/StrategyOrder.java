package io.gemini.definition.order;

import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;

import io.gemini.definition.market.instrument.Instrument;
import io.gemini.definition.order.enums.OrdType;
import io.gemini.definition.order.enums.TrdAction;
import io.gemini.definition.order.enums.TrdDirection;
import io.gemini.definition.order.structure.OrdPrice;
import io.gemini.definition.order.structure.OrdQty;
import io.mercury.common.collections.MutableMaps;

/**
 * 
 * 策略在出现交易信号后发出的订单, 可以对应一个或多个实际订单
 * 
 * @author yellow013
 */

@Deprecated
public final class StrategyOrder extends OrderBasicImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1630590012253172782L;

	/**
	 * 所属实际订单
	 */
	private MutableLongObjectMap<ActualParentOrder> ownOrders = MutableMaps.newLongObjectHashMap();

	/**
	 * 
	 * @param strategyId
	 * @param subAccountId
	 * @param accountId
	 * @param instrument
	 * @param ordQty
	 * @param ordPrice
	 * @param ordType
	 * @param direction
	 */
	public StrategyOrder(int strategyId, int subAccountId, int accountId, Instrument instrument, OrdQty ordQty,
			OrdPrice ordPrice, OrdType ordType, TrdDirection direction) {
		super(0L, strategyId, subAccountId, accountId, instrument, ordQty, ordPrice, ordType, direction);
	}

	public MutableLongObjectMap<ActualParentOrder> ownOrders() {
		return ownOrders;
	}

	public ActualParentOrder addOwnOrder(ActualParentOrder order) {
		ownOrders.put(uniqueId(), order);
		return order;
	}

	@Override
	public int ordLevel() {
		return 2;
	}

	@Override
	public long ownerUniqueId() {
		return uniqueId();
	}

	public ActualParentOrder toActualOrder(TrdDirection direction, int offerQty, OrdType ordType) {
		return addOwnOrder(new ActualParentOrder(strategyId(), accountId(), subAccountId(), instrument(), offerQty,
				price().offerPrice(), ordType, direction, TrdAction.Open, uniqueId()));
	}

}
