package io.horizon.structure.order.actual;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.MutableList;
import org.slf4j.Logger;

import io.horizon.structure.account.SubAccount;
import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.AbstractOrder;
import io.horizon.structure.order.TrdRecord;
import io.horizon.structure.order.enums.OrdType;
import io.horizon.structure.order.enums.TrdAction;
import io.horizon.structure.order.enums.TrdDirection;
import io.mercury.common.collections.MutableLists;
import lombok.Getter;

/**
 *
 * 实际执行订单的最小执行单元, 不可再进行拆分, 可能根据合规, 账户情况等由ParentOrder拆分出多个ChildOrder
 * 
 * @author yellow013
 * @creation 2018-01-14
 */
public class ChildOrder extends AbstractOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6034876220144503779L;

	// 交易动作
	@Getter
	protected final TrdAction action;

	// 经纪商提供的唯一码, 可能有多个, 使用数组实现
	@Getter
	protected final String[] brokerIdentifier = new String[4];

	// 订单成交列表
	@Getter
	protected final MutableList<TrdRecord> trdRecords = MutableLists.newFastList(4);

	/**
	 * 创建新订单
	 * 
	 * @param strategyId
	 * @param subAccountId
	 * @param accountId
	 * @param instrument
	 * @param offerQty
	 * @param offerPrice
	 * @param type
	 * @param direction
	 * @param action
	 * @param parentOrdSysId
	 * @return
	 */
	public static ChildOrder newOrder(int strategyId, int subAccountId, int accountId, @Nonnull Instrument instrument,
			int offerQty, long offerPrice, @Nonnull OrdType type, @Nonnull TrdDirection direction,
			@Nonnull TrdAction action) {
		return new ChildOrder(
				// 使用strategyId生成ordSysId
				OrdSysIdAllocator.allocate(strategyId),
				// --------------------------
				strategyId, subAccountId, accountId, instrument,
				// 设置委托数量
				OrdQty.withOffer(offerQty),
				// 设置委托价格
				OrdPrice.withOffer(offerPrice),
				// --------------------------
				type, direction, action);
	}

	/**
	 * 
	 * 用于构建外部来源的新订单, 通常是根据系统未托管的订单回报构建, 此时需要传递订单当前状态
	 * 
	 * @param ordSysId   外部传入的ordSysId, 用于处理非系统订单
	 * @param accountId  实际账户ID
	 * @param instrument 交易标的
	 * @param ordQty     委托数量
	 * @param ordPrice   委托价格
	 * @param ordType    订单类型
	 * @param direction  交易方向
	 * @param action     交易动作
	 * @param status     当前状态
	 */

	public static ChildOrder newExternalOrder(long ordSysId, int accountId, Instrument instrument, OrdQty qty,
			OrdPrice price, TrdDirection direction, TrdAction action) {
		return new ChildOrder(ordSysId,
				//
				Constant.ProcessExternalOrderStrategyId,
				//
				SubAccount.ProcessExternalOrderSubAccount.getSubAccountId(),
				// -------------------------------
				accountId, instrument, qty, price,
				// -------------------------------
				OrdType.Limit, direction, action);
	}

	/**
	 * 订单构造方法
	 * 
	 * @param ordSysId       订单唯一ID
	 * @param strategyId     策略ID
	 * @param subAccountId   子账户ID
	 * @param accountId      账户ID
	 * @param instrument     交易标的
	 * @param qty            数量
	 * @param price          价格
	 * @param type           订单类型
	 * @param direction      交易方向
	 * @param action         交易动作
	 * @param parentOrdSysId 所属上级订单
	 */
	protected ChildOrder(long ordSysId, int strategyId, int subAccountId, int accountId, @Nonnull Instrument instrument,
			@Nonnull OrdQty qty, @Nonnull OrdPrice price, @Nonnull OrdType type, @Nonnull TrdDirection direction,
			@Nonnull TrdAction action) {
		super(ordSysId, strategyId, subAccountId, accountId, instrument, qty, price, type, direction);
		this.action = action;
	}

	@Override
	public int getOrdLevel() {
		return 0;
	}

	private static final String ChildOrderTemplate = "{}, ChildOrder attr : ordSysId==[{}], "
			+ "status==[{}], direction==[{}], type==[{}], action==[{}], "
			+ "instrument -> {}, qty -> {}, price -> {}, timestamp -> {}, trdRecords -> {}";

	@Override
	public void writeLog(Logger log, String msg) {
		log.info(ChildOrderTemplate, msg, ordSysId, status, direction, type, action, instrument, qty, price, timestamp,
				trdRecords);
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
		trdRecords.add(new TrdRecord(trdRecords.size() + 1, ordSysId, timestamp, trdPrice, trdQty));
	}

	/**
	 * 
	 * @return
	 */
	public long fillAndGetAvgTradePrice() {
		return price.calculateAvgTradePrice(this).getAvgTradePrice();
	}

}
