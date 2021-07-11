package io.horizon.trader.order;

import static io.mercury.common.collections.MutableMaps.newIntObjectHashMap;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.slf4j.Logger;

import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.instrument.Instrument;
import io.horizon.trader.account.Account;
import io.horizon.trader.account.AccountKeeper;
import io.horizon.trader.account.SubAccount;
import io.horizon.trader.order.attr.OrdPrice;
import io.horizon.trader.order.attr.OrdQty;
import io.horizon.trader.order.attr.OrdType;
import io.horizon.trader.order.attr.TrdAction;
import io.horizon.trader.order.attr.TrdDirection;
import io.mercury.common.collections.Capacity;
import io.mercury.common.log.CommonLoggerFactory;
import lombok.Getter;

/**
 * 统一管理订单<br>
 * OrderBookManager只对订单进行存储<br>
 * 根据订单状态存储在不同的分区<br>
 * 不对订单进行处理<br>
 * 
 * @author yellow013
 */

@NotThreadSafe
public final class OrderKeeper implements Serializable {

	private static final long serialVersionUID = 8581377004396461013L;

	/*
	 * Logger
	 */
	private static final Logger log = CommonLoggerFactory.getLogger(OrderKeeper.class);

	/*
	 * 存储所有的order
	 */
	@Getter
	private static final OrderBook OrderBook = new OrderBook(Capacity.L09_SIZE);

	/*
	 * 按照subAccountId分组存储
	 */
	@Getter
	private static final MutableIntObjectMap<OrderBook> SubAccountOrderBooks = newIntObjectHashMap();

	/*
	 * 按照accountId分组存储
	 */
	@Getter
	private static final MutableIntObjectMap<OrderBook> AccountOrderBooks = newIntObjectHashMap();

	/*
	 * 按照strategyId分组存储
	 */
	@Getter
	private static final MutableIntObjectMap<OrderBook> StrategyOrderBooks = newIntObjectHashMap();

	/*
	 * 按照instrumentId分组存储
	 */
	@Getter
	private static final MutableIntObjectMap<OrderBook> InstrumentOrderBooks = newIntObjectHashMap();

	private OrderKeeper() {
	}

	/**
	 * 新增订单
	 * 
	 * @param order
	 */
	private static void putOrder(Order order) {
		int subAccountId = order.getSubAccountId();
		int accountId = order.getAccountId();
		OrderBook.putOrder(order);
		getSubAccountOrderBook(subAccountId).putOrder(order);
		getAccountOrderBook(accountId).putOrder(order);
		getStrategyOrderBook(order.getStrategyId()).putOrder(order);
		getInstrumentOrderBook(order.getInstrument()).putOrder(order);
	}

	/**
	 * 
	 * @param order
	 */
	private static void updateOrder(Order order) {
		switch (order.getStatus()) {
		case Filled:
		case Canceled:
		case NewRejected:
		case CancelRejected:
			OrderBook.finishOrder(order);
			getSubAccountOrderBook(order.getSubAccountId()).finishOrder(order);
			getAccountOrderBook(order.getAccountId()).finishOrder(order);
			getStrategyOrderBook(order.getStrategyId()).finishOrder(order);
			getInstrumentOrderBook(order.getInstrument()).finishOrder(order);
			break;
		default:
			log.info("Not need processed, strategyId==[{}], ordSysId==[{}], status==[{}]", order.getStrategyId(),
					order.getOrdSysId(), order.getStatus());
			break;
		}
	}

