package io.horizon.market.data;

import io.horizon.market.data.api.MarketDataSnapshot;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class MarketDataSnapshotMatcher extends TypeSafeMatcher<MarketDataSnapshot> {
    private final MarketDataSnapshot marketDataSnapshot;

    public static MarketDataSnapshotMatcher matches(final MarketDataSnapshot marketDataSnapshot) {
        return new  MarketDataSnapshotMatcher(marketDataSnapshot);
    }

    private MarketDataSnapshotMatcher(final MarketDataSnapshot marketDataSnapshot) {
        this.marketDataSnapshot = marketDataSnapshot;
    }

    @Override
    protected boolean matchesSafely(final MarketDataSnapshot marketDataSnapshot) {
        return this.marketDataSnapshot.getTriggerTimestamp() == marketDataSnapshot.getTriggerTimestamp() &&
                this.marketDataSnapshot.getEvents().equals(marketDataSnapshot.getEvents());
    }

    @Override
    public void describeTo(final Description description) {
    }
}
