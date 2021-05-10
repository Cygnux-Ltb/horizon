package io.horizon.market.instrument;

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
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PriceMultiplier {

	/**
	 * 1L
	 */
	NONE(
			// longMultiplier
			LONG_MULTIPLIER_1L,
			// doubleMultiplier
			DOUBLE_MULTIPLIER_1D,
			// DoubleToLongFunction
			d -> (long) d,
			// LongToDoubleFunction
			l -> (double) l),

	/**
	 * 100L
	 */
	MULTIPLIER_100(
			// longMultiplier
			LONG_MULTIPLIER_100L,
			// doubleMultiplier
			DOUBLE_MULTIPLIER_100D,
			// DoubleToLongFunction
			DecimalSupporter::doubleToLong2,
			// LongToDoubleFunction
			DecimalSupporter::longToDouble2)

	,

	/**
	 * 10000L
	 */
	MULTIPLIER_10000(
			// longMultiplier
			LONG_MULTIPLIER_10000L,
			// doubleMultiplier
			DOUBLE_MULTIPLIER_10000D,
			// DoubleToLongFunction
			DecimalSupporter::doubleToLong4,
			// LongToDoubleFunction
			DecimalSupporter::longToDouble4),

	/**
	 * 1000000L
	 */
	MULTIPLIER_1000000(
			// longMultiplier
			LONG_MULTIPLIER_1000000L,
			// doubleMultiplier
			DOUBLE_MULTIPLIER_1000000D,
			// DoubleToLongFunction
			DecimalSupporter::doubleToLong6,
			// LongToDoubleFunction
			DecimalSupporter::longToDouble6),

	/**
	 * 100000000L
	 */
	MULTIPLIER_100000000(
			// longMultiplier
			LONG_MULTIPLIER_100000000L,
			// doubleMultiplier
			DOUBLE_MULTIPLIER_100000000D,
			// DoubleToLongFunction
			DecimalSupporter::doubleToLong8,
			// LongToDoubleFunction
			DecimalSupporter::longToDouble8),

	;

	@Getter
	private final long longMultiplier;
	@Getter
	private final double doubleMultiplier;

	private final DoubleToLongFunction toLongFunc;
	private final LongToDoubleFunction toDoubleFunc;

	/**
	 * 
	 * @param d
	 * @return
	 */
	public long toLong(double d) {
		return toLongFunc.applyAsLong(d);
	}

	/**
	 * 
	 * @param l
	 * @return
	 */
	public double toDouble(long l) {
		return toDoubleFunc.applyAsDouble(l);
	}

}
