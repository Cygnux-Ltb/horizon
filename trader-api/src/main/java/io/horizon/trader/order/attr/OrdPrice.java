package io.horizon.trader.order.attr;

import javax.annotation.Nonnull;

import io.horizon.trader.order.ChildOrder;
import io.horizon.trader.order.TradeRecord;
import io.mercury.common.serialization.specific.JsonSerializable;

/**
 * @author yellow013
 */
public final class OrdPrice implements JsonSerializable {

    /*
     * 委托价格
     */
    private double offerPrice;

    /*
     * 成交均价
     */
    private double avgTradePrice;

    private OrdPrice() {
    }

    private OrdPrice(double offerPrice) {
        this.offerPrice = offerPrice;
    }

    public static OrdPrice withEmpty() {
        return new OrdPrice();
    }

    public static OrdPrice withOffer(double offerPrice) {
        return new OrdPrice(offerPrice);
    }

    public double getOfferPrice() {
        return offerPrice;
    }

    public double getAvgTradePrice() {
        return avgTradePrice;
    }

    public OrdPrice setOfferPrice(double offerPrice) {
        this.offerPrice = offerPrice;
        return this;
    }

    public OrdPrice calcAvgTradePrice(@Nonnull ChildOrder order) {
        var records = order.getRecords();
        if (records.size() == 1) {
            this.avgTradePrice = records.get(0).tradePrice();
        }
        if (records.size() > 1) {
            var multiplier = order.getInstrument().getMultiplier();
            // 计算总成交金额
            long totalTurnover = records
                    .sumOfLong(trade -> multiplier.toLong(trade.tradePrice()) * trade.tradeQty());
            // 计算总成交量
            long totalQty = records.sumOfInt(TradeRecord::tradeQty);
            if (totalQty > 0L)
                this.avgTradePrice = multiplier.toDouble(totalTurnover / totalQty);
        }
        return this;
    }

    private static final String OfferPriceField = "{\"offerPrice\" : ";
    private static final String AvgTradePriceField = ", \"avgTradePrice\" : ";
    private static final String End = "}";

    @Override
    public String toString() {
        return OfferPriceField +
                offerPrice +
                AvgTradePriceField +
                avgTradePrice +
                End;
    }

    @Override
    @Nonnull
    public String toJson() {
        return toString();
    }

}
