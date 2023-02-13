package io.horizon.market.handler;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;
import io.horizon.market.data.MarketData;
import io.mercury.common.concurrent.disruptor.RingMulticaster;
import io.mercury.common.concurrent.disruptor.RingMulticaster.Builder;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.common.thread.RunnableComponent;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;

import static io.mercury.common.concurrent.disruptor.CommonWaitStrategy.BusySpin;
import static io.mercury.common.thread.RunnableComponent.StartMode.manual;

public final class MarketDataMulticaster<I, E extends MarketData>
        extends RunnableComponent implements Closeable {

    private static final Logger log = Log4j2LoggerFactory.getLogger(MarketDataMulticaster.class);

    private RingMulticaster<E, I> multicaster;

    private final Builder<E, I> builder;

    public MarketDataMulticaster(String adaptorName,
                                 @Nonnull EventFactory<E> eventFactory,
                                 @Nonnull EventTranslatorOneArg<E, I> translator) {
        this.builder = RingMulticaster
                // 单生产者广播器
                .withSingleProducer(eventFactory,
                        (E event, long sequence, I in) -> {
                            event.updated();
                            translator.translateTo(event, sequence, in);
                        })
                // 设置缓冲区大小
                .size(64)
                // 设置AdaptorName加后缀
                .setName(adaptorName + "-md-multicaster")
                // 设置启动模式
                .setStartMode(manual())
                // 设置使用自旋等待策略
                .setWaitStrategy(BusySpin);
    }

    public void publish(I in) {
        multicaster.publishEvent(in);
    }

    /**
     * @param handler MarketDataHandler<M>
     */
    public void addMarketDataHandler(MarketDataHandler<E> handler) {
        addEventHandler((E event, long sequence, boolean endOfBatch) -> {
            try {
                handler.onMarketData(event);
            } catch (Exception e) {
                log.error("MarketDataHandler throw {}, MarketData -> {}",
                        e.getClass().getSimpleName(), event, e);
            }
        });
    }

    /**
     * @param handler EventHandler<M>
     */
    public void addEventHandler(EventHandler<E> handler) {
        builder.addHandler(handler);
    }

    public void startup() {

    }

    @Override
    public void close() throws IOException {
        super.stop();
    }

    @Override
    protected void start0() {
        multicaster = builder.create();
        multicaster.start();
    }

    @Override
    protected void stop0() {
        multicaster.stop();
    }
}
