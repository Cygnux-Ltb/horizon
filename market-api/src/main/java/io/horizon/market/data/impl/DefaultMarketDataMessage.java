package io.horizon.market.data.impl;

import com.google.common.collect.ImmutableList;
import io.horizon.market.data.api.MarketDataEvent;
import io.horizon.market.data.api.MarketDataMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class DefaultMarketDataMessage implements MarketDataMessage {

    private final long triggerTimestamp;
    private final long eventTimestamp;
    private final List<? extends MarketDataEvent> events;

    protected DefaultMarketDataMessage(final Builder<?> messageBuilder) {
        this.triggerTimestamp = messageBuilder.triggerTimestamp;
        this.eventTimestamp = messageBuilder.eventTimestamp;
        this.events = messageBuilder.buildEvents();
    }

    @Override
    public long getTriggerTimestamp() {
        return triggerTimestamp;
    }

    @Override
    public long getEventTimestamp() {
        return eventTimestamp;
    }

    @Override
    public List<? extends MarketDataEvent> getEvents() {
        return events;
    }

    protected abstract static class Builder<T extends Builder<T>> {
        private long triggerTimestamp;
        private long eventTimestamp;
        private ImmutableList.Builder<MarketDataEvent> eventsListBuilder = ImmutableList.builder();
        private final List<DefaultMarketDataNewOrder.Builder<?>> newOrderBuilders = new ArrayList<>();
        private final List<DefaultMarketDataReplaceOrder.Builder<?>> replaceOrderBuilders = new ArrayList<>();
        private final List<DefaultMarketDataDeleteOrder.Builder<?>> deleteOrderBuilders = new ArrayList<>();

        public abstract T getThis();

        private List<MarketDataEvent> buildEvents() {
            deleteOrderBuilders.forEach(o -> eventsListBuilder.add(o.build()));
            newOrderBuilders.forEach(o -> eventsListBuilder.add(o.build()));
            replaceOrderBuilders.forEach(o -> eventsListBuilder.add(o.build()));
            return eventsListBuilder.build();
        }

        public T withTriggerTimestamp(final long triggerTimestamp) {
            this.triggerTimestamp = triggerTimestamp;
            return getThis();
        }

        public T withEventTimestamp(final long eventTimestamp) {
            this.eventTimestamp = eventTimestamp;
            return getThis();
        }

        public T withEvent(final MarketDataEvent marketDataEvent) {
            this.eventsListBuilder.add(marketDataEvent);
            return getThis();
        }

        public T withEvents(final Collection<MarketDataEvent> marketDataEvents) {
            this.eventsListBuilder.addAll(marketDataEvents);
            return getThis();
        }

        public DefaultMarketDataNewOrder.Builder<T> addNewOrder() {
            final DefaultMarketDataNewOrder.Builder<T> newOrderBuilder = DefaultMarketDataNewOrder.newBuilder(getThis());
            newOrderBuilders.add(newOrderBuilder);
            return newOrderBuilder;
        }

        public DefaultMarketDataReplaceOrder.Builder<T> addReplaceOrder() {
            final DefaultMarketDataReplaceOrder.Builder<T> replaceOrderBuilder = DefaultMarketDataReplaceOrder.newBuilder(getThis());
            replaceOrderBuilders.add(replaceOrderBuilder);
            return replaceOrderBuilder;
        }

        public DefaultMarketDataDeleteOrder.Builder<T> addDeletedOrder() {
            final DefaultMarketDataDeleteOrder.Builder<T> deleteOrderBuilder = DefaultMarketDataDeleteOrder.newBuilder(getThis());
            deleteOrderBuilders.add(deleteOrderBuilder);
            return deleteOrderBuilder;
        }

        public T clear() {
            triggerTimestamp = 0;
            eventTimestamp = 0;
            eventsListBuilder = ImmutableList.builder();
            newOrderBuilders.clear();
            replaceOrderBuilders.clear();
            deleteOrderBuilders.clear();
            return getThis();
        }


        public abstract MarketDataMessage build();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultMarketDataMessage)) return false;
        DefaultMarketDataMessage that = (DefaultMarketDataMessage) o;
        return triggerTimestamp == that.triggerTimestamp &&
                eventTimestamp == that.eventTimestamp &&
                Objects.equals(events, that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(triggerTimestamp, eventTimestamp, events);
    }
}
