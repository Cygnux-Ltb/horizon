package io.horizon.structure.order;

import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.enums.OrdStatus;
import io.horizon.structure.order.enums.OrdType;
import io.horizon.structure.order.enums.TrdAction;
import io.horizon.structure.order.enums.TrdDirection;
import io.mercury.common.sequence.EpochSeqAllocator;
import io.mercury.common.sequence.Serial;
import io.mercury.serialization.json.JsonWrapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Setter
@Accessors(chain = true)
public final class OrderReport implements Serial<OrderReport> {

	private final long serialId = EpochSeqAllocator.allocate();

	// mapping to order id
	@Getter
	private final long ordSysId;

	// report epoch milliseconds
	@Getter
	private long epochMillis;

	// investorId
	@Getter
	private String investorId;

	// ordType
	@Getter
	private OrdType ordType;

	// order status of now report
	@Getter
	private OrdStatus ordStatus;

	// CTP orderRef
	@Getter
	private String orderRef;

	// broker return id
	@Getter
	private String brokerUniqueId;

	// instrument
	@Getter
	private Instrument instrument;

	// direction
	@Getter
	private TrdDirection direction;

	// action
	@Getter
	private TrdAction action;

	// offer quantity
	@Getter
	private int offerQty;

	// filled quantity
	@Getter
	private int filledQty;

	// offer price
	@Getter
	private long offerPrice;

	// order trade price
	@Getter
	private long tradePrice;

	// offer time
	@Getter
	private String offerTime;

	// last update time
	@Getter
	private String lastUpdateTime;

	@Override
	public String toString() {
		return JsonWrapper.toJson(this);
	}

	@Override
	public long serialId() {
		return serialId;
	}

}
