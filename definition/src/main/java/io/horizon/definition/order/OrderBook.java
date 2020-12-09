package io.horizon.definition.order;

import static io.mercury.common.collections.MutableMaps.newLongObjectHashMap;

import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;

import io.horizon.definition.order.exception.OrdStatusException;
import io.mercury.common.collections.Capacity;

/**
 * 用于存储订单的组件
 * 
 * @author yellow013
 *
 */
public final class OrderBook {

	/**
	 * 存储本OrderBook里的所有订单,以uniqueId索引
	 */
	private MutableLongObjectMap<Order> orderMap;

	/**
	 * 存储本OrderBook里的所有long订单,以uniqueId索引
	 */
	private MutableLongObjectMap<Order> longOrderMap;

	/**
	 * 存储本OrderBook里的所有short订单,以uniqueId索引
	 */
	private MutableLongObjectMap<Order> shortOrderMap;

	/**
	 * 存储本OrderBook里的所有活动状态的订单,以uniqueId索引
	 */
	private MutableLongObjectMap<Order> activeOrderMap;

	/**
	 * 存储本OrderBook里的所有活动状态的long订单,以uniqueId索引
	 */
	private MutableLongObjectMap<Order> activeLongOrderMap;

	/**
	 * 存储本OrderBook里的所有活动状态的short订单,以uniqueId索引
	 */
	private MutableLongObjectMap<Order> activeShortOrderMap;

	/**
	 * 
	 */
	public OrderBook() {
		this(Capacity.L08_SIZE_256);
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
		switch (order.direction()) {
		case Long:
			longOrderMap.put(order.ordId(), order);
			activeLongOrderMap.put(order.ordId(), order);
			break;
		case Short:
			shortOrderMap.put(order.ordId(), order);
			activeShortOrderMap.put(order.ordId(), order);
			break;
		default:
			throw new IllegalStateException("uniqueId: [" + order.ordId() + "], direction is invalid");
		}
		orderMap.put(order.ordId(), order);
		return activeOrderMap.put(order.ordId(), order);
	}

	public Order finishOrder(Order order) throws OrdStatusException {
		switch (order.direction()) {
		case Long:
			activeLongOrderMap.remove(order.ordId());
			break;
		case Short:
			activeShortOrderMap.remove(order.ordId());
			break;
		case Invalid:
			throw new OrdStatusException("ordId: [" + order.ordId() + "], direction is invalid.");
		}
		return activeOrderMap.remove(order.ordId());
	}

	public boolean containsOrder(long uniqueId) {
		return orderMap.containsKey(uniqueId);
	}

	public Order getOrder(long uniqueId) {
		return orderMap.get(uniqueId);
	}

	public MutableLongObjectMap<Order> orderMap() {
		return orderMap;
	}

	public MutableLongObjectMap<Order> activeOrderMap() {
		return activeOrderMap;
	}

	public MutableLongObjectMap<Order> longOrderMap() {
		return longOrderMap;
	}

	public MutableLongObjectMap<Order> activeLongOrderMap() {
		return activeLongOrderMap;
	}

	public MutableLongObjectMap<Order> shortOrderMap() {
		return shortOrderMap;
	}

	public MutableLongObjectMap<Order> activeShortOrderMap() {
		return activeShortOrderMap;
	}

}
