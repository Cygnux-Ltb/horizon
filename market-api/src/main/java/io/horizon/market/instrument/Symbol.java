package io.horizon.market.instrument;

import java.time.ZoneOffset;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.mercury.common.functional.Formattable;

public interface Symbol extends Formattable<String> {

	int getSymbolId();

	String getSymbolCode();

	int getSeqWithinExchange();

	Exchange getExchange();

	default String getExchangeCode() {
		return getExchange().getCode();
	}

	default ZoneOffset getZoneOffset() {
		return getExchange().getZoneOffset();
	}

	InstrumentType getType();

	int getTickSize();

	ImmutableSortedSet<TradablePeriod> getTradablePeriodSet();

	PriceMultiplier getPriceMultiplier();

}
