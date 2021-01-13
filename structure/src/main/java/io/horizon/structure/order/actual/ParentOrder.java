package io.horizon.structure.order.actual;

import java.util.Collection;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.MutableList;
import org.slf4j.Logger;

import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.OrdIdAllocator;
import io.horizon.structure.order.OrdPrice;
import io.horizon.structure.order.OrdQty;
import io.horizon.structure.order.enums.OrdType;
import io.horizon.structure.order.enums.TrdAction;
import io.horizon.structure.order.enums.TrdDirection;
import io.mercury.common.collections.MutableLists;
import lombok.Getter;

/**
 * 
 * 一个实际需要执行的订单, 在具体执行时可以被拆分为多个子订单
 * 
 * @author yellow013
 * @creation 2018-07-09
 */
@Deprecated
public final class ParentOrder extends ActualOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5096106824571703291L;

	/**
	 * 所属子订单
	 */
	@Getter
	private final MutableList<ChildOrder> childOrders;

	/**
	 * 
	 * @param strategyId   策略Id
	 * @param subAccountId 子账户Id
	 * @param accountId    实际账户Id
	 * @param instrument   交易标的
	 * @param offerQty     委托数量
	 * @param offerPrice   委托价格
	 * @param type         订单类型
	 * @param direction    交易方向
	 * @param action       交易动作
	 * @param ownerOrdId   所属上级订单Id
	 */
	public ParentOrder(int strategyId, int subAccountId, int accountId, Instrument instrument, int offerQty,
			long offerPrice, OrdType type, TrdDirection direction, TrdAction action, long ownerOrdId) {
		super(OrdIdAllocator.allocate(strategyId), strategyId, subAccountId, accountId, instrument,
				OrdQty.withOffer(offerQty), OrdPrice.withOffer(offerPrice), type, direction, action, ownerOrdId);
		this.childOrders = MutableLists.newFastList(8);
	}

	/**
	 * 
	 * @param strategyId   策略Id
	 * @param subAccountId 子账户Id
	 * @param accountId    实际账户Id
	 * @param instrument   交易标的
	 * @param offerQty     委托数量
	 * @param offerPrice   委托价格
	 * @param type         订单类型
	 * @param direction    交易方向
	 * @param action       交易动作
	 */
	public ParentOrder(int strategyId, int subAccountId, int accountId, Instrument instrument, int offerQty,
			long offerPrice, OrdType type, TrdDirection direction, TrdAction action) {
		this(strategyId, subAccountId, accountId, instrument, offerQty, offerPrice, type, direction, action, 0L);
	}

	/**
	 * 
	 * @return ChildOrder
	 */
	public ChildOrder toChildOrder() {
		ChildOrder childOrder = new ChildOrder(getStrategyId(), getSubAccountId(), getAccountId(), getInstrument(),
				getQty().offerQty(), getPrice().offerPrice(), getType(), getDirection(), getAction(), getOrdId());
		childOrders.add(childOrder);
		return childOrder;
	}

	/**
	 * 由外部传入拆分为多个订单的逻辑
	 * 
	 * @param splitFunc
	 * @return
	 */
	public MutableList<ChildOrder> splitChildOrder(@Nonnull Function<ParentOrder, Collection<ChildOrder>> splitFunc) {
		this.childOrders.addAll(splitFunc.apply(this));
		return this.childOrders;
	}

	@Override
	public int getOrdLevel() {
		return 1;
	}

	private static final String ParentOrderTemplate = "{} :: {}, ParentOrder attr : ordId==[{}], ownerOrdId==[{}], "
			+ "status==[{}], direction==[{}], action==[{}], type==[{}], instrument -> {}, "
			+ "price -> {}, qty -> {}, timestamp -> {}";

	@Override
	public void writeLog(Logger log, String objName, String msg) {
		log.info(ParentOrderTemplate, objName, msg, getOrdId(), getOwnerOrdId(), getStatus(), getDirection(),
				getAction(), getType(), getInstrument(), getPrice(), getQty(), getTimestamp());
	}

}
