package io.horizon.trader.order;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.market.instrument.Instrument;
import io.horizon.trader.order.attr.OrdPrice;
import io.horizon.trader.order.attr.OrdQty;
import io.horizon.trader.order.attr.OrdStatus;
import io.horizon.trader.order.attr.OrdTimestamp;
import io.horizon.trader.order.attr.OrdType;
import io.horizon.trader.order.attr.TrdDirection;

public abstract class AbstractOrder implements Order {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3444258095612091354L;

	/*
	 * ordSysId
	 */

	protected final long ordSysId;

	/*
	 * 策略Id
	 */

	protected final int strategyId;

	/*
	 * 子账户Id
	 */

	protected final int subAccountId;

	/*
	 * 实际账户Id
	 */

	protected final int accountId;

	/*
	 * instrument
	 */

	protected final Instrument instrument;

	/*
	 * 数量
	 */

	protected final OrdQty qty;

	/*
	 * 价格
	 */

	protected final OrdPrice price;

	/*
	 * 订单类型
	 */

	protected final OrdType type;

	/*
	 * 订单方向
	 */

	protected final TrdDirection direction;

	/*
	 * 时间戳
	 */

	protected final OrdTimestamp timestamp;

	/*
	 * 订单状态(可变)
	 */

	protected OrdStatus status;

	/*
	 * 订单备注(可变)
	 */

	protected String remark;

	/*
	 * 默认备注
	 */
	private static final String DefaultRemark = "NONE";

	/**
	 * 
	 * @param ordSysId
	 * @param strategyId
	 * @param subAccountId
	 * @param accountId
	 * @param instrument
	 * @param qty
	 * @param price
	 * @param type
	 * @param direction
	 */
	protected AbstractOrder(long ordSysId, int strategyId, int subAccountId, int accountId,
			@Nonnull Instrument instrument, @Nonnull OrdQty qty, @Nonnull OrdPrice price, @Nonnull OrdType type,
			@Nonnull TrdDirection direction) {
		this.ordSysId = ordSysId;
		this.strategyId = strategyId;
		this.subAccountId = subAccountId;
		this.accountId = accountId;
		this.instrument = instrument;
		this.qty = qty;
		this.price = price;
		this.type = type;
		this.direction = direction;
		this.timestamp = OrdTimestamp.withNow();
		this.status = OrdStatus.PendingNew;
		this.remark = DefaultRemark;
	}

	@Override
	public long getOrdSysId() {
		return ordSysId;
	}

	@Override
	public int getStrategyId() {
		return strategyId;
	}

	@Override
	public int getSubAccountId() {
		return subAccountId;
	}

	@Override
	public int getAccountId() {
		return accountId;
	}

	@Override
	public Instrument getInstrument() {
		return instrument;
	}

	@Override
	public OrdQty getQty() {
		return qty;
	}

	@Override
	public OrdPrice getPrice() {
		return price;
	}

	@Override
	public OrdType getType() {
		return type;
	}

	@Override
	public TrdDirection getDirection() {
		return direction;
	}

	@Override
	public OrdTimestamp getTimestamp() {
		return timestamp;
	}

	@Override
	public OrdStatus getStatus() {
		return status;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	@Override
	public Order setStatus(OrdStatus status) {
		this.status = status;
		return this;
	}

	@Override
	public Order setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	private static final String OrderOutputTemplate = "{}, Order attr : "
			+ "ordSysId==[{}], status==[{}], direction==[{}], type==[{}], "
			+ "instrument -> {}, price -> {}, qty -> {}, timestamp -> {}, remark -> {}";

	@Override
	public void writeLog(Logger log, String msg) {
		log.info(OrderOutputTemplate, msg, ordSysId, status, direction, type, instrument, price, qty, timestamp,
				remark);
	}

}
