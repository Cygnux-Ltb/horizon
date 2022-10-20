package org.dev4fx.marketdata.model.impl;

import org.dev4fx.marketdata.model.api.MarketDataSnapshot;
import org.dev4fx.marketdata.model.api.Visitor;

public final class DefaultMarketDataSnapshot extends DefaultMarketDataMessage implements MarketDataSnapshot {

    private DefaultMarketDataSnapshot(final Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    @Override
    public <R, I> R accept(final Visitor<R, I> visitor, final I input) {
        return visitor.visit(this, input);
    }

    public final static class Builder extends DefaultMarketDataMessage.Builder<Builder> {

        private Builder() {
        }

        public Builder getThis() {
            return this;
        }

        @Override
        public MarketDataSnapshot build() {
            return new DefaultMarketDataSnapshot(this);
        }

    }

}
