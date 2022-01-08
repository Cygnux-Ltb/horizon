package io.horizon.trader.order;

import static io.horizon.trader.account.SubAccount.ExternalOrderSubAccount;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.MutableList;
import org.slf4j.Logger;

import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.InstrumentKeeper;
import io.horizon.trader.account.Account;
import io.horizon.trader.account.AccountKeeper;
import io.horizon.trader.account.SubAccount;
import io.horizon.trader.order.attr.OrdPrice;
import io.horizon.trader.order.attr.OrdQty;
import io.horizon.trader.order.enums.OrdType;
import io.horizon.trader.order.enums.TrdAction;
import io.horizon.trader.order.enums.TrdDirection;
import io.horizon.trader.transport.outbound.OrderReport;
import io.mercury.common.collections.MutableLists;

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

	/*
	 * 交易动作
	 */

	protected final TrdAction action;

	/*
	 * 经纪商提供的唯一码, 可能有多个, 使用数组实现
	 */
	protected final String[] brokerIdentifier = new String[4];

	/*
	 * 订单成交列表
	 */
	protected final MutableList<TradeRecord> records = MutableLists.newFastList(4);

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
	protected ChildOrder(
			// 订单唯一ID, 策略ID
			long ordSysId, int strategyId,
			// 子账户ID, 账户ID
			int subAccountId, int accountId,
			// 交易标的
			@Nonnull Instrument instrument,
			// 数量, 价格
			@Nonnull OrdQty qty, @Nonnull OrdPrice price,
			// 订单类型
			@Nonnull OrdType type,
			// 交易方向
			@Nonnull TrdDirection direction,
			// 交易动作
			@Nonnull TrdAction action) {
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
	public static ChildOrder newOrder(
			// OrdSysIdAllocator
			@Nonnull OrdSysIdAllocator ordSysIdAllocator,
			// strategyId
			int strategyId,
			// SubAccount, Account
			@Nonnull SubAccount subAccount, @Nonnull Account account,
			// Instrument
			@Nonnull Instrument instrument,
			// offerQty, offerPrice
			final int offerQty, final long offerPrice,
			// OrdType
			@Nonnull final OrdType type,
			// TrdDirection
			@Nonnull final TrdDirection direction,
			// TrdAction
			@Nonnull final TrdAction action) {
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
	 * 
	 * @param report
	 * @return
	 */
	public static ChildOrder newExternalOrder(OrderReport report) {
		Account account = AccountKeeper.getAccountByInvestorId(report.getInvestorId());
		Instrument instrument = InstrumentKeeper.getInstrument(report.getInstrumentCode());
		TrdDirection direction = TrdDirection.valueOf(report.getDirection());
		TrdAction action = TrdAction.valueOf(report.getAction());
		return new ChildOrder(report.getOrdSysId(),
				// -------------------------------
				// 外部订单使用的策略ID
				0,
				// 专用的处理外部来源订单的子账户
				ExternalOrderSubAccount.getSubAccountId(),
				// -------------------------------
				account.getAccountId(), instrument,
				// 以委托数量创建
				OrdQty.withOffer(report.getOfferQty()),
				// 以委托价格创建
				OrdPrice.withOffer(report.getOfferPrice()),
				// -------------------------------
				OrdType.Limited, direction, action);

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
	public static ChildOrder newExternalOrder(
			//
			final long ordSysId,
			//
			final int accountId,
			//
			@Nonnull final Instrument instrument,
			//
			final OrdQty qty,
			//
			final OrdPrice price,
			//
			@Nonnull final TrdDirection direction,
			//
			@Nonnull final TrdAction action) {
		return new ChildOrder(ordSysId,
				// -------------------------------
				// 外部策略
				0,
				// -------------------------------
				ExternalOrderSubAccount.getSubAccountId(),
				// -------------------------------
				accountId, instrument, qty, price,
				// -------------------------------
				OrdType.Limited, direction, action);
	}

	@Override
	public int getOrdLevel() {
		return 0;
	}

	private static final String ChildOrderLogTemplate = "Msg : {}, ChildOrder attr : ordSysId==[{}], "
			+ "status==[{}], direction==[{}], type==[{}], action==[{}], "
			+ "instrument -> {}, qty -> {}, price -> {}, timestamp -> {}, trdRecords -> {}";

	@Override
	public void writeLog(Logger log, String msg) {
		log.info(ChildOrderLogTemplate, msg, ordSysId, status, direction, type, action, instrument, qty, price,
				timestamp, records);
	}

	public TrdAction getAction() {
		return action;
	}

	public String[] getBrokerIdentifier() {
		return brokerIdentifier;
	}

	public MutableList<TradeRecord> getRecords() {
		return records;
	}

	/**
	 * 
	 * @return
	 */
	public TradeRecord getFirstRecord() {
		return records.getFirst();
	}

	/**
	 * 
	 * @return
	 */
	public TradeRecord getLastRecord() {
		return records.getLast();
	}

	/**
	 * 
	 * @param epochMicros
	 * @param tradePrice
	 * @param tradeQty
	 */
	public void addRecord(long epochMicros, long tradePrice, int tradeQty) {
		records.add(new TradeRecord(ordSysId, records.size() + 1, epochMicros, tradePrice, tradeQty));
	}

	/**
	 * 
	 * @return
	 */
	public long fillAndGetAvgTradePrice() {
		return price.calcAvgTradePrice(this).getAvgTradePrice();
	}

}
