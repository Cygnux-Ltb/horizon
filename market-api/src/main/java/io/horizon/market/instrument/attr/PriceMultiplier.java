package io.horizon.market.instrument.attr;

import static io.mercury.common.number.DecimalSupporter.DOUBLE_MULTIPLIER_100000000D;
import static io.mercury.common.number.DecimalSupporter.DOUBLE_MULTIPLIER_1000000D;
import static io.mercury.common.number.DecimalSupporter.DOUBLE_MULTIPLIER_10000D;
import static io.mercury.common.number.DecimalSupporter.DOUBLE_MULTIPLIER_100D;
import static io.mercury.common.number.DecimalSupporter.DOUBLE_MULTIPLIER_1D;
import static io.mercury.common.number.DecimalSupporter.LONG_MULTIPLIER_100000000L;
import static io.mercury.common.number.DecimalSupporter.LONG_MULTIPLIER_1000000L;
import static io.mercury.common.number.DecimalSupporter.LONG_MULTIPLIER_10000L;
import static io.mercury.common.number.DecimalSupporter.LONG_MULTIPLIER_100L;
import static io.mercury.common.number.DecimalSupporter.LONG_MULTIPLIER_1L;

import java.util.function.DoubleToLongFunction;
import java.util.function.LongToDoubleFunction;

import io.mercury.common.number.DecimalSupporter;

public enum PriceMultiplier {

    /**
     * 1L
     */
    NONE(//
            LONG_MULTIPLIER_1L, DOUBLE_MULTIPLIER_1D,
            //
            d -> (long) d, l -> (double) l),

    /**
     * 100L
     */
    MULTIPLIER_100(//
            LONG_MULTIPLIER_100L, DOUBLE_MULTIPLIER_100D,
            //
            DecimalSupporter::doubleToLong2, DecimalSupporter::longToDouble2),

    /**
     * 10000L
     */
    MULTIPLIER_10000(//
            LONG_MULTIPLIER_10000L, DOUBLE_MULTIPLIER_10000D,
            //
            DecimalSupporter::doubleToLong4, DecimalSupporter::longToDouble4),

    /**
     * 1000000L
     */
    MULTIPLIER_1000000(//
            LONG_MULTIPLIER_1000000L, DOUBLE_MULTIPLIER_1000000D,
            //
            DecimalSupporter::doubleToLong6, DecimalSupporter::longToDouble6),

    /**
     * 100000000L
     */
    MULTIPLIER_100000000(//
            LONG_MULTIPLIER_100000000L, DOUBLE_MULTIPLIER_100000000D,
            //
            DecimalSupporter::doubleToLong8, DecimalSupporter::longToDouble8),

    ;

    private final long longMultiplier;
    private final double doubleMultiplier;

    private final DoubleToLongFunction toLongFunc;
    private final LongToDoubleFunction toDoubleFunc;

    PriceMultiplier(long longMultiplier, double doubleMultiplier, DoubleToLongFunction toLongFunc,
                    LongToDoubleFunction toDoubleFunc) {
        this.longMultiplier = longMultiplier;
        this.doubleMultiplier = doubleMultiplier;
        this.toLongFunc = toLongFunc;
        this.toDoubleFunc = toDoubleFunc;
    }

    public long getLongMultiplier() {
        return longMultiplier;
    }

    public double getDoubleMultiplier() {
        return doubleMultiplier;
    }

    /**
     * @param d double
     * @return long
     */
    public long toLong(double d) {
        return toLongFunc.applyAsLong(d);
    }

    /**
     * @param l long
     * @return double
     */
    public double toDouble(long l) {
        return toDoubleFunc.applyAsDouble(l);
    }

}
