package io.horizon.structure.event;

import static io.mercury.common.collections.ImmutableLists.newImmutableList;

import java.io.Closeable;

import org.eclipse.collections.api.list.ImmutableList;
import org.slf4j.Logger;

import io.horizon.structure.adaptor.Adaptor;
import io.horizon.structure.adaptor.AdaptorEvent;
import io.horizon.structure.event.handler.AdaptorEventHandler;
import io.horizon.structure.event.handler.MarketDataHandler;
import io.horizon.structure.market.data.MarketData;
import io.horizon.structure.market.instrument.Instrument;
import io.mercury.common.log.CommonLoggerFactory;

public interface MarketDataRecorder<M extends MarketData> extends MarketDataHandler<M>, AdaptorEventHandler, Closeable {

	MarketDataRecorder<M> addAdaptor(Adaptor adaptor);

	/**
	 * 
	 * MarketDataRecorder base implements
	 * 
	 * @author yellow013
	 *
	 * @param <M>
	 */

	public static abstract class BaseMarketDataRecorder<M extends MarketData> implements MarketDataRecorder<M> {

		private static final Logger log = CommonLoggerFactory.getLogger(BaseMarketDataRecorder.class);

		protected final ImmutableList<Instrument> instruments;

		protected Adaptor adaptor;

		protected BaseMarketDataRecorder(Instrument... instruments) {
			this.instruments = newImmutableList(instruments);
		}

		@Override
		public void onAdaptorEvent(AdaptorEvent event) {
			log.info("Received event -> {}", event);
			switch (event.getStatus()) {
			case MdEnable:
				if (adaptor != null) {
					Instrument[] subscribe = new Instrument[instruments.size()];
					adaptor.subscribeMarketData(instruments.toArray(subscribe));
				} else {
					throw new IllegalStateException("adaptor is null");
				}
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
