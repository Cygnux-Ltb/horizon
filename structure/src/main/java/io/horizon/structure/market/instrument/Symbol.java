package io.horizon.structure.market.instrument;

import java.time.ZoneOffset;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.horizon.structure.serial.TradablePeriodSerial;
import io.mercury.common.functional.Formattable;

public interface Symbol extends Formattable<String> {

	int getSymbolId();

	String getSymbolCode();

	Exchange getExchange();

	default String getExchangeCode() {
		return getExchange().name();
	}

	default ZoneOffset getZoneOffset() {
		return getExchange().getZoneOffset();
	}
	
	InstrumentType getType();

	ImmutableSortedSet<TradablePeriodSerial> getTradablePeriodSet();

	PriceMultiplier getPriceMultiplier();

}
