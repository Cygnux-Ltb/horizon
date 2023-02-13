package io.horizon.trader.strategy;

import io.horizon.market.data.MarketData;
import io.horizon.market.handler.MarketDataHandler;
import io.horizon.market.instrument.Instrument;
import io.horizon.trader.account.Account;
import io.horizon.trader.account.SubAccount;
import io.horizon.trader.adaptor.Adaptor;
import io.horizon.trader.handler.AdaptorReportHandler;
import io.horizon.trader.handler.OrderHandler;
import io.mercury.common.fsm.Enableable;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.util.function.Supplier;

public interface Strategy<M extends MarketData> extends
        // 用于控制可用状态
        Enableable,
        // 用于确定优先级
        Comparable<Strategy<M>>,
        // 集成行情处理
        MarketDataHandler<M>,
        // 集成订单处理
        OrderHandler,
        // 集成AdaptorReport处理
        AdaptorReportHandler,
        // 用于资源清理
        Closeable {

    /**
     * 系统可允许的最大策略ID
     */
    int MAX_STRATEGY_ID = 1023;

    int getStrategyId();

    String getStrategyName();

    SubAccount getSubAccount();

    Account getAccount();

    ImmutableIntObjectMap<Instrument> getInstruments();

    Strategy<M> initialize(@Nonnull Supplier<Boolean> initializer);

    Strategy<M> addAdaptor(@Nonnull Adaptor adaptor);

    void onStrategyEvent(@Nonnull StrategyEvent event);

    void onThrowable(Throwable throwable) throws StrategyException;

    @Override
    default int compareTo(Strategy<M> o) {
        return Integer.compare(this.getStrategyId(), o.getStrategyId());
    }

}
