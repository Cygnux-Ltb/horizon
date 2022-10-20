package io.horizon.market.data.impl;

import io.horizon.market.data.api.MarketDataNewOrder;
import io.horizon.market.data.api.Side;
import io.horizon.market.data.api.Visitor;

import java.util.Objects;

public final class DefaultMarketDataNewOrder
        extends DefaultMarketDataEvent implements MarketDataNewOrder {

    private final double price;
    private final double qty;
    private final Side side;

    private DefaultMarketDataNewOrder(final Builder<?> builder) {
        super(builder);
        this.price = builder.price;
        this.qty = builder.qty;
        this.side = builder.side;
    }

    public static <F> Builder<F> newBuilder() {
        return new Builder<>();
    }

    public static <F> Builder<F> newBuilder(final F fromBuilder) {
        return new Builder<>(fromBuilder);
    }


    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getQty() {
        return qty;
    }

    @Override
    public Side getSide() {
        return side;
    }

    @Override
    public <R, I> R accept(final Visitor<R, I> visitor, final I input) {
        return visitor.visit(this, input);
    }

    public final static class Builder<F> extends DefaultMarketDataEvent.Builder<F, Builder<F>> {
        private double price;
        private double qty;
        private Side side;

        private Builder() {
        }

        private Builder(final F fromBuilder) {
            this.fromBuilder = fromBuilder;
        }

        public Builder<F> getThis() {
            return this;
        }

        public Builder<F> withPrice(final double price) {
            this.price = price;
            return getThis();
        }

        public Builder<F> withQty(final double qty) {
            this.qty = qty;
            return getThis();
        }

        public Builder<F> withSide(final Side side) {
            this.side = side;
            return getThis();
        }

        public double getPrice() {
            return price;
        }

        public double getQty() {
            return qty;
        }

        public Side getSide() {
            return side;
        }

        @Override
        public MarketDataNewOrder build() {
            return new DefaultMarketDataNewOrder(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultMarketDataNewOrder that)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(that.price, price) == 0 &&
                Double.compare(that.qty, qty) == 0 &&
                side == that.side;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), price, qty, side);
    }
}
