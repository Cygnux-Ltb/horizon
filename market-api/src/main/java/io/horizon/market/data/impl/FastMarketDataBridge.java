package io.horizon.market.data.impl;

import io.horizon.market.data.MarketData;
import io.horizon.market.instrument.Instrument;
import io.horizon.market.transport.outbound.FastMarketData;
import io.mercury.common.datetime.Timestamp;

public final class FastMarketDataBridge implements MarketData {

    private final FastMarketData marketData;

    private final double[] bidPrices = new double[5];
    private final int[] bidVolumes = new int[5];
    private final double[] askPrices = new double[5];
    private final int[] askVolumes = new int[5];

    private FastMarketDataBridge() {
        this.marketData = FastMarketData
                // call -> new builder
                .newBuilder()
                // set -> timestamp, instrumentId, instrumentCode
                .setTimestamp(0L).setInstrumentId(0).setInstrumentCode("")
                // set -> last price, volume, turnover
                .setLastPrice(0L).setVolume(0).setTurnover(0L)
                // set -> level5 bid prices
                .setBidPrices1(0L).setBidPrices2(0L).setBidPrices3(0L).setBidPrices4(0L).setBidPrices5(0L)
                // set -> level5 bid volumes
                .setBidVolumes1(0).setBidVolumes2(0).setBidVolumes3(0).setBidVolumes4(0).setBidVolumes5(0)
                // set -> level5 ask prices
                .setAskPrices1(0L).setAskPrices2(0L).setAskPrices3(0L).setAskPrices4(0L).setAskPrices5(0L)
                // set -> level5 ask volumes
                .setAskVolumes1(0).setAskVolumes2(0).setAskVolumes3(0).setAskVolumes4(0).setAskVolumes5(0)
                // call -> build
                .build();
    }

    /**
     * @return FastMarketDataBridge
     */
    public static FastMarketDataBridge newInstance() {
        return new FastMarketDataBridge();
    }

    public FastMarketData getFastMarketData() {
        return marketData;
    }

    public FastMarketDataBridge setInstrument(Instrument instrument) {
        this.marketData.setInstrumentId(instrument.getInstrumentId());
        this.marketData.setInstrumentCode(instrument.getInstrumentCode());
        return this;
    }

    @Override
    public int getInstrumentId() {
        return marketData.getInstrumentId();
    }

    @Override
    public String getInstrumentCode() {
        return marketData.getInstrumentCode();
    }

    @Override
    public long getEpochMillis() {
        return marketData.getTimestamp();
    }

    @Override
    public Timestamp getTimestamp() {
        return Timestamp.withEpochMillis(marketData.getTimestamp());
    }

    @Override
    public double getLastPrice() {
        return marketData.getLastPrice();
    }

    @Override
    public int getVolume() {
        return marketData.getVolume();
    }

    @Override
    public long getTurnover() {
        return marketData.getTurnover();
    }

    @Override
    public int getDepth() {
        return 5;
    }

    @Override
    public double[] getBidPrices() {
        return bidPrices;
    }

    @Override
    public double getBidPrice1() {
        return marketData.getBidPrices1();
    }

    @Override
    public double getBidPrice2() {
        return marketData.getBidPrices2();
    }

    @Override
    public double getBidPrice3() {
        return marketData.getBidPrices3();
    }

    @Override
    public double getBidPrice4() {
        return marketData.getBidPrices4();
    }

    @Override
    public double getBidPrice5() {
        return marketData.getBidPrices5();
    }

    @Override
    public int[] getBidVolumes() {
        return bidVolumes;
    }

    @Override
    public int getBidVolume1() {
        return marketData.getBidVolumes1();
    }

    @Override
    public int getBidVolume2() {
        return marketData.getBidVolumes2();
    }

    @Override
    public int getBidVolume3() {
        return marketData.getBidVolumes3();
    }

    @Override
    public int getBidVolume4() {
        return marketData.getBidVolumes4();
    }

    @Override
    public int getBidVolume5() {
        return marketData.getBidVolumes5();
    }

    @Override
    public double[] getAskPrices() {
        return askPrices;
    }

    @Override
    public double getAskPrice1() {
        return marketData.getAskPrices1();
    }

    @Override
    public double getAskPrice2() {
        return marketData.getAskPrices2();
    }

    @Override
    public double getAskPrice3() {
        return marketData.getAskPrices3();
    }

    @Override
    public double getAskPrice4() {
        return marketData.getAskPrices4();
    }

    @Override
    public double getAskPrice5() {
        return marketData.getAskPrices5();
    }

    @Override
    public int[] getAskVolumes() {
        return askVolumes;
    }

    @Override
    public int getAskVolume1() {
        return marketData.getAskVolumes1();

    }

    @Override
    public int getAskVolume2() {
        return marketData.getAskVolumes2();
    }

    @Override
    public int getAskVolume3() {
        return marketData.getAskVolumes3();
    }

    @Override
    public int getAskVolume4() {
        return marketData.getAskVolumes4();
    }

    @Override
    public int getAskVolume5() {
        return marketData.getAskVolumes5();
    }

    @Override
    public void updated() {
        this.bidPrices[0] = marketData.getBidPrices1();
        this.bidPrices[1] = marketData.getBidPrices2();
        this.bidPrices[2] = marketData.getBidPrices3();
        this.bidPrices[3] = marketData.getBidPrices4();
        this.bidPrices[4] = marketData.getBidPrices5();
        this.bidVolumes[0] = marketData.getBidVolumes1();
        this.bidVolumes[1] = marketData.getBidVolumes2();
        this.bidVolumes[2] = marketData.getBidVolumes3();
        this.bidVolumes[3] = marketData.getBidVolumes4();
        this.bidVolumes[4] = marketData.getBidVolumes5();
        this.askPrices[0] = marketData.getAskPrices1();
        this.askPrices[1] = marketData.getAskPrices2();
        this.askPrices[2] = marketData.getAskPrices3();
        this.askPrices[3] = marketData.getAskPrices4();
        this.askPrices[4] = marketData.getAskPrices5();
        this.askVolumes[0] = marketData.getAskVolumes1();
        this.askVolumes[1] = marketData.getAskVolumes2();
        this.askVolumes[2] = marketData.getAskVolumes3();
        this.askVolumes[3] = marketData.getAskVolumes4();
        this.askVolumes[4] = marketData.getAskVolumes5();
    }

}
