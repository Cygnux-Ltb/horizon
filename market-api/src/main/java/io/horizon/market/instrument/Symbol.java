package io.horizon.market.instrument;

import org.eclipse.collections.api.list.ImmutableList;

import io.horizon.market.instrument.attr.PriceMultiplier;
import io.horizon.market.instrument.attr.TradablePeriod;
import io.mercury.common.functional.Formatter;

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
