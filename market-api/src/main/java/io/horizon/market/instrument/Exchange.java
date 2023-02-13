package io.horizon.market.instrument;

import static io.mercury.common.datetime.TimeZone.CST;
import static io.mercury.common.datetime.TimeZone.JST;
import static io.mercury.common.datetime.TimeZone.UTC;

import java.time.ZoneOffset;
import java.util.stream.Stream;

public enum Exchange {

    // BSE - The Bombay Stock Exchange Limited
    // PSE - Philippine Stock Exchange
    // CBOT - Chicago Board of Trade
    // NYMEX - New York Mercantile Exchange
    // COMEX - Chicago Mercantile Exchange
    // ICE - Intercontinental Exchange

    /**
     * Tokyo Commodity Exchange
     */
    TOCOM(11, "Tokyo Commodity Exchange", JST),

    /**
     * Tokyo Financial Exchange
     */
    TFX(12, "Tokyo Financial Exchange", JST),

    /**
     *
     */
    LME(31, "London Metal Exchange", UTC),

    /**
     * Shanghai Futures Exchange
     */
    SHFE(41, "Shanghai Futures Exchange", CST),

    /**
     * Dalian Commodity Exchange
     */
    DCE(42, "Dalian Commodity Exchange", CST),

    /**
     * Zhengzhou Commodity Exchange
     */
    ZCE(43, "Zhengzhou Commodity Exchange", CST),

    /**
     * China Financial Futures Exchange
     */
    CFFEX(44, "China Financial Futures Exchange", CST),

    /**
     * Shanghai International Energy Exchange
     */
    SHINE(45, "Shanghai International Energy Exchange", CST),

    /**
     * Shanghai Gold Exchange
     */
    SGE(46, "Shanghai Gold Exchange", CST),

    /**
     * Shanghai Stock Exchange
     */
    SSE(47, "Shanghai Stock Exchange", CST),

    /**
     * Shenzhen Stock Exchange
     */
    SZSE(48, "Shenzhen Stock Exchange", CST),

    ;

    // 唯一编码
    private final int exchangeId;

    // 交易所全名
    private final String desc;

    // 交易所时区
    private final ZoneOffset zoneOffset;

    // public static final long MAX = 0xFFFFL;

    /**
     * @param exchangeId int
     * @param fullName   String
     * @param zoneOffset ZoneOffset
     */
    Exchange(int exchangeId, String fullName, ZoneOffset zoneOffset) {
        this.exchangeId = exchangeId * 10000000;
        this.desc = fullName;
        this.zoneOffset = zoneOffset;
    }

    public int getExchangeId() {
        return exchangeId;
    }

    public String getExchangeCode() {
        return name();
    }

    public String getDesc() {
        return desc;
    }

    public ZoneOffset getZoneOffset() {
        return zoneOffset;
    }

    public static void main(String[] args) {

        Stream.of(Exchange.values()).forEach(e -> System.out.println(e.getExchangeId()));

    }

}
