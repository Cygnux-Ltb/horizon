package io.horizon.market.instrument;

import io.horizon.market.instrument.attr.PriceMultiplier;
import io.horizon.market.instrument.attr.TradablePeriod;
import io.mercury.common.functional.Formatter;
import org.eclipse.collections.api.list.ImmutableList;

public interface Symbol extends Formatter<String> {

    Exchange getExchange();

    int getSymbolId();

    String getSymbolCode();

    ImmutableList<TradablePeriod> getTradablePeriods();

    ImmutableList<Instrument> getInstruments();

    PriceMultiplier getMultiplier();

    int getTickSize();

    boolean isSymbolCode(String code);

}
