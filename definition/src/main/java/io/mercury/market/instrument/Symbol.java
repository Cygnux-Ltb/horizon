package io.mercury.market.instrument;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.mercury.market.vector.TradingPeriod;

public interface Symbol extends FinancialObj {

	ImmutableSortedSet<TradingPeriod> getTradingPeriodSet();

	Exchange exchange();

	PriceMultiplier getPriceMultiplier();

	String fmtText();

}
