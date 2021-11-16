package io.horizon.market.handler;

import static io.mercury.common.concurrent.disruptor.CommonWaitStrategy.BusySpin;
import static io.mercury.common.thread.RunnableComponent.StartMode.Manual;

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;

import io.horizon.market.data.MarketData;
import io.mercury.common.concurrent.disruptor.RingMulticaster;
import io.mercury.common.concurrent.disruptor.RingMulticaster.Builder;
import io.mercury.common.log.CommonLoggerFactory;

public final class MarketDataMulticaster<I, M extends MarketData> implements Closeable {

	private static final Logger log = CommonLoggerFactory.getLogger(MarketDataMulticaster.class);

	private RingMulticaster<M, I> multicaster;

	private final Builder<M, I> builder;

	public MarketDataMulticaster(String adaptorName, EventFactory<M> eventFactory,
			@Nonnull EventTranslatorOneArg<M, I> translator) {
		this.builder = RingMulticaster.newBuilder(eventFactory, (M event, long sequence, I in) -> {
			event.updated();
			translator.translateTo(event, sequence, in);
		}).size(64)
				// 设置AdaptorName加后缀
				.name(adaptorName + "-MktMulticaster").setStartMode(Manual)
				// 设置使用自旋等待策略
				.setWaitStrategy(BusySpin);
	}

	public void publish(I in) {
		multicaster.publishEvent(in);
	}

	public void addEventHandler(EventHandler<M> handler) {
		builder.addHandler(handler);
	}

	public void addHandler(MarketDataHandler<M> handler) {
		builder.addHandler((event, sequence, endOfBatch) -> {
			try {
				handler.onMarketData(event);
			} catch (Exception e) {
				log.error("MarketDataHandler throw {}, MarketData -> {}", e.getClass().getSimpleName(), event, e);
			}
		});
	}

	public void startup() {
		multicaster = builder.create();
		multicaster.start();
	}

	@Override
	public void close() throws IOException {
		multicaster.stop();
	}

}
