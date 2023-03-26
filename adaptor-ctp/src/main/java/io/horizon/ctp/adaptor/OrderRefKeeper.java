package io.horizon.ctp.adaptor;

import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.common.thread.SleepSupport;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.api.map.primitive.MutableObjectLongMap;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static io.horizon.trader.order.OrdSysIdAllocator.ForExternalOrder;
import static io.mercury.common.collections.Capacity.L10_SIZE;
import static io.mercury.common.collections.MutableMaps.newLongObjectHashMap;
import static io.mercury.common.collections.MutableMaps.newObjectLongHashMap;
import static io.mercury.common.datetime.EpochTime.getEpochMillis;
import static io.mercury.common.datetime.TimeZone.CST;
import static java.lang.System.currentTimeMillis;

/**
 * @author yellow013
 * <p>
 * TODO - Add Persistence
 */
public class OrderRefKeeper {

    private static final Logger log = Log4j2LoggerFactory.getLogger(OrderRefKeeper.class);

    private final MutableObjectLongMap<String> orderRefMapper = newObjectLongHashMap(L10_SIZE.value());

    private final MutableLongObjectMap<String> ordSysIdMapper = newLongObjectHashMap(L10_SIZE.value());

    private final static OrderRefKeeper INSTANCE = new OrderRefKeeper();

    private OrderRefKeeper() {
    }

    public static void put(String orderRef, long ordSysId) {
        log.info("CTP orderRef==[{}] mapping to ordSysId==[{}]", orderRef, ordSysId);
        INSTANCE.orderRefMapper.put(orderRef, ordSysId);
        INSTANCE.ordSysIdMapper.put(ordSysId, orderRef);
    }

    /**
     * @param orderRef String
     * @return long
     */
    public static long getOrdSysId(String orderRef) {
        long ordSysId = INSTANCE.orderRefMapper.get(orderRef);
        if (ordSysId == 0L) {
            // 处理其他来源的订单
            ordSysId = ForExternalOrder.getOrdSysId();
            log.warn("Handle external order, allocate external order used ordSysId==[{}], orderRef==[{}]", ordSysId,
                    orderRef);
        }
        return ordSysId;
    }

    /**
     * @param ordSysId long
     * @return String
     */
    public static String getOrderRef(long ordSysId) throws OrderRefNotFoundException {
        String orderRef = INSTANCE.ordSysIdMapper.get(ordSysId);
        if (orderRef == null)
            throw new OrderRefNotFoundException(ordSysId);
        return orderRef;
    }

    /**
     * 以<b> [下午15点15分] </b> 作为计算OrderRef的基准时间
     */
    public static final LocalTime BenchmarkTime = LocalTime.of(15, 15);

    /**
     * 如果当前时间在基准时间之后, 则使用当天的基准时间; 如果在基准时间之前, 则使用前一天的基准时间
     */
    public static final long BenchmarkPoint = getEpochMillis(
            ZonedDateTime.of(LocalTime.now().isBefore(BenchmarkTime)
                            ? LocalDate.now().minusDays(1)
                            : LocalDate.now(),
                    BenchmarkTime, CST));

    /**
     * 基于<b> Epoch时间戳与前一天基准点 </b>的偏移量计算OrderRef, 保证OrderRef在同一个交易日内自增
     *
     * @return int
     */
    public static int nextOrderRef() {
        return (int) (currentTimeMillis() - BenchmarkPoint);
    }

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            System.out.println(nextOrderRef());
            SleepSupport.sleep(2);
        }

        Duration duration = Duration.between(ZonedDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(17, 0), CST),
                ZonedDateTime.of(LocalDate.now(), LocalTime.of(17, 0), CST));
        System.out.println(duration.getSeconds() * 1000);
        System.out.println(Integer.MAX_VALUE);

    }

}
