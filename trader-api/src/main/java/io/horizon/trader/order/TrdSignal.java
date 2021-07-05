package io.horizon.trader.order;

import io.horizon.market.instrument.Instrument;
import io.horizon.trader.order.attr.TrdAction;
import io.horizon.trader.order.attr.TrdDirection;
import io.mercury.common.fsm.Signal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface TrdSignal extends Signal {

	Instrument getInstrument();

	int getStrategyId();

	TrdAction getAction();

	TrdDirection getDirection();

	public static TrdSignal newOpenLongSignal(Instrument instrument, int strategyId) {
		return new OpenLongSignal(instrument, strategyId);
	}

	public static TrdSignal newOpenShortSignal(Instrument instrument, int strategyId) {
		return new OpenShortSignal(instrument, strategyId);
	}

	public static TrdSignal newCloseLongSignal(Instrument instrument, int strategyId) {
		return new CloseLongSignal(instrument, strategyId);
	}

	public static TrdSignal newCloseShortSignal(Instrument instrument, int strategyId) {
		return new CloseShortSignal(instrument, strategyId);
	}

	public static class OpenLongSignal extends BaseTradeSignal {

		private OpenLongSignal(Instrument instrument, int strategyId) {
			super(instrument, strategyId);
		}

		@Override
		public TrdAction getAction() {
			return TrdAction.Open;
		}

		@Override
		public TrdDirection getDirection() {
			return TrdDirection.Long;
		}

		@Override
		public int getSignalCode() {
			return 1;
		}
	}

	public static class OpenShortSignal extends BaseTradeSignal {

		private OpenShortSignal(Instrument instrument, int strategyId) {
			super(instrument, strategyId);
		}

		@Override
		public TrdAction getAction() {
			return TrdAction.Open;
		}

		@Override
		public TrdDirection getDirection() {
			return TrdDirection.Short;
		}

		@Override
		public int getSignalCode() {
			return 2;
		}
	}

	public static class CloseLongSignal extends BaseTradeSignal {

		private CloseLongSignal(Instrument instrument, int strategyId) {
			super(instrument, strategyId);
		}

		@Override
		public TrdAction getAction() {
			return TrdAction.Close;
		}

		@Override
		public TrdDirection getDirection() {
			return TrdDirection.Long;
		}

		@Override
		public int getSignalCode() {
			return 4;
		}
	}

	public static class CloseShortSignal extends BaseTradeSignal {

		private CloseShortSignal(Instrument instrument, int strategyId) {
			super(instrument, strategyId);
		}

		@Override
		public TrdAction getAction() {
			return TrdAction.Close;
		}

		@Override
		public TrdDirection getDirection() {
			return TrdDirection.Short;
		}

		@Override
		public int getSignalCode() {
			return 8;
		}
	}

	@RequiredArgsConstructor
	static abstract class BaseTradeSignal implements TrdSignal {

		@Getter
		private final Instrument instrument;

		@Getter
		private final int strategyId;

	}

}