	/**
	 * 处理订单回报
	 * 
	 * @param report
	 * @return
	 */
	public static ChildOrder handleOrderReport(OrderReport report) {
		log.info("Handle OrdReport, report -> {}", report);
		// 根据订单回报查找所属订单
		Order order = getOrder(report.getOrdSysId());
		if (order == null) {
			// 处理订单由外部系统发出而收到报单回报的情况
			log.warn("Received other source order, ordSysId==[{}]", report.getOrdSysId());
			Account account = AccountKeeper.getAccountByInvestorId(report.getInvestorId());
			// 根据成交回报创建新订单, 放入OrderBook托管
			order = ChildOrder.newExternalOrder(report.getOrdSysId(), account.getAccountId(), report.getInstrument(),
					OrdQty.withOffer(report.getOfferQty()), OrdPrice.withOffer(report.getOfferPrice()),
					report.getDirection(), report.getAction());
			// 新订单放入OrderBook
			putOrder(order);
		} else {
			order.writeLog(log, "OrderBookKeeper :: Search order OK");
		}
		ChildOrder childOrder = (ChildOrder) order;
		// 根据订单回报更新订单状态
		OrderUpdater.updateWithReport(childOrder, report);
		// 更新Keeper内订单
		updateOrder(childOrder);
		return childOrder;
	}

	/**
	 * 
	 * @param ordSysId
	 * @return
	 */
	public static boolean isContainsOrder(long ordSysId) {
		return OrderBook.isContainsOrder(ordSysId);
	}

	/**
	 * 
	 * @param ordSysId
	 * @return
	 */
	@Nullable
	public static Order getOrder(long ordSysId) {
		return OrderBook.getOrder(ordSysId);
	}

	/**
	 * 
	 * @param subAccountId
	 * @return
	 */
	public static OrderBook getSubAccountOrderBook(int subAccountId) {
		return SubAccountOrderBooks.getIfAbsentPut(subAccountId, OrderBook::new);
	}

	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public static OrderBook getAccountOrderBook(int accountId) {
		return AccountOrderBooks.getIfAbsentPut(accountId, OrderBook::new);
	}

	/**
	 * 
	 * @param strategyId
	 * @return
	 */
	public static OrderBook getStrategyOrderBook(int strategyId) {
		return StrategyOrderBooks.getIfAbsentPut(strategyId, OrderBook::new);
	}

	/**
	 * 
	 * @param instrument
	 * @return
	 */
	public static OrderBook getInstrumentOrderBook(Instrument instrument) {
		return InstrumentOrderBooks.getIfAbsentPut(instrument.getInstrumentId(), OrderBook::new);
	}

	public static void onMarketData(BasicMarketData marketData) {
		// TODO 处理行情
	}

	/**
	 * 创建[ParentOrder], 并存入Keeper
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
	public static ChildOrder createAndSaveChildOrder(OrdSysIdAllocator ordSysIdAllocator, int strategyId,
			SubAccount subAccount, Account account, Instrument instrument, int offerQty, long offerPrice, OrdType type,
			TrdDirection direction, TrdAction action) {
		ChildOrder childOrder = ChildOrder.newOrder(ordSysIdAllocator, strategyId, subAccount, account, instrument,
				offerQty, offerPrice, type, direction, action);
		putOrder(childOrder);
		return childOrder;
	}

//	/**
//	 * 将[ParentOrder]转换为[ChildOrder], 并存入Keeper
//	 * 
//	 * @param parentOrder
//	 * @return
//	 */
//	public static ChildOrder toChildOrder(ParentOrder parentOrder) {
//		ChildOrder childOrder = parentOrder.toChildOrder();
//		putOrder(childOrder);
//		return childOrder;
//	}
//
//	/**
//	 * 将[ParentOrder]拆分为多个[ChildOrder], 并存入Keeper
//	 * 
//	 * @param parentOrder
//	 * @param count
//	 * @return
//	 */
//	public static MutableList<ChildOrder> splitChildOrder(ParentOrder parentOrder, int count) {
//		MutableList<ChildOrder> childOrders = parentOrder.splitChildOrder(order -> {
//			// TODO
//			return null;
//		});
//		childOrders.each(OrderBookKeeper::putOrder);
//		return childOrders;
//	}

	@Override
	public String toString() {
		return "";
	}

}
