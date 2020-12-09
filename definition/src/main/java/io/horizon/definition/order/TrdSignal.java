package io.horizon.definition.order;

import io.horizon.definition.market.instrument.Instrument;
import io.horizon.definition.order.enums.TrdAction;
import io.horizon.definition.order.enums.TrdDirection;
import io.mercury.common.fsm.Signal;

public interface TradeSignal extends Signal {

	Instrument instrument();

	int strategyId();

	TrdAction action();

	TrdDirection direction();

	public static TradeSignal openLongSignal(Instrument instrument, int strategyId) {
		return new OpenLongSignal(instrument, strategyId);
	}

	public static TradeSignal openShortSignal(Instrument instrument, int strategyId) {
		return new OpenShortSignal(instrument, strategyId);
	}

	public static TradeSignal closeLongSignal(Instrument instrument, int strategyId) {
		return new CloseLongSignal(instrument, strategyId);
	}

	public static TradeSignal closeShortSignal(Instrument instrument, int strategyId) {
		return new CloseShortSignal(instrument, strategyId);
	}

	public static class OpenLongSignal extends BaseTradeSignal {

		private OpenLongSignal(Instrument instrument, int strategyId) {
			super(instrument, strategyId);
		}

		@Override
		public TrdAction action() {
			return TrdAction.Open;
		}

		@Override
		public TrdDirection direction() {
			return TrdDirection.Long;
		}

		@Override
		public int signalCode() {
			return 1;
		}
	}

	public static class OpenShortSignal extends BaseTradeSignal {

		private OpenShortSignal(Instrument instrument, int strategyId) {
			super(instrument, strategyId);
		}

		@Override
		public TrdAction action() {
			return TrdAction.Open;
		}

		@Override
		public TrdDirection direction() {
			return TrdDirection.Short;
		}

		@Override
		public int signalCode() {
			return 2;
		}
	}

	public static class CloseLongSignal extends BaseTradeSignal {

		private CloseLongSignal(Instrument instrument, int strategyId) {
			super(instrument, strategyId);
		}

		@Override
		public TrdAction action() {
			return TrdAction.Close;
		}

		@Override
		public TrdDirection direction() {
			return TrdDirection.Long;
		}

		@Override
		public int signalCode() {
			return 4;
		}
	}

	public static class CloseShortSignal extends BaseTradeSignal {

		private CloseShortSignal(Instrument instrument, int strategyId) {
			super(instrument, strategyId);
		}

		@Override
		public TrdAction action() {
			return TrdAction.Close;
		}

		@Override
		public TrdDirection direction() {
			return TrdDirection.Short;
		}

		@Override
		public int signalCode() {
			return 8;
		}
	}

	public abstract class BaseTradeSignal implements TradeSignal {

		private Instrument instrument;
		private int strategyId;

		private BaseTradeSignal(Instrument instrument, int strategyId) {
			this.instrument = instrument;
			this.strategyId = strategyId;
		}

		@Override
		public int strategyId() {
			return strategyId;
		}

		@Override
		public Instrument instrument() {
			return instrument;
		}
	}

}