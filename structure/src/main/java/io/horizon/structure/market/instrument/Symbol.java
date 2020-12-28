package io.horizon.structure.market.instrument;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.horizon.structure.vector.TradingPeriod;
import io.mercury.common.functional.Formattable;

public interface Symbol extends Formattable<String> {

	int symbolId();

	String symbolCode();

	Exchange exchange();

	ImmutableSortedSet<TradingPeriod> getTradingPeriodSet();

	PriceMultiplier getPriceMultiplier();

}
