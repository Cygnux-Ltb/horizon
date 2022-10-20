package org.dev4fx.marketdata.model.impl;

import org.dev4fx.marketdata.model.api.MarketDataEvent;

import java.util.Objects;

public abstract class DefaultMarketDataEvent implements MarketDataEvent {
    private final String orderId;
    private final String instrument;
    private final String market;

    protected DefaultMarketDataEvent(final Builder<?, ?> eventBuilder) {
        this.orderId = eventBuilder.orderId;
        this.instrument = eventBuilder.instrument;
        this.market = eventBuilder.market;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public String getInstrument() {
        return instrument;
    }

    @Override
    public String getMarket() {
        return market;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultMarketDataEvent)) return false;
        DefaultMarketDataEvent event = (DefaultMarketDataEvent) o;
        return Objects.equals(orderId, event.orderId) &&
                Objects.equals(instrument, event.instrument) &&
                Objects.equals(market, event.market);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, instrument, market);
    }

    protected abstract static class Builder<F, T extends Builder<F, T>> {
        private String orderId;
        private String instrument;
        private String market;
        protected F fromBuilder;

        public F end() {
            return fromBuilder;
        }

        public abstract T getThis();

        public T withOrderId(final String orderId) {
            this.orderId = orderId;
            return getThis();
        }

        public T withInstrument(final String instrument) {
            this.instrument = instrument;
            return getThis();
        }

        public T withMarket(final String market) {
            this.market = market;
            return getThis();
        }

        public String getOrderId() {
            return orderId;
        }

        public String getInstrument() {
            return instrument;
        }

        public String getMarket() {
            return market;
        }

        public abstract MarketDataEvent build();
    }

}
