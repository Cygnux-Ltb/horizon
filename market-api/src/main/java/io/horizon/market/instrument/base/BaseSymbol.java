package io.horizon.market.instrument.base;

import io.horizon.market.instrument.Exchange;
import io.horizon.market.instrument.Symbol;
import io.horizon.market.instrument.attr.PriceMultiplier;
import io.horizon.market.instrument.attr.TradablePeriod;
import org.eclipse.collections.api.list.ImmutableList;

public abstract class BaseSymbol implements Symbol {

    protected final int symbolId;

    protected final String symbolCode;

    protected final Exchange exchange;

    protected final PriceMultiplier priceMultiplier;

    protected final ImmutableList<TradablePeriod> tradablePeriods;

    public BaseSymbol(int symbolId, String symbolCode, Exchange exchange, PriceMultiplier priceMultiplier,
                      ImmutableList<TradablePeriod> tradablePeriods) {
        this.symbolId = symbolId;
        this.symbolCode = symbolCode;
        this.exchange = exchange;
        this.priceMultiplier = priceMultiplier;
        this.tradablePeriods = tradablePeriods;
    }

    @Override
    public int getSymbolId() {
        return symbolId;
    }

    @Override
    public String getSymbolCode() {
        return symbolCode;
    }

    @Override
    public Exchange getExchange() {
        return exchange;
    }

    @Override
    public PriceMultiplier getMultiplier() {
        return priceMultiplier;
    }

    @Override
    public ImmutableList<TradablePeriod> getTradablePeriods() {
        return tradablePeriods;
    }

}
