package io.horizon.market.data.api;

public interface Visitable {

    <R, I> R accept(Visitor<R, I> visitor, I input);

}
