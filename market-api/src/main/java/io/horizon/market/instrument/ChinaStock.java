package io.horizon.market.instrument;

import io.horizon.market.instrument.attr.PriceMultiplier;
import io.horizon.market.instrument.attr.TradablePeriod;
import io.horizon.market.instrument.base.BaseStock;
import org.eclipse.collections.api.list.ImmutableList;

/**
 * 此class仅作为namespace使用
 *
 * @author yellow013
 */
public final class ChinaStock {

    private ChinaStock() {
    }

    public static final class ChinaStockInstrument extends BaseStock implements Symbol {

        public ChinaStockInstrument(int instrumentId, String instrumentCode,
                                    Exchange exchange, PriceMultiplier multiplier,
                                    ImmutableList<TradablePeriod> tradablePeriods) {
            super(instrumentId, instrumentCode, exchange, multiplier, 1, tradablePeriods);
        }

        @Override
        public int getTickSize() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public ImmutableList<TradablePeriod> getTradablePeriods() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public PriceMultiplier getMultiplier() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean isSymbolCode(String code) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public ImmutableList<Instrument> getInstruments() {
            // TODO Auto-generated method stub
            return null;
        }

    }

}
