package io.horizon.structure.order;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.enums.OrdStatus;
import io.horizon.structure.order.enums.OrdType;
import io.horizon.structure.order.enums.TrdDirection;
import lombok.Getter;

public abstract class OrderBasicImpl implements Order {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3444258095612091354L;

	// ordId
	@Getter
	private final long ordId;

	// 策略Id
	@Getter
	private final int strategyId;

	// 子账户Id
	@Getter
	private final int subAccountId;

	// 实际账户Id
	@Getter
	private final int accountId;

	// instrument
	@Getter
	private final Instrument instrument;

	// 数量
	@Getter
	private final OrdQty qty;

	// 价格
	@Getter
	private final OrdPrice price;

	// 订单类型
	@Getter
	private final OrdType type;

	// 订单方向
	@Getter
	private final TrdDirection direction;

	// 时间戳
	@Getter
	private final OrdTimestamp timestamp;

	// 订单状态(可变)
	@Getter
	private OrdStatus status;

	// 订单备注(可变)
	@Getter
	private String remark;

	private static final String DefaultRemark = "NONE";

	/**
	 * 
	 * @param ordId
	 * @param strategyId
	 * @param subAccountId
	 * @param accountId
	 * @param instrument
	 * @param qty
	 * @param price
	 * @param type
	 * @param direction
	 */
	protected OrderBasicImpl(long ordId, int strategyId, int subAccountId, int accountId,
			@Nonnull Instrument instrument, @Nonnull OrdQty qty, @Nonnull OrdPrice price, @Nonnull OrdType type,
			@Nonnull TrdDirection direction) {
		this.ordId = ordId;
		this.strategyId = strategyId;
		this.subAccountId = subAccountId;
		this.accountId = accountId;
		this.instrument = instrument;
		this.qty = qty;
		this.price = price;
		this.type = type;
		this.direction = direction;
		this.timestamp = OrdTimestamp.generate();
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

	private static final String OrderOutputText = "{} :: {}, Order attr : ordId==[{}], status==[{}], "
			+ "direction==[{}], type==[{}], instrument -> {}, price -> {}, qty -> {}, timestamp -> {}";

	@Override
	public void writeLog(Logger log, String objName, String msg) {
		log.info(OrderOutputText, objName, msg, getOrdId(), getStatus(), getDirection(), getType(), getInstrument(),
				getPrice(), getQty(), getTimestamp());
	}

}
