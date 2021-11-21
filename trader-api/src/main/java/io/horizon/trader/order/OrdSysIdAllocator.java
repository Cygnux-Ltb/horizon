package io.horizon.trader.order;

import java.util.function.LongSupplier;

import io.horizon.trader.Const;
import io.mercury.common.sequence.SnowflakeAlgo;

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

		private SnowflakeAlgo algo = new SnowflakeAlgo(Const.ExternalOrderStrategyId);

		@Override
		public long getOrdSysId() {
			return algo.next();
		}

	};

}
