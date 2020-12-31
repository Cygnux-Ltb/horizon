package io.horizon.structure.order.actual;

import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.OrdPrice;
import io.horizon.structure.order.OrdQty;
import io.horizon.structure.order.OrderBasicImpl;
import io.horizon.structure.order.enums.OrdType;
import io.horizon.structure.order.enums.TrdAction;
import io.horizon.structure.order.enums.TrdDirection;
import lombok.Getter;

public abstract class ActualOrder extends OrderBasicImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6034876220144503779L;

	// 交易动作
	@Getter
	private final TrdAction action;

	// 所属上级ordId
	@Getter
	private final long ownerOrdId;

	/**
	 * 
	 * @param ordId
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
	protected ActualOrder(long ordId, int strategyId, int subAccountId, int accountId, Instrument instrument,
			OrdQty qty, OrdPrice price, OrdType type, TrdDirection direction, TrdAction action, long ownerOrdId) {
		super(ordId, strategyId, subAccountId, accountId, instrument, qty, price, type, direction);
		this.action = action;
		this.ownerOrdId = ownerOrdId;
	}

}
