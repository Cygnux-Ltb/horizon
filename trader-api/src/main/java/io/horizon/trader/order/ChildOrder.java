package io.horizon.trader.order;

import static io.horizon.trader.Constant.ExternalOrderStrategyId;
import static io.horizon.trader.account.SubAccount.ExternalOrderSubAccount;
import static io.horizon.trader.order.OrdEnum.OrdType.Limit;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.MutableList;
import org.slf4j.Logger;

import io.horizon.market.instrument.Instrument;
import io.horizon.trader.account.Account;
import io.horizon.trader.account.SubAccount;
import io.horizon.trader.order.OrdEnum.OrdType;
import io.horizon.trader.order.OrdEnum.TrdAction;
import io.horizon.trader.order.OrdEnum.TrdDirection;
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
	 * 订单构造方法
	 * 
	 * @param ordSysId     订单唯一ID
	 * @param strategyId   策略ID
	 * @param subAccountId 子账户ID
	 * @param accountId    账户ID
	 * @param instrument   交易标的
	 * @param qty          数量
	 * @param price        价格
	 * @param type         订单类型
	 * @param direction    交易方向
	 * @param action       交易动作
	 */
	protected ChildOrder(final long ordSysId, final int strategyId, final int subAccountId, final int accountId,
			@Nonnull final Instrument instrument, @Nonnull final OrdQty qty, @Nonnull final OrdPrice price,
			@Nonnull final OrdType type, @Nonnull final TrdDirection direction, @Nonnull TrdAction action) {
		super(ordSysId, strategyId, subAccountId, accountId, instrument, qty, price, type, direction);
		this.action = action;
	}

	/**
	 * 创建新订单
	 * 
	 * @param ordSysIdAllocator
	 * @param strategyId
	 * @param subAccount
	 * @param account
	 * @param instrument
	 * @param offerQty
	 * @param offerPrice
	 * @param type
	 * @param direction
	 * @param action
	 * @return
	 */
	static ChildOrder newOrder(final OrdSysIdAllocator ordSysIdAllocator, final int strategyId,
			@Nonnull final SubAccount subAccount, @Nonnull final Account account, @Nonnull final Instrument instrument,
			final int offerQty, final long offerPrice, @Nonnull final OrdType type,
			@Nonnull final TrdDirection direction, @Nonnull final TrdAction action) {
		return new ChildOrder(
				// 使用strategyId生成ordSysId
				ordSysIdAllocator.getOrdSysId(),
				// --------------------------
				strategyId, subAccount.getSubAccountId(), account.getAccountId(), instrument,
				// 设置委托数量
				OrdQty.withOffer(offerQty),
				// 设置委托价格
				OrdPrice.withOffer(offerPrice),
				// --------------------------
				type, direction, action);
	}

	/**
	 * 用于构建外部来源的新订单, 通常是根据系统未托管的订单回报构建, 此时需要传递订单当前状态
	 * 
	 * @param ordSysId   外部传入的ordSysId, 用于处理非系统订单
	 * @param accountId  实际账户ID
	 * @param instrument 交易标的
	 * @param qty        委托数量
	 * @param price      委托价格
	 * @param direction  交易方向
	 * @param action     交易动作
	 * @return
	 */
	static ChildOrder newExternalOrder(final long ordSysId, final int accountId, @Nonnull final Instrument instrument,
			final OrdQty qty, final OrdPrice price, @Nonnull final TrdDirection direction,
			@Nonnull final TrdAction action) {
		return new ChildOrder(ordSysId,
				// -------------------------------
				ExternalOrderStrategyId,
				// -------------------------------
				ExternalOrderSubAccount.getSubAccountId(),
				// -------------------------------
				accountId, instrument, qty, price,
				// -------------------------------
				Limit, direction, action);
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
		trdRecords.add(new TrdRecord(ordSysId, trdRecords.size() + 1, timestamp, trdPrice, trdQty));
	}

	/**
	 * 
	 * @return
	 */
	public long fillAndGetAvgTradePrice() {
		return price.calculateAvgTradePrice(this).getAvgTradePrice();
	}

}
