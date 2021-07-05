package io.horizon.trader.order;

import io.horizon.market.instrument.Instrument;
import io.horizon.trader.order.attr.OrdStatus;
import io.horizon.trader.order.attr.OrdType;
import io.horizon.trader.order.attr.TrdAction;
import io.horizon.trader.order.attr.TrdDirection;
import io.mercury.common.sequence.EpochSequence;
import io.mercury.common.sequence.Serial;
import io.mercury.serialization.json.JsonWrapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 
 * @author yellow013
 *
 */
@Accessors(chain = true)
@RequiredArgsConstructor
public final class OrderReport implements Serial {

	/**
	 * Use io.mercury.common.sequence.EpochSequence.allocate()
	 */
	@Getter
	private final long serialId = EpochSequence.allocate();

	// mapping to order id
	@Getter
	private final long ordSysId;

	// report epoch milliseconds
	@Getter
	@Setter
	private long epochMillis;

	// investorId
	@Getter
	@Setter
	private String investorId;

	// ordType
	@Getter
	@Setter
	private OrdType ordType;

	// order status of now report
	@Getter
	@Setter
	private OrdStatus ordStatus;

	// FTDC orderRef
	@Getter
	@Setter
	private String orderRef;

	// broker return id
	@Getter
	@Setter
	private String brokerUniqueId;

	// instrument
	@Getter
	@Setter
	private Instrument instrument;

	// direction
	@Getter
	@Setter
	private TrdDirection direction;

	// action
	@Getter
	@Setter
	private TrdAction action;

	// offer quantity
	@Getter
	@Setter
	private int offerQty;

	// filled quantity
	@Getter
	@Setter
	private int filledQty;

	// offer price
	@Getter
	@Setter
	private long offerPrice;

	// order trade price
	@Getter
	@Setter
	private long tradePrice;

	// offer time
	@Getter
	@Setter
	private String offerTime;

	// last update time
	@Getter
	@Setter
	private String lastUpdateTime;

	@Override
	public String toString() {
		return JsonWrapper.toJson(this);
	}

}
