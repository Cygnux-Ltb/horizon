package io.horizon.definition.order.actual;

import io.horizon.definition.market.instrument.Instrument;
import io.horizon.definition.order.OrderBasicImpl;
import io.horizon.definition.order.enums.OrdType;
import io.horizon.definition.order.enums.TrdAction;
import io.horizon.definition.order.enums.TrdDirection;
import io.horizon.definition.order.structure.OrdPrice;
import io.horizon.definition.order.structure.OrdQty;

abstract class ActualOrder extends OrderBasicImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6034876220144503779L;

	/**
	 * 交易动作
	 */
	private final TrdAction action;

	/**
	 * 所属上级ordId
	 */
	private final long ownerUniqueId;

	/**
	 * 
	 * @param uniqueId
	 * @param strategyId
	 * @param subAccountId
	 * @param accountId
	 * @param instrument
	 * @param qty
	 * @param price
	 * @param type
	 * @param direction
	 * @param action
	 * @param ownerOrdId
	 */
	protected ActualOrder(long uniqueId, int strategyId, int subAccountId, int accountId, Instrument instrument,
			OrdQty qty, OrdPrice price, OrdType type, TrdDirection direction, TrdAction action, long ownerUniqueId) {
		super(uniqueId, strategyId, subAccountId, accountId, instrument, qty, price, type, direction);
		this.action = action;
		this.ownerUniqueId = ownerUniqueId != 0L ? ownerUniqueId : uniqueId();
	}

	@Override
	public long ownerUniqueId() {
		return ownerUniqueId;
	}

	public TrdAction action() {
		return action;
	}

}
