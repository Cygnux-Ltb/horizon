package io.horizon.trader.order.attr;

import javax.annotation.Nonnull;

import io.horizon.trader.order.ChildOrder;
import io.horizon.trader.order.TrdRecord;
import org.eclipse.collections.api.list.MutableList;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 
 * @author yellow013
 *
 */
public final class OrdPrice {

	// 委托价格
	@Getter
	@Setter
	@Accessors(chain = true)
	private long offerPrice;

	// 成交均价
	@Getter
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

}
