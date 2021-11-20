package io.horizon.ftdc.adaptor;

import static io.mercury.common.collections.MutableMaps.newLongObjectHashMap;
import static io.mercury.common.collections.MutableMaps.newObjectLongHashMap;

import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.api.map.primitive.MutableObjectLongMap;
import org.slf4j.Logger;

import io.horizon.ftdc.exception.OrderRefNotFoundException;
import io.horizon.trader.order.OrdSysIdAllocator;
import io.mercury.common.collections.Capacity;
import io.mercury.common.log.CommonLoggerFactory;

/**
 * 
 * @author yellow013
 *
 * @TODO - Add Persistence
 */

public class OrderRefKeeper {

	private static final Logger log = CommonLoggerFactory.getLogger(OrderRefKeeper.class);

	private final MutableObjectLongMap<String> mapOfOrdId = newObjectLongHashMap(Capacity.L10_SIZE);

	private final MutableLongObjectMap<String> mapOfOrderRef = newLongObjectHashMap(Capacity.L10_SIZE);

	private final static OrderRefKeeper StaticInstance = new OrderRefKeeper();

	private OrderRefKeeper() {
	}

	public static void put(String orderRef, long ordId) {
		log.info("CTP orderRef==[{}] mapping to System ordId==[{}]", orderRef, ordId);
		StaticInstance.mapOfOrdId.put(orderRef, ordId);
		StaticInstance.mapOfOrderRef.put(ordId, orderRef);
	}

	public static long getOrdSysId(String orderRef) {
		long ordSysId = StaticInstance.mapOfOrdId.get(orderRef);
		if (ordSysId == 0L) {
			// 处理其他来源的订单
			ordSysId = OrdSysIdAllocator.ExternalOrderAllocator.getOrdSysId();
			log.warn("Handle external order, allocate external order used ordSysId==[{}], orderRef==[{}]", ordSysId,
					orderRef);
		}
		return ordSysId;
	}

	public static String getOrderRef(long ordId) throws OrderRefNotFoundException {
		String orderRef = StaticInstance.mapOfOrderRef.get(ordId);
		if (orderRef == null)
			throw new OrderRefNotFoundException(ordId);
		return orderRef;
	}

}
