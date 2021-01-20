package io.horizon.structure.order;

import static io.mercury.common.datetime.TimeConst.MILLIS_PER_SECONDS;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.collections.api.list.MutableList;
import org.slf4j.Logger;

import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.actual.ChildOrder;
import io.horizon.structure.order.enums.OrdStatus;
import io.horizon.structure.order.enums.OrdType;
import io.horizon.structure.order.enums.TrdDirection;
import io.mercury.common.datetime.EpochTime;
import io.mercury.common.datetime.TimeConst;
import io.mercury.common.datetime.TimeZone;
import io.mercury.common.datetime.Timestamp;
import io.mercury.common.util.BitFormatter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Order extends Comparable<Order>, Serializable {

	/**
	 * ordSysId构成<br>
	 * 策略Id | 时间戳Second | 自增量Number<br>
	 * strategyId | epochSecond| increment<br>
	 * 922 | 3372036854 | 775807<br>
	 * 
	 * TODO 使用雪花算法实现
	 * 
	 * @return long
	 */
	long getOrdSysId();

	/**
	 * strategyId
	 * 
	 * @return
	 */
	int getStrategyId();

	/**
	 * subAccountId
	 * 
	 * @return
	 */
	int getSubAccountId();

	/**
	 * accountId
	 * 
	 * @return
	 */
	int getAccountId();

	/**
	 * instrument
	 * 
	 * @return
	 */
	Instrument getInstrument();

	/**
	 * OrdQty
	 * 
	 * @return
	 */
	OrdQty getQty();

	/**
	 * OrdPrice
	 * 
	 * @return
	 */
	OrdPrice getPrice();

	/**
	 * OrdType
	 * 
	 * @return
	 */
	OrdType getType();

	/**
	 * 
	 * TrdDirection
	 * 
	 * @return
	 */
	TrdDirection getDirection();

	/**
	 * OrdTimestamp
	 * 
	 * @return
	 */
	OrdTimestamp getTimestamp();

	/**
	 * OrdStatus
	 * 
	 * @return current status
	 */
	OrdStatus getStatus();

	/**
	 * 
	 * @param ordStatus
	 */
	Order setStatus(OrdStatus status);

	/**
	 * remark
	 * 
	 * @return
	 */
	String getRemark();

	/**
	 * 
	 * @param remark
	 */
	Order setRemark(String remark);

	/**
	 * 
	 * @return
	 */
	int getOrdLevel();

	/**
	 * 
	 * @param log
	 * @param objName
	 * @param msg
	 */
	void writeLog(Logger log, String msg);

	@Override
	default int compareTo(Order o) {
		return getOrdLevel() > o.getOrdLevel() ? -1
				: getOrdLevel() < o.getOrdLevel() ? 1
						: getOrdSysId() < o.getOrdSysId() ? -1 : getOrdSysId() > o.getOrdSysId() ? 1 : 0;
	}

	/**
	 * 
	 * @author yellow013
	 *
	 */
	public static final class OrdQty {

		// 委托数量
		@Getter
		private int offerQty;

		// 剩余数量
		@Getter
		private int leavesQty;

		// 已成交数量
		@Getter
		private int filledQty;

		// 上一次成交数量
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

	/**
	 * 
	 * @author yellow013
	 *
	 */
	public static final class OrdPrice {

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

		public OrdPrice calculateAvgTradePrice(@Nonnull ChildOrder childOrder) {
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

	/**
	 * 
	 * @author yellow013
	 *
	 */
	@RequiredArgsConstructor
	public static final class OrdTimestamp {

		@Getter
		private final Timestamp generateTime;

		@Getter
		@Nullable
		private Timestamp sendingTime;

		@Getter
		@Nullable
		private Timestamp firstReportTime;

		@Getter
		@Nullable
		private Timestamp finishTime;

		private OrdTimestamp() {
			this.generateTime = Timestamp.newWithNow();
		}

		/**
		 * 初始化订单生成时间
		 */
		public static OrdTimestamp newTimestamp() {
			return new OrdTimestamp();
		}

		/**
		 * 补充发送时间
		 * 
		 * @return
		 */
		public OrdTimestamp fillSendingTime() {
			this.sendingTime = Timestamp.newWithNow();
			return this;
		}

		/**
		 * 补充首次收到订单回报的时间
		 * 
		 * @return
		 */
		public OrdTimestamp fillFirstReportTime() {
			this.firstReportTime = Timestamp.newWithNow();
			return this;
		}

		/**
		 * 补充最终完成时间
		 * 
		 * @return
		 */
		public OrdTimestamp fillFinishTime() {
			this.finishTime = Timestamp.newWithNow();
			return this;
		}

	}

	/**
	 * 
	 * Generate规则<br>
	 * <br>
	 * A方案<br>
	 * 1.获取当前epoch秒<br>
	 * 2.如果是同一秒内生成的两个id, 则自增位加一<br>
	 * <br>
	 * B方案<br>
	 * 1.使用一个固定日期作为基准<br>
	 * 2.使用一个较长的自增位<br>
	 * <br>
	 * C方案<br>
	 * 1.使用位运算合并long类型, 分配64位<br>
	 * 2.最高位使用strategyId <br>
	 * <br>
	 * 当前实现为方案A<br>
	 * 
	 * @author yellow013
	 * @creation 2019年4月13日
	 */
	@NotThreadSafe
	public final class OrdSysIdAllocator {

		/**
		 * 
		 * @param strategyId min value 1 max value 920
		 * @return
		 */
		public static long allocate(int strategyId) {
			if (strategyId < 0 || strategyId > Constant.MaxStrategyId)
				throw new RuntimeException("strategyId is illegal");
			return generate(strategyId);
		}

		public static long allocateWithExternal() {

			return generate(Constant.ProcessExternalOrderStrategyId);
		}

		private static volatile int increment;
		private static volatile long lastUseEpochSeconds;

		private static long generate(int highPos) {
			long epochSeconds = EpochTime.seconds();
			if (epochSeconds != lastUseEpochSeconds) {
				lastUseEpochSeconds = epochSeconds;
				increment = 0;
			}
			return highPos * 10_000_000_000_000_000L + lastUseEpochSeconds * 1_000_000L + ++increment;
		}

		/**
		 * 
		 */
		private static final long BASELINE_2020_SECONDS = 1577836800L;
		private static final long BASELINE_2020_MILLIS = BASELINE_2020_SECONDS * MILLIS_PER_SECONDS;

		/**
		 * 
		 */
		private static final long BASELINE_2010_SECONDS = 1262304000L;
		private static final long BASELINE_2010_MILLIS = BASELINE_2010_SECONDS * TimeConst.MILLIS_PER_SECONDS;

		/**
		 * 
		 */
		private static final long BASELINE_2000_SECONDS = 946684800L;
		private static final long BASELINE_2000_MILLIS = BASELINE_2000_SECONDS * TimeConst.MILLIS_PER_SECONDS;

		public static int analyzeStrategyId(long ordSysId) {
			return 0;
		}

		public static long analyzeEpochSeconds(long ordSysId) {
			return 0;
		}

		public static void main(String[] args) throws InterruptedException {

			System.out.println(9219999999999999999L);
			System.out.println(Long.MAX_VALUE);
			System.out.println("000" + EpochTime.seconds() + "000000");
			System.out.println("000" + EpochTime.millis() + "000000");
			System.out.println(allocate(800));
			System.out.println("uniqueId");
			System.out.println(BitFormatter.longBinaryFormat(Long.MAX_VALUE));
			System.out.println(Short.MAX_VALUE);
			System.out.println(BitFormatter.intBinaryFormat(Short.MAX_VALUE));
			System.out.println(BitFormatter.intBinaryFormat(999));

			System.out.println(Long.MAX_VALUE);
			System.out.println(Long.SIZE);
			System.out.println(Short.MAX_VALUE);
			System.out.println(Short.SIZE);

			System.out.println(24 * 60 * 60);

			System.out.println(Instant.ofEpochMilli(BASELINE_2000_MILLIS));
			System.out.println(Instant.ofEpochMilli(BASELINE_2010_MILLIS));
			System.out.println(Instant.ofEpochMilli(BASELINE_2020_MILLIS));
			System.out.println(Instant.ofEpochSecond(BASELINE_2000_SECONDS));
			System.out.println(Instant.ofEpochSecond(BASELINE_2010_SECONDS));
			System.out.println(Instant.ofEpochSecond(BASELINE_2020_MILLIS));

			System.out.println(ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0, TimeZone.UTC).toEpochSecond());
			System.out.println(ZonedDateTime.of(2010, 1, 1, 0, 0, 0, 0, TimeZone.UTC).toEpochSecond());
			System.out.println(ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, TimeZone.UTC).toEpochSecond());

		}
	}

	public static interface Constant {

		/**
		 * 系统可允许的最大策略ID
		 */
		int MaxStrategyId = 900;

		/**
		 * 接收到非系统报单的订单回报, 统一使用此策略ID, 用于根据订单回报创建订单, 并管理状态.
		 */
		int ProcessExternalOrderStrategyId = 910;

	}

}
