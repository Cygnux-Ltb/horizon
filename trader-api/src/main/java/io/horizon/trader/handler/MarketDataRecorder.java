package io.horizon.trader.handler;

import static io.mercury.common.collections.ImmutableLists.newImmutableList;

import java.io.Closeable;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.ImmutableList;
import org.slf4j.Logger;

import io.horizon.market.data.MarketData;
import io.horizon.market.handler.MarketDataHandler;
import io.horizon.market.instrument.Instrument;
import io.horizon.trader.adaptor.Adaptor;
import io.horizon.trader.adaptor.AdaptorEvent;
import io.mercury.common.log.CommonLoggerFactory;

/**
 * 
 * 行情记录器接口
 * 
 * @author yellow013
 *
 * @param <M>
 */
public interface MarketDataRecorder<M extends MarketData> extends MarketDataHandler<M>, AdaptorEventHandler, Closeable {

	MarketDataRecorder<M> addAdaptor(@Nonnull final Adaptor adaptor);

	/**
	 * 
	 * MarketDataRecorder base implements
	 * 
	 * @author yellow013
	 *
	 * @param <M>
	 */
	public static abstract class AbstractMarketDataRecorder<M extends MarketData> implements MarketDataRecorder<M> {

		private static final Logger log = CommonLoggerFactory.getLogger(AbstractMarketDataRecorder.class);

		protected final ImmutableList<Instrument> instruments;

		protected Adaptor adaptor;

		protected AbstractMarketDataRecorder(Instrument... instruments) {
			this.instruments = newImmutableList(instruments);
		}

		@Override
		public void onAdaptorEvent(AdaptorEvent event) {
			log.info("Received event -> {}", event);
			switch (event.getStatus()) {
			case MdEnable:
				if (adaptor != null) {
					adaptor.subscribeMarketData(instruments.toArray(new Instrument[instruments.size()]));
				} else {
					throw new IllegalStateException("adaptor is null");
				}
				break;
			case MdDisable:
				log.info("");
				break;
			default:
				log.warn("Event no processing, AdaptorEvent -> {}", event);
				break;
			}
		}

		@Override
		public MarketDataRecorder<M> addAdaptor(Adaptor adaptor) {
			this.adaptor = adaptor;
			return this;
		}

	}

}
