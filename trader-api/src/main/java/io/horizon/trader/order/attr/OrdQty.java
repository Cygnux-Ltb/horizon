package io.horizon.trader.order.attr;

import lombok.Getter;

/**
 * 
 * @author yellow013
 *
 */
public final class OrdQty {

	/*
	 * 委托数量
	 */
	@Getter
	private int offerQty;

	/*
	 * 剩余数量
	 */
	@Getter
	private int leavesQty;

	/*
	 * 已成交数量
	 */
	@Getter
	private int filledQty;

	/*
	 * 上一次成交数量
	 */
	@Getter
	private int lastFilledQty;

	private OrdQty(int offerQty) {
		this.offerQty = offerQty;
		this.leavesQty = offerQty;
	}

	public static final OrdQty withOffer(int offerQty) {
		return new OrdQty(offerQty);
	}

	/**
	 * 设置委托数量
	 * 
	 * @param offerQty
	 * @return
	 */
	public OrdQty setOfferQty(int offerQty) {
		if (this.offerQty == 0) {
			this.offerQty = offerQty;
			this.leavesQty = offerQty;
		}
		return this;
	}

	/**
	 * 设置已成交数量, 适用于在订单回报中返回此订单总成交数量的柜台
	 * 
	 * @param filledQty
	 * @return
	 */
	public OrdQty setFilledQty(int filledQty) {
		this.lastFilledQty = this.filledQty;
		this.filledQty = filledQty;
		this.leavesQty = offerQty - filledQty;
		return this;
	}

	/**
	 * 添加已成交数量, 适用于在订单回报中返回当次成交数量的柜台
	 * 
	 * @param filledQty
	 * @return
	 */
	public OrdQty addFilledQty(int filledQty) {
		return setFilledQty(this.filledQty + filledQty);
	}

	private static final String OfferQtyField = "{\"offerQty\" : ";
	private static final String LeavesQtyField = ", \"leavesQty\" : ";
	private static final String LastFilledQtyField = ", \"lastFilledQty\" : ";
	private static final String FilledQtyField = ", \"filledQty\" : ";
	private static final String End = "}";

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(90);
		builder.append(OfferQtyField);
		builder.append(offerQty);
		builder.append(LeavesQtyField);
		builder.append(leavesQty);
		builder.append(LastFilledQtyField);
		builder.append(lastFilledQty);
		builder.append(FilledQtyField);
		builder.append(filledQty);
		builder.append(End);
		return builder.toString();
	}

}
