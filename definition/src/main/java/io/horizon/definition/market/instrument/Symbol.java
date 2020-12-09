package io.horizon.definition.market.instrument;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.horizon.definition.vector.TradingPeriod;

public interface Symbol extends FinancialObj {

	Exchange exchange();

	ImmutableSortedSet<TradingPeriod> getTradingPeriodSet();

	PriceMultiplier getPriceMultiplier();

	String fmtText();

}
