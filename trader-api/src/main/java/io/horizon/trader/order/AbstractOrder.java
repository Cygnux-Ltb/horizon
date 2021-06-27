package io.horizon.transaction.order;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.market.instrument.Instrument;
import io.horizon.transaction.order.OrdEnum.OrdStatus;
import io.horizon.transaction.order.OrdEnum.OrdType;
import io.horizon.transaction.order.OrdEnum.TrdDirection;
import lombok.Getter;

public abstract class AbstractOrder implements Order {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3444258095612091354L;

	// ordSysId
	@Getter
	protected final long ordSysId;

	// 策略Id
	@Getter
	protected final int strategyId;

	// 子账户Id
	@Getter
	protected final int subAccountId;

	// 实际账户Id
	@Getter
	protected final int accountId;

	// instrument
	@Getter
	protected final Instrument instrument;

	// 数量
	@Getter
	protected final OrdQty qty;

	// 价格
	@Getter
	protected final OrdPrice price;

	// 订单类型
	@Getter
	protected final OrdType type;

	// 订单方向
	@Getter
	protected final TrdDirection direction;

	// 时间戳
	@Getter
	protected final OrdTimestamp timestamp;

	// 订单状态(可变)
	@Getter
	protected OrdStatus status;

	// 订单备注(可变)
	@Getter
	protected String remark;

	// 默认备注
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
		this.timestamp = OrdTimestamp.newInstance();
		this.status = OrdStatus.PendingNew;
		this.remark = DefaultRemark;
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
