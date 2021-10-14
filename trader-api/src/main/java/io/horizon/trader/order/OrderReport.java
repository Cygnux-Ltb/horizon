package io.horizon.trader.order;

import io.horizon.market.instrument.Instrument;
import io.horizon.trader.order.attr.OrdStatus;
import io.horizon.trader.order.attr.OrdType;
import io.horizon.trader.order.attr.TrdAction;
import io.horizon.trader.order.attr.TrdDirection;
import io.mercury.common.sequence.EpochSequence;
import io.mercury.common.sequence.Serial;
import io.mercury.serialization.json.JsonWrapper;

/**
 * 
 * @author yellow013
 *
 */

public final class OrderReport implements Serial<OrderReport> {

	/**
	 * Use io.mercury.common.sequence.EpochSequence.allocate()
	 */
	private final long serialId = EpochSequence.allocate();

	/**
	 * mapping to order id
	 */
	private final long ordSysId;

	/**
	 * report epoch milliseconds
	 */
	private long epochMillis;

	/**
	 * investorId
	 */
	private String investorId;

	/**
	 * ordType
	 */
	private OrdType ordType;

	/**
	 * order status of now report
	 */
	private OrdStatus ordStatus;

	/**
	 * FTDC orderRef
	 */
	private String orderRef;

	/**
	 * broker return id
	 */

	private String brokerUniqueId;

	/**
	 * instrument
	 */
	private Instrument instrument;

	/**
	 * direction
	 */
	private TrdDirection direction;

	/**
	 * action
	 */
	private TrdAction action;

	/**
	 * offer quantity
	 */
	private int offerQty;

	/**
	 * filled quantity
	 */
	private int filledQty;

	/**
	 * offer price
	 */
	private long offerPrice;

	/**
	 * order trade price
	 */
	private long tradePrice;

	/**
	 * offer time
	 */
	private String offerTime;

	/**
	 * last update time
	 */
	private String lastUpdateTime;

	public OrderReport(long ordSysId) {
		this.ordSysId = ordSysId;
	}

	public long getSerialId() {
		return serialId;
	}

	public long getOrdSysId() {
		return ordSysId;
	}

	public long getEpochMillis() {
		return epochMillis;
	}

	public String getInvestorId() {
		return investorId;
	}

	public OrdType getOrdType() {
		return ordType;
	}

	public OrdStatus getOrdStatus() {
		return ordStatus;
	}

	public String getOrderRef() {
		return orderRef;
	}

	public String getBrokerUniqueId() {
		return brokerUniqueId;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public TrdDirection getDirection() {
		return direction;
	}

	public TrdAction getAction() {
		return action;
	}

	public int getOfferQty() {
		return offerQty;
	}

	public int getFilledQty() {
		return filledQty;
	}

	public long getOfferPrice() {
		return offerPrice;
	}

	public long getTradePrice() {
		return tradePrice;
	}

	public String getOfferTime() {
		return offerTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public OrderReport setEpochMillis(long epochMillis) {
		this.epochMillis = epochMillis;
		return this;
	}

	public OrderReport setInvestorId(String investorId) {
		this.investorId = investorId;
		return this;
	}

	public OrderReport setOrdType(OrdType ordType) {
		this.ordType = ordType;
		return this;
	}

	public OrderReport setOrdStatus(OrdStatus ordStatus) {
		this.ordStatus = ordStatus;
		return this;
	}

	public OrderReport setOrderRef(String orderRef) {
		this.orderRef = orderRef;
		return this;
	}

	public OrderReport setBrokerUniqueId(String brokerUniqueId) {
		this.brokerUniqueId = brokerUniqueId;
		return this;
	}

	public OrderReport setInstrument(Instrument instrument) {
		this.instrument = instrument;
		return this;
	}

	public OrderReport setDirection(TrdDirection direction) {
		this.direction = direction;
		return this;
	}

	public OrderReport setAction(TrdAction action) {
		this.action = action;
		return this;
	}

	public OrderReport setOfferQty(int offerQty) {
		this.offerQty = offerQty;
		return this;
	}

	public OrderReport setFilledQty(int filledQty) {
		this.filledQty = filledQty;
		return this;
	}

	public OrderReport setOfferPrice(long offerPrice) {
		this.offerPrice = offerPrice;
		return this;
	}

	public OrderReport setTradePrice(long tradePrice) {
		this.tradePrice = tradePrice;
		return this;
	}

	public OrderReport setOfferTime(String offerTime) {
		this.offerTime = offerTime;
		return this;
	}

	public OrderReport setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
		return this;
	}

	@Override
	public String toString() {
		return JsonWrapper.toJson(this);
	}

}
