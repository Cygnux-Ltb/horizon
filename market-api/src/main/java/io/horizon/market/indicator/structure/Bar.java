package io.horizon.market.indicator.structure;

import io.mercury.common.serialization.specific.JsonSerializable;
import io.mercury.serialization.json.JsonWrapper;

import javax.annotation.Nonnull;

/**
 * @author yellow013
 */
public final class Bar implements JsonSerializable {

    // 开盘价
    private double open = 0.0D;

    // 最高价
    private double highest = Integer.MIN_VALUE;

    // 最低价
    private double lowest = Integer.MAX_VALUE;

    // 最新价
    private double last = 0.0D;

    /**
     * @param price double
     * @return Bar
     */
    public Bar onPrice(double price) {
        if (open == 0L)
            open = price;
        if (price > highest)
            highest = price;
        if (price < lowest)
            lowest = price;
        last = price;
        return this;
    }

    public double getOpen() {
        return open;
    }

    public double getHighest() {
        return highest;
    }

    public double getLowest() {
        return lowest;
    }

    public double getLast() {
        return last;
    }

    private static final String OpenField = "{\"open\":";
    private static final String HighestField = ",\"highest\":";
    private static final String LowestField = ",\"lowest\":";
    private static final String LastField = ",\"last\":";
    private static final String End = "}";

    @Override
    public String toString() {
        return // 开盘价
                OpenField + open +
                        // 最高价
                        HighestField + (highest == Long.MIN_VALUE ? 0L : highest) +
                        // 最低价
                        LowestField + (lowest == Long.MAX_VALUE ? 0L : lowest) +
                        // 最新价
                        LastField + last + End;
    }

    @Nonnull
    @Override
    public String toJson() {
        return toString();
    }

    public static void main(String[] args) {

        Bar bar = new Bar().onPrice(10000).onPrice(100L).onPrice(1000L);
        System.out.println(JsonWrapper.toJson(bar));
        System.out.println(bar);

    }

}
