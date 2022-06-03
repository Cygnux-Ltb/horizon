package io.horizon.trader.order.attr;

import io.mercury.common.serialization.specific.JsonSerializable;

/**
 * 
 * @author yellow013
 *
 */
public final class OrdQty implements JsonSerializable {

	/*
	 * 委托数量
	 */
	private int offerQty;

	/*
	 * 剩余数量
	 */
	private int leavesQty;

	/*
	 * 已成交数量
	 */
	private int filledQty;

	/*
	 * 上一次成交数量
	 */
	private int lastFilledQty;

	private OrdQty(int offerQty) {
		this.offerQty = offerQty;
		this.leavesQty = offerQty;
	}

	public static final OrdQty withOffer(int offerQty) {
		return new OrdQty(offerQty);
	}

	public int getOfferQty() {
		return offerQty;
	}

	public int getLeavesQty() {
		return leavesQty;
	}

	public int getFilledQty() {
		return filledQty;
	}

	public int getLastFilledQty() {
		return lastFilledQty;
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
		var sb = new StringBuilder(90);
		sb.append(OfferQtyField);
		sb.append(offerQty);
		sb.append(LeavesQtyField);
		sb.append(leavesQty);
		sb.append(LastFilledQtyField);
		sb.append(lastFilledQty);
		sb.append(FilledQtyField);
		sb.append(filledQty);
		sb.append(End);
		return sb.toString();
	}

	@Override
	public String toJson() {
		return toString();
	}

}
