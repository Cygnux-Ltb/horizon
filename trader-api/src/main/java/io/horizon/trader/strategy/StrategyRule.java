package io.horizon.trader.strategy;

import java.util.function.Predicate;

public interface StrategyRule {

    default <T> boolean and(T t, Predicate<T> rule0, Predicate<T> rule1) {
        return rule0.test(t) && rule1.test(t);
    }

    default <T0, T1> boolean and(T0 t0, T1 t1, Predicate<T0> rule0, Predicate<T1> rule1) {
        return rule0.test(t0) && rule1.test(t1);
    }

    default <T> boolean or(T t, Predicate<T> rule0, Predicate<T> rule1) {
        return rule0.test(t) || rule1.test(t);
    }

    default <T0, T1> boolean or(T0 t0, T1 t1, Predicate<T0> rule0, Predicate<T1> rule1) {
        return rule0.test(t0) || rule1.test(t1);
    }

}
