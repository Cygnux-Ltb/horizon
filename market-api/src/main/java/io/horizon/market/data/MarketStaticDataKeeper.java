package io.horizon.market.data;

public final class MarketStaticDataKeeper {

    // TODO
    // Map

    public record MarketStaticData(
            // 交易日
            int tradingDay,
            // instrumentID
            int instrumentId,
            // instrumentCode
            String instrumentCode,
            // 昨开盘价
            long preOpenPrice,
            // 昨最高价
            long preHighestPrice,
            // 昨最低价
            long preLowestPrice,
            // 昨收盘价
            long preClosePrice,
            // 昨结算价
            long preSettlementPrice,
            // 昨持仓量
            long preOpenInterest,
            // 今日开盘价
            long openPrice,
            // 涨停价
            long upperLimitPrice,
            // 跌停价
            long lowerLimitPrice,
            // 昨Delta
            long preDelta) {
    }

}
