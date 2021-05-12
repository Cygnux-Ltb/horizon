package io.horizon.transaction.order;

import static io.horizon.transaction.Constant.ExternalOrderStrategyId;

import java.util.function.LongSupplier;

import io.mercury.common.sequence.SnowflakeAlgorithm;

/**
 * OrdSysIdAllocator OrdSysId分配器接口
 * 
 * @author yellow013
 *
 */
@FunctionalInterface
public interface OrdSysIdAllocator extends LongSupplier {

	long getOrdSysId();

	@Override
	default long getAsLong() {
		return getOrdSysId();
	}

	OrdSysIdAllocator ExternalOrderAllocator = new OrdSysIdAllocator() {

		private SnowflakeAlgorithm algo = new SnowflakeAlgorithm(ExternalOrderStrategyId);

		@Override
		public long getOrdSysId() {
			return algo.next();
		}
	};

}
