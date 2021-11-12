package io.horizon.market.instrument;

import org.eclipse.collections.api.list.ImmutableList;

import io.mercury.common.functional.Formattable;

public interface Symbol extends Formattable<String> {

	Exchange getExchange();

	int getSymbolId();

	String getSymbolCode();

	ImmutableList<TradablePeriod> getTradablePeriods();

	PriceMultiplier getPriceMultiplier();

}
