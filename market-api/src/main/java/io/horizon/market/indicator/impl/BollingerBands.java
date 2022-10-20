package io.horizon.market.indicator.impl;

import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.indicator.IndicatorEvent;
import io.horizon.market.indicator.base.FixedPeriodIndicator;
import io.horizon.market.indicator.base.FixedPeriodPoint;
import io.horizon.market.indicator.impl.BollingerBands.BollingerBandsEvent;
import io.horizon.market.indicator.impl.BollingerBands.BollingerBandsPoint;
import io.horizon.market.instrument.Instrument;
import io.mercury.common.sequence.TimeWindow;

import java.time.Duration;

public final class BollingerBands extends
        FixedPeriodIndicator<BollingerBandsPoint, BollingerBandsEvent, BasicMarketData> {

    public BollingerBands(Instrument instrument, Duration duration, int cycle) {
        super(instrument, duration, cycle);
    }

    @Override
    protected void handleMarketData(BasicMarketData marketData) {

    }

    /**
     * @author yellow013
     */
    public interface BollingerBandsEvent extends IndicatorEvent {

        @Override
        default String getEventName() {
            return "BollingerBandsEvent";
        }

    }

    public static final class BollingerBandsPoint extends FixedPeriodPoint<BasicMarketData> {

        private BollingerBandsPoint(int index, TimeWindow timePeriod) {
            super(index, timePeriod);
        }

        @Override
        protected void handleMarketData0(BasicMarketData marketData) {

        }

    }

}
