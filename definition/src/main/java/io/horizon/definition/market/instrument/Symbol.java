package io.gemini.definition.market.instrument;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.gemini.definition.market.vector.TradingPeriod;

public interface Symbol extends FinancialObj {

	ImmutableSortedSet<TradingPeriod> getTradingPeriodSet();

	Exchange exchange();

	PriceMultiplier getPriceMultiplier();

	String fmtText();

}
