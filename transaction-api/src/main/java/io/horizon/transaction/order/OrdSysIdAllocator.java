package io.horizon.structure.order;

import java.util.function.LongSupplier;

import io.mercury.common.sequence.SnowflakeAlgorithm;

@FunctionalInterface
public interface OrdSysIdAllocator extends LongSupplier {

	long getOrdSysId();

	@Override
	default long getAsLong() {
		return getOrdSysId();
	}

	OrdSysIdAllocator ExternalOrderAllocator = new OrdSysIdAllocator() {

		private SnowflakeAlgorithm algorithm = new SnowflakeAlgorithm(0);

		@Override
		public long getOrdSysId() {
			return algorithm.next();
		}
	};

}
