package io.horizon.structure.order.actual;

import org.eclipse.collections.api.list.MutableList;
import org.slf4j.Logger;

import io.horizon.structure.account.SubAccount;
import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.OrdIdAllocator;
import io.horizon.structure.order.OrdPrice;
import io.horizon.structure.order.OrdQty;
import io.horizon.structure.order.TrdRecord;
import io.horizon.structure.order.enums.OrdType;
import io.horizon.structure.order.enums.TrdAction;
import io.horizon.structure.order.enums.TrdDirection;
import io.horizon.structure.strategy.StrategyIdConst;
import io.mercury.common.collections.MutableLists;
import lombok.Getter;

/**
 * 实际执行订单的最小执行单元, 可能根据合规, 账户情况等由ParentOrder拆分出多个ChildOrder
 * 
 * @author yellow013
 * @creation 2018年1月14日
 */
public final class ChildOrder extends ActualOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3863592977001402228L;

	// 经纪商提供的唯一码, 可能有多个, 使用数组实现
	@Getter
	private final String[] brokerIdentifier = new String[4];

	// 订单成交列表
	@Getter
	private final MutableList<TrdRecord> trdRecords = MutableLists.newFastList(8);

	/**
	 * 
	 * @param strategyId   策略Id
	 * @param subAccountId 子账户Id
	 * @param accountId    实际账户Id
	 * @param instrument   交易标的
	 * @param ordQty       委托数量
	 * @param ordPrice     委托价格
	 * @param ordType      订单类型
	 * @param direction    交易方向
	 * @param action       交易动作
	 * @param ownerOrdId   所属上级订单
	 */
	public ChildOrder(int strategyId, int subAccountId, int accountId, Instrument instrument, int offerQty,
			long offerPrice, OrdType type, TrdDirection direction, TrdAction action, long ownerOrdId) {
		super(OrdIdAllocator.allocate(strategyId), strategyId, subAccountId, accountId, instrument,
				OrdQty.withOffer(offerQty), OrdPrice.withOffer(offerPrice), type, direction, action, ownerOrdId);
	}

	/**
	 * 
	 * 用于构建外部来源的订单
	 * 
	 * @param uniqueId   外部传入的uniqueId, 用于处理非系统订单
	 * @param accountId  实际账户Id
	 * @param instrument 交易标的
	 * @param ordQty     委托数量
	 * @param ordPrice   委托价格
	 * @param ordType    订单类型
	 * @param direction  交易方向
	 * @param action     交易动作
	 */
	public ChildOrder(long uniqueId, int accountId, Instrument instrument, int offerQty, long offerPrice,
			TrdDirection direction, TrdAction action) {
		super(uniqueId, StrategyIdConst.ProcessExternalOrderStrategyId,
				SubAccount.ProcessExternalOrderSubAccount.getSubAccountId(), accountId, instrument,
				OrdQty.withOffer(offerQty), OrdPrice.withOffer(offerPrice), OrdType.Limit, direction, action, 0L);
	}

	@Override
	public int getOrdLevel() {
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public String[] brokerIdentifier() {
		return brokerIdentifier;
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

	private int serial = -1;

	/**
	 * 
	 * @param epochTime
	 * @param trdPrice
	 * @param trdQty
	 */
	public void addTrdRecord(long epochTime, long trdPrice, int trdQty) {
		trdRecords.add(new TrdRecord(++serial, getOrdId(), epochTime, trdPrice, trdQty));
	}

	private static final String ChildOrderTemplate = "{} :: {}, ChildOrder attr : ordId==[{}], ownerOrdId==[{}], "
			+ "status==[{}], direction==[{}], action==[{}], type==[{}], instrument -> {}, price -> {}, "
			+ "qty -> {}, timestamp -> {}, trdRecords -> {}";

	@Override
	public void writeLog(Logger log, String objName, String msg) {
		log.info(ChildOrderTemplate, objName, msg, getOrdId(), getOwnerOrdId(), getStatus(), getDirection(),
				getAction(), getType(), getInstrument(), getPrice(), getQty(), getTimestamp(), trdRecords);
	}

}
