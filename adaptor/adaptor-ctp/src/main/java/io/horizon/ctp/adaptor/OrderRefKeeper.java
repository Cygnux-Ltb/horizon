package io.horizon.ctp.adaptor;

import static io.mercury.common.collections.MutableMaps.newLongObjectHashMap;
import static io.mercury.common.collections.MutableMaps.newObjectLongHashMap;

import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.api.map.primitive.MutableObjectLongMap;
import org.slf4j.Logger;

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

	private final MutableObjectLongMap<String> orderRefMapper = newObjectLongHashMap(Capacity.L10_SIZE);

	private final MutableLongObjectMap<String> ordSysIdMapper = newLongObjectHashMap(Capacity.L10_SIZE);

	private final static OrderRefKeeper Instance = new OrderRefKeeper();

	private OrderRefKeeper() {
	}

	public static void put(String orderRef, long ordSysId) {
		log.info("CTP orderRef==[{}] mapping to ordSysId==[{}]", orderRef, ordSysId);
		Instance.orderRefMapper.put(orderRef, ordSysId);
		Instance.ordSysIdMapper.put(ordSysId, orderRef);
	}

	public static long getOrdSysId(String orderRef) {
		long ordSysId = Instance.orderRefMapper.get(orderRef);
		if (ordSysId == 0L) {
			// 处理其他来源的订单
			ordSysId = OrdSysIdAllocator.ForExternalOrder.getOrdSysId();
			log.warn("Handle external order, allocate external order used ordSysId==[{}], orderRef==[{}]", ordSysId,
					orderRef);
		}
		return ordSysId;
	}

	public static String getOrderRef(long ordSysId) throws OrderRefNotFoundException {
		String orderRef = Instance.ordSysIdMapper.get(ordSysId);
		if (orderRef == null)
			throw new OrderRefNotFoundException(ordSysId);
		return orderRef;
	}

}
