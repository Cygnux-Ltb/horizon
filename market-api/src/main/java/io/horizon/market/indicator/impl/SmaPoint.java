package io.horizon.market.indicator.impl;

import java.time.Duration;

import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.instrument.Instrument;
import io.mercury.common.collections.window.LongRingWindow;
import io.mercury.common.sequence.TimeWindow;

public final class SmaPoint extends MaPoint {

    private final long historyPriceSum;

    private final int cycle;

    public SmaPoint(int index, Instrument instrument, Duration duration,
                    TimeWindow timePeriod, int cycle, LongRingWindow historyPriceWindow) {
        super(index, instrument, duration, timePeriod, historyPriceWindow);
        this.historyPriceSum = historyPriceWindow.sum();
        this.cycle = cycle;
    }

    public static SmaPoint with(int index, Instrument instrument, Duration duration,
                                TimeWindow timePeriod, int cycle, LongRingWindow historyPriceWindow) {
        return new SmaPoint(index, instrument, duration, timePeriod, cycle, historyPriceWindow);
    }

    public int getCycle() {
        return cycle;
    }

    @Override
    protected void handleMarketData0(BasicMarketData marketData) {
        this.lastPrice = marketData.getLastPrice();
        int count = historyPriceWindow.count();
        this.avgPrice = (historyPriceSum + lastPrice) / count;
    }

    public static void main(String[] args) {

        double d = 1 + 1 + 6 + 10;
        double b = d / 4;
        System.out.println(b);
        double b1 = b + 20;
        System.out.println(b1 / 2);
        System.out.println((1 + 1 + 6 + 10 + 20) / 2);

    }

}
