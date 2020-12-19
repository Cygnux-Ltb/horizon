package io.horizon.definition.order;

import java.io.Serializable;

import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.slf4j.Logger;

import io.horizon.definition.account.Account;
import io.horizon.definition.account.AccountKeeper;
import io.horizon.definition.market.data.impl.BasicMarketData;
import io.horizon.definition.market.instrument.Instrument;
import io.horizon.definition.order.actual.ChildOrder;
import io.horizon.definition.order.actual.ParentOrder;
import io.horizon.definition.order.enums.OrdType;
import io.horizon.definition.order.enums.TrdAction;
import io.horizon.definition.order.enums.TrdDirection;
import io.mercury.common.collections.Capacity;
import io.mercury.common.collections.MutableMaps;
import io.mercury.common.log.CommonLoggerFactory;

/**
 * 统一管理订单<br>
 * OrderKeeper只对订单进行存储<br>
 * 不处理订单<br>
 * 
 * @author yellow013
 */

@NotThreadSafe
public final class OrderKeeper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8581377004396461013L;

	/**
	 * Logger
	 */
	private static final Logger log = CommonLoggerFactory.getLogger(OrderKeeper.class);

	/**
	 * 存储所有的order
	 */
	private static final OrderBook AllOrders = new OrderBook(Capacity.L09_SIZE_512);

	/**
	 * 按照subAccountId分组存储
	 */
	private static final MutableIntObjectMap<OrderBook> SubAccountOrderBooks = MutableMaps.newIntObjectHashMap();

	/**
	 * 按照accountId分组存储
	 */
	private static final MutableIntObjectMap<OrderBook> AccountOrderBooks = MutableMaps.newIntObjectHashMap();

	/**
	 * 按照strategyId分组存储
	 */
	private static final MutableIntObjectMap<OrderBook> StrategyOrderBooks = MutableMaps.newIntObjectHashMap();

	/**
	 * 按照instrumentId分组存储
	 */
	private static final MutableIntObjectMap<OrderBook> InstrumentOrderBooks = MutableMaps.newIntObjectHashMap();

	private OrderKeeper() {
	}

	/**
	 * 新增订单
	 * 
	 * @param order
	 */
	static void putOrder(Order order) {
		int subAccountId = order.subAccountId();
		int accountId = AccountKeeper.getAccountBySubAccountId(subAccountId).accountId();
		AllOrders.putOrder(order);
		getSubAccountOrderBook(subAccountId).putOrder(order);
		getAccountOrderBook(accountId).putOrder(order);
		getStrategyOrderBook(order.strategyId()).putOrder(order);
		getInstrumentOrderBook(order.instrument()).putOrder(order);
	}

	/**
	 * 
	 * @param order
	 */
	private static void updateOrder(Order order) {
		switch (order.status()) {
		case Filled:
		case Canceled:
		case NewRejected:
		case CancelRejected:
			AllOrders.finishOrder(order);
			getSubAccountOrderBook(order.subAccountId()).finishOrder(order);
			getAccountOrderBook(order.accountId()).finishOrder(order);
			getStrategyOrderBook(order.strategyId()).finishOrder(order);
			getInstrumentOrderBook(order.instrument()).finishOrder(order);
			break;
		default:
			log.info("Not need processed, strategyId==[{}], ordId==[{}], status==[{}]", order.strategyId(),
					order.ordId(), order.status());
			break;
		}
	}

	/**
	 * 处理订单回报
	 * 
	 * @param report
	 * @return
	 */
	public static ChildOrder onOrdReport(OrdReport report) {
		log.info("Handle OrdReport, report -> {}", report);
		// 根据订单回报查找所属订单
		Order order = getOrder(report.getOrdId());
		if (order == null) {
			// 处理订单由外部系统发出而收到报单回报的情况
			log.warn("Received other source order, ordId==[{}]", report.getOrdId());
			Account account = AccountKeeper.getAccountByInvestorId(report.getInvestorId());
			order = new ChildOrder(report.getOrdId(), account.accountId(), report.getInstrument(), report.getOfferQty(),
					report.getOfferPrice(), report.getDirection(), report.getAction());
			putOrder(order);
		} else {
			order.writeLog(log, "OrderKeeper", "Search order OK");
		}
		ChildOrder childOrder = (ChildOrder) order;
		// 根据订单回报更新订单状态
		OrderUpdater.updateWithReport(childOrder, report);
		// 更新Keeper内订单
		updateOrder(childOrder);
		return childOrder;
	}

	public static boolean containsOrder(long uniqueId) {
		return AllOrders.containsOrder(uniqueId);
	}

	public static Order getOrder(long uniqueId) {
		return AllOrders.getOrder(uniqueId);
	}

	public static OrderBook getSubAccountOrderBook(int subAccountId) {
		return SubAccountOrderBooks.getIfAbsentPut(subAccountId, OrderBook::new);
	}

	public static OrderBook getAccountOrderBook(int accountId) {
		return AccountOrderBooks.getIfAbsentPut(accountId, OrderBook::new);
	}

	public static OrderBook getStrategyOrderBook(int strategyId) {
		return StrategyOrderBooks.getIfAbsentPut(strategyId, OrderBook::new);
	}

	public static OrderBook getInstrumentOrderBook(Instrument instrument) {
		return InstrumentOrderBooks.getIfAbsentPut(instrument.instrumentId(), OrderBook::new);
	}

	public static void onMarketData(BasicMarketData marketData) {
		// TODO 处理行情
	}

	/**
	 * 创建[ParentOrder], 并存入Keeper
	 * 
	 * @param strategyId
	 * @param accountId
	 * @param subAccountId
	 * @param instrument
	 * @param offerQty
	 * @param offerPrice
	 * @param ordType
	 * @param direction
	 * @param action
	 * @return
	 */
	public static ParentOrder createParentOrder(int strategyId, int accountId, int subAccountId, Instrument instrument,
			int offerQty, long offerPrice, OrdType ordType, TrdDirection direction, TrdAction action) {
		ParentOrder parentOrder = new ParentOrder(strategyId, accountId, subAccountId, instrument, offerQty, offerPrice,
				ordType, direction, action);
		putOrder(parentOrder);
		return parentOrder;
	}

	/**
	 * 将[ParentOrder]转换为[ChildOrder], 并存入Keeper
	 * 
	 * @param parentOrder
	 * @return
	 */
	public static ChildOrder toChildOrder(ParentOrder parentOrder) {
		ChildOrder childOrder = parentOrder.toChildOrder();
		putOrder(childOrder);
		return childOrder;
	}

	/**
	 * 将[ParentOrder]拆分为多个[ChildOrder], 并存入Keeper
	 * 
	 * @param parentOrder
	 * @param count
	 * @return
	 */
	public static MutableList<ChildOrder> splitChildOrder(ParentOrder parentOrder, int count) {
		MutableList<ChildOrder> childOrders = parentOrder.splitChildOrder(order -> {
			
			return null;
		});
		childOrders.each(OrderKeeper::putOrder);
		return childOrders;
	}

	@Override
	public String toString() {
		return "";
	}

}
