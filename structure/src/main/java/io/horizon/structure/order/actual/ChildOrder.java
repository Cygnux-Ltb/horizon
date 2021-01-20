package io.horizon.structure.order.actual;

import org.eclipse.collections.api.list.MutableList;

import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.AbstractOrder;
import io.horizon.structure.order.OrdPrice;
import io.horizon.structure.order.OrdQty;
import io.horizon.structure.order.TrdRecord;
import io.horizon.structure.order.enums.OrdType;
import io.horizon.structure.order.enums.TrdAction;
import io.horizon.structure.order.enums.TrdDirection;
import io.mercury.common.collections.MutableLists;
import lombok.Getter;

/**
 * 
 * 实际执行的订单
 * 
 * @author yellow013
 *
 */
public class ActualOrder extends AbstractOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6034876220144503779L;

	// 交易动作
	@Getter
	protected final TrdAction action;

	// 所属上级ordId
	@Getter
	protected final long ownerOrdId;

	// 经纪商提供的唯一码, 可能有多个, 使用数组实现
	@Getter
	protected final String[] brokerIdentifier = new String[4];

	// 订单成交列表
	@Getter
	protected final MutableList<TrdRecord> trdRecords = MutableLists.newFastList(4);

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
	public ActualOrder(long ordId, int strategyId, int subAccountId, int accountId, Instrument instrument, OrdQty qty,
			OrdPrice price, OrdType type, TrdDirection direction, TrdAction action, long ownerOrdId) {
		super(ordId, strategyId, subAccountId, accountId, instrument, qty, price, type, direction);
		this.action = action;
		this.ownerOrdId = ownerOrdId;
	}

	/**
	 * 
	 * @return
	 */
	public TrdRecord getFirstTrdRecord() {
		return trdRecords.getFirst();
	}

	/**
	 * 
	 * @return
	 */
	public TrdRecord getLastTrdRecord() {
		return trdRecords.getLast();
	}

	/**
	 * 
	 * @param epochTime
	 * @param trdPrice
	 * @param trdQty
	 */
	public void addTrdRecord(long timestamp, long trdPrice, int trdQty) {
		trdRecords.add(new TrdRecord(trdRecords.size() + 1, ordId, timestamp, trdPrice, trdQty));
	}

	@Override
	public int getOrdLevel() {
		return 0;
	}

}
