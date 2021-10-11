package io.horizon.trader.order.attr;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.MutableList;

import io.horizon.trader.order.ChildOrder;
import io.horizon.trader.order.TrdRecord;
import io.mercury.common.serialization.JsonSerializable;

/**
 * 
 * @author yellow013
 *
 */
public final class OrdPrice implements JsonSerializable {

	/*
	 * 委托价格
	 */

	private long offerPrice;

	/*
	 * 成交均价
	 */

	private long avgTradePrice;

	private OrdPrice() {
	}

	private OrdPrice(long offerPrice) {
		this.offerPrice = offerPrice;
	}

	public static OrdPrice withEmpty() {
		return new OrdPrice();
	}

	public static OrdPrice withOffer(long offerPrice) {
		return new OrdPrice(offerPrice);
	}

	public long getOfferPrice() {
		return offerPrice;
	}

	public long getAvgTradePrice() {
		return avgTradePrice;
	}

	public OrdPrice setOfferPrice(long offerPrice) {
		this.offerPrice = offerPrice;
		return this;
	}

	public OrdPrice calcAvgTradePrice(@Nonnull ChildOrder childOrder) {
		MutableList<TrdRecord> trdRecords = childOrder.getTrdRecords();
		if (!trdRecords.isEmpty()) {
			// 计算总成交金额
			long totalTurnover = trdRecords.sumOfLong(trade -> trade.getTrdPrice() * trade.getTrdQty());
			// 计算总成交量
			long totalQty = trdRecords.sumOfInt(trade -> trade.getTrdQty());
			if (totalQty > 0L)
				this.avgTradePrice = totalTurnover / totalQty;
			return this;
		}
		return this;
	}

	private static final String OfferPriceField = "{\"offerPrice\" : ";
	private static final String AvgTradePriceField = ", \"trdAvgPrice\" : ";
	private static final String End = "}";

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(75);
		builder.append(OfferPriceField);
		builder.append(offerPrice);
		builder.append(AvgTradePriceField);
		builder.append(avgTradePrice);
		builder.append(End);
		return builder.toString();
	}

	@Override
	public String toJson() {
		return toString();
	}

}
