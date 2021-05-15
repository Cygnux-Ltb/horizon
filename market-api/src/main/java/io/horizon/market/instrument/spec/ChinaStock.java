package io.horizon.market.instrument.spec;

import java.time.ZoneOffset;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.horizon.market.instrument.AbstractInstrument;
import io.horizon.market.instrument.Exchange;
import io.horizon.market.instrument.InstrumentType;
import io.horizon.market.instrument.PriceMultiplier;
import io.horizon.market.instrument.Symbol;
import io.horizon.market.serial.TradablePeriodSerial;

public final class ChinaStock extends AbstractInstrument implements Symbol {

	protected ChinaStock(int instrumentId, String instrumentCode) {
		super(instrumentId, instrumentCode);
	}

	@Override
	public boolean isAvailableImmediately() {
		return false;
	}

	@Override
	public String format() {
		return super.format();
	}

	@Override
	public PriceMultiplier getPriceMultiplier() {
		return PriceMultiplier.NONE;
	}

	@Override
	public String getSymbolCode() {
		return null;
	}

	@Override
	public InstrumentType getType() {
		return InstrumentType.STOCK;
	}

	@Override
	public String getExchangeCode() {
		return Symbol.super.getExchangeCode();
	}

	@Override
	public ZoneOffset getZoneOffset() {
		return super.getZoneOffset();
	}

	@Override
	public int getSymbolId() {
		return 0;
	}

	@Override
	public Exchange getExchange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImmutableSortedSet<TradablePeriodSerial> getTradablePeriodSet() {
		// TODO Auto-generated method stub
		return null;
	}

}
