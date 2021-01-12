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

@RequiredArgsConstructor
public final class OrdReport implements Serial<OrdReport> {

	private final long serialId = EpochSeqAllocator.allocate();

	// mapping to order id
	@Getter
	private final long ordId;

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

	public OrdReport setEpochMillis(long epochMillis) {
		this.epochMillis = epochMillis;
		return this;
	}

	public OrdReport setInvestorId(String investorId) {
		this.investorId = investorId;
		return this;
	}

	public OrdReport setOrdType(OrdType ordType) {
		this.ordType = ordType;
		return this;
	}

	public OrdReport setOrdStatus(OrdStatus ordStatus) {
		this.ordStatus = ordStatus;
		return this;
	}

	public OrdReport setOrderRef(String orderRef) {
		this.orderRef = orderRef;
		return this;
	}

	public OrdReport setBrokerUniqueId(String brokerUniqueId) {
		this.brokerUniqueId = brokerUniqueId;
		return this;
	}

	public OrdReport setInstrument(Instrument instrument) {
		this.instrument = instrument;
		return this;
	}

	public OrdReport setDirection(TrdDirection direction) {
		this.direction = direction;
		return this;
	}

	public OrdReport setAction(TrdAction action) {
		this.action = action;
		return this;
	}

	public OrdReport setOfferQty(int offerQty) {
		this.offerQty = offerQty;
		return this;
	}

	public OrdReport setFilledQty(int filledQty) {
		this.filledQty = filledQty;
		return this;
	}

	public OrdReport setOfferPrice(long offerPrice) {
		this.offerPrice = offerPrice;
		return this;
	}

	public OrdReport setTradePrice(long tradePrice) {
		this.tradePrice = tradePrice;
		return this;
	}

	public OrdReport setOfferTime(String offerTime) {
		this.offerTime = offerTime;
		return this;
	}

	public OrdReport setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
		return this;
	}

	@Override
	public String toString() {
		return JsonWrapper.toJson(this);
	}

	@Override
	public long serialId() {
		return serialId;
	}

}
