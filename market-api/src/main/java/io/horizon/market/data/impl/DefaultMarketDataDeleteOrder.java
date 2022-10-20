package org.dev4fx.marketdata.model.impl;

import org.dev4fx.marketdata.model.api.MarketDataDeleteOrder;
import org.dev4fx.marketdata.model.api.Visitor;

public final class DefaultMarketDataDeleteOrder extends DefaultMarketDataEvent implements MarketDataDeleteOrder {

    private DefaultMarketDataDeleteOrder(final Builder<?> builder) {
        super(builder);
    }

    public static <F> Builder<F> newBuilder() {
        return new Builder<>();
    }

    public static <F> Builder<F> newBuilder(final F fromBuilder) {
        return new Builder<>(fromBuilder);
    }


    @Override
    public <R, I> R accept(final Visitor<R, I> visitor, final I input) {
        return visitor.visit(this, input);
    }

    public final static class Builder<F> extends DefaultMarketDataEvent.Builder<F, Builder<F>> {

        private Builder() {
        }

        private Builder(final F fromBuilder) {
            this.fromBuilder = fromBuilder;
        }

        public Builder<F> getThis() {
            return this;
        }

        @Override
        public MarketDataDeleteOrder build() {
            return new DefaultMarketDataDeleteOrder(this);
        }
    }


}
