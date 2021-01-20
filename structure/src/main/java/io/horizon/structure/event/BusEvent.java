package io.horizon.structure.event;

import io.horizon.structure.market.data.MarketData;
import io.horizon.structure.order.Order;
import io.horizon.structure.order.OrderReport;
import io.mercury.common.fsm.Signal;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Deprecated
@Getter
@Setter
@Accessors(chain = true)
public final class BusEvent {

	private ChannelType channelType;
	private BusEventType busEventType;

	private ControlEvent controlEvent;
	private MarketData marketData;
	private Signal signal;
	private Order order;
	private OrderReport orderReport;

	public static enum ChannelType {
		InBound, Outbound
	}

	public static enum BusEventType {
		MarketData, Order, Signal, ControlEvent,
	}

}
