package io.horizon.market.utils;

import org.eclipse.collections.api.block.predicate.primitive.DoubleDoublePredicate;
import org.eclipse.collections.api.block.predicate.primitive.LongLongPredicate;

public class CalculateUtil {

	public static final LongLongPredicate L_UpCross = (s, l) -> s > l;

	public static final LongLongPredicate L_DownCross = (s, l) -> s < l;

	public static final DoubleDoublePredicate D_UpCross = (s, l) -> s > l;

	public static final DoubleDoublePredicate D_DownCross = (s, l) -> s < l;

	public static long ema(long[] args) {
		return 0L;

	}

	public static long ema0() {
		return 0L;
	}

}