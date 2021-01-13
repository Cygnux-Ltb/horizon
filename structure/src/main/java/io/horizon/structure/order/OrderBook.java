package io.horizon.structure.order;

import static io.mercury.common.collections.MutableMaps.newLongObjectHashMap;

import javax.annotation.Nullable;

import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;

import io.horizon.structure.order.exception.OrdStatusException;
import io.mercury.common.collections.Capacity;
import lombok.Getter;

/**
 * 用于存储订单的组件
 * 
 * @author yellow013
 *
 */
public final class OrderBook {

	// 存储本OrderBook里的所有订单, 以uniqueId索引
	@Getter
	private final MutableLongObjectMap<Order> orderMap;

	// 存储本OrderBook里的所有long订单, 以uniqueId索引
	@Getter
	private final MutableLongObjectMap<Order> longOrderMap;

	// 存储本OrderBook里的所有short订单, 以uniqueId索引
	@Getter
	private final MutableLongObjectMap<Order> shortOrderMap;

	// 存储本OrderBook里的所有活动状态的订单, 以uniqueId索引
	@Getter
	private final MutableLongObjectMap<Order> activeOrderMap;

	// 存储本OrderBook里的所有活动状态的long订单, 以uniqueId索引
	@Getter
	private final MutableLongObjectMap<Order> activeLongOrderMap;

	// 存储本OrderBook里的所有活动状态的short订单, 以uniqueId索引
	@Getter
	private final MutableLongObjectMap<Order> activeShortOrderMap;

	/**
	 * Use default Capacity.L07_SIZE, Size == 128
	 */
	public OrderBook() {
		this(Capacity.L07_SIZE);
	}

	/**
	 * 
	 * @param capacity
	 */
	public OrderBook(Capacity capacity) {
		this.orderMap = newLongObjectHashMap(capacity);
		this.longOrderMap = newLongObjectHashMap(capacity.half());
		this.shortOrderMap = newLongObjectHashMap(capacity.half());
		this.activeOrderMap = newLongObjectHashMap(capacity.quarter());
		this.activeLongOrderMap = newLongObjectHashMap(capacity.quarter());
		this.activeShortOrderMap = newLongObjectHashMap(capacity.quarter());
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	public Order putOrder(Order order) {
		switch (order.getDirection()) {
		case Long:
			longOrderMap.put(order.getOrdId(), order);
			activeLongOrderMap.put(order.getOrdId(), order);
			break;
		case Short:
			shortOrderMap.put(order.getOrdId(), order);
			activeShortOrderMap.put(order.getOrdId(), order);
			break;
		default:
			throw new IllegalStateException("ordId: [" + order.getOrdId() + "], direction is invalid");
		}
		orderMap.put(order.getOrdId(), order);
		return activeOrderMap.put(order.getOrdId(), order);
	}

	public Order finishOrder(Order order) throws OrdStatusException {
		switch (order.getDirection()) {
		case Long:
			activeLongOrderMap.remove(order.getOrdId());
			break;
		case Short:
			activeShortOrderMap.remove(order.getOrdId());
			break;
		case Invalid:
			throw new OrdStatusException("ordId: [" + order.getOrdId() + "], direction is invalid.");
		}
		return activeOrderMap.remove(order.getOrdId());
	}

	public boolean isContainsOrder(long ordId) {
		return orderMap.containsKey(ordId);
	}

	@Nullable
	public Order getOrder(long ordId) {
		return orderMap.get(ordId);
	}

}
