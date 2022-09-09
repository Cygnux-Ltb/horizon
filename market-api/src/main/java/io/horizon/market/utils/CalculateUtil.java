package io.horizon.market.utils;

import org.eclipse.collections.api.block.predicate.primitive.DoubleDoublePredicate;
import org.eclipse.collections.api.block.predicate.primitive.LongLongPredicate;

public class CalculateUtil {

    public static final LongLongPredicate LongUpCross = (s, l) -> s > l;

    public static final LongLongPredicate LongDownCross = (s, l) -> s < l;

    public static final DoubleDoublePredicate DoubleUpCross = (s, l) -> s > l;

    public static final DoubleDoublePredicate DoubleDownCross = (s, l) -> s < l;

    /**
     * @param longs long[]
     * @return long
     */
    public static long ema(long[] longs) {
        return ema0(longs);
    }

    /**
     * @param longs long[]
     * @return long
     */
    private static long ema0(long[] longs) {
        return 0L;
    }

}
