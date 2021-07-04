package io.horizon.trader.order;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface OrdEnum {

	static interface OrdConstant {
		// 无效
		int INVALID = -1;

		// 新订单未确认
		int PENDING_NEW = 1;
		// 新订单
		int NEW = 3;
		// 新订单已拒绝
		int NEW_REJECTED = 4;

		// 部分成交
		int PARTIALLY_FILLED = 5;
		// 全部成交
		int FILLED = 7;

		// 未确认撤单
		int PENDING_CANCEL = 11;
		// 已撤单
		int CANCELED = 15;
		// 撤单已拒绝
		int CANCEL_REJECTED = 17;

		// 未确认修改订单
		int PENDING_REPLACE = 21;

		// 已修改
		int REPLACED = 25;
		// 已暂停
		int SUSPENDED = 31;

		// 未提供
		int UNPROVIDED = 41;

		;

		// 买
		int BUY = 1;
		// 卖
		int SELL = 2;
		// 融资买入
		int MARGIN_BUY = 4;
		// 融券卖出
		int SHORT_SELL = 8;

	}

	public enum OrdStatus {

		// 无效
		Invalid(OrdConstant.INVALID, true),

		// 新订单未确认
		PendingNew(OrdConstant.PENDING_NEW, false),
		// 新订单
		New(OrdConstant.NEW, false),
		// 新订单已拒绝
		NewRejected(OrdConstant.NEW_REJECTED, true),

		// 部分成交
		PartiallyFilled(OrdConstant.PARTIALLY_FILLED, false),
		// 全部成交
		Filled(OrdConstant.FILLED, true),

		// 未确认撤单
		PendingCancel(OrdConstant.PENDING_CANCEL, false),
		// 已撤单
		Canceled(OrdConstant.CANCELED, true),
		// 撤单已拒绝
		CancelRejected(OrdConstant.CANCEL_REJECTED, true),

		// 未确认修改订单
		PendingReplace(OrdConstant.PENDING_REPLACE, false),

		// 已修改
		Replaced(OrdConstant.REPLACED, true),
		// 已暂停
		Suspended(OrdConstant.SUSPENDED, false),

		// 未提供
		Unprovided(OrdConstant.UNPROVIDED, false),

		;

		@Getter
		private final int code;
		@Getter
		private final boolean finished;

		/**
		 * 
		 * @param code     代码
		 * @param finished 是否为已结束状态
		 */
		private OrdStatus(int code, boolean finished) {
			this.code = code;
			this.finished = finished;
			this.str = name() + "[" + code + "-" + (finished ? "Finished" : "Unfinished") + "]";
		}

		private static final Logger log = CommonLoggerFactory.getLogger(OrdStatus.class);

		public static OrdStatus valueOf(int code) {
			switch (code) {
			// 未确认新订单
			case OrdConstant.PENDING_NEW:
				return PendingNew;
			// 新订单
			case OrdConstant.NEW:
				return New;
			// 新订单已拒绝
			case OrdConstant.NEW_REJECTED:
				return NewRejected;
			// 部分成交
			case OrdConstant.PARTIALLY_FILLED:
				return PartiallyFilled;
			// 全部成交
			case OrdConstant.FILLED:
				return Filled;
			// 未确认撤单
			case OrdConstant.PENDING_CANCEL:
				return PendingCancel;
			// 已撤单
			case OrdConstant.CANCELED:
				return Canceled;
			// 撤单已拒绝
			case OrdConstant.CANCEL_REJECTED:
				return CancelRejected;
			// 未确认修改订单
			case OrdConstant.PENDING_REPLACE:
				return PendingReplace;
			// 已修改
			case OrdConstant.REPLACED:
				return Replaced;
			// 已暂停
			case OrdConstant.SUSPENDED:
				return Suspended;
			// 未提供
			case OrdConstant.UNPROVIDED:
				return Unprovided;
			// 没有匹配项
			default:
				log.error("OrdStatus valueOf error, return OrdStatus -> [Invalid], param is {}", code);
				return Invalid;
			}
		}

		private final String str;

		@Override
		public String toString() {
			return str;
		}

	}

	public class OrdStatusException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4772495541311633988L;

		public OrdStatusException(String message) {
			super(message);
		}

	}

	public enum OrdSide {

		Invalid(OrdConstant.INVALID, TrdDirection.Invalid),

		Buy(OrdConstant.BUY, TrdDirection.Long),

		Sell(OrdConstant.SELL, TrdDirection.Short),

		MarginBuy(OrdConstant.MARGIN_BUY, TrdDirection.Long),

		ShortSell(OrdConstant.SHORT_SELL, TrdDirection.Short),

		;

		private OrdSide(int code, TrdDirection direction) {
			this.code = code;
			this.direction = direction;
			this.str = name() + "[" + code + "-" + direction + "]";
		}

		@Getter
		private final int code;

		@Getter
		private final TrdDirection direction;

		private static final Logger log = CommonLoggerFactory.getLogger(OrdSide.class);

		public static OrdSide valueOf(int code) {
			switch (code) {
			case OrdConstant.BUY:
				return Buy;
			case OrdConstant.SELL:
				return Sell;
			case OrdConstant.MARGIN_BUY:
				return MarginBuy;
			case OrdConstant.SHORT_SELL:
				return ShortSell;
			default:
				log.error("OrdSide valueOf error, return OrdSide -> [Invalid], param is {}", code);
				return Invalid;
			}
		}

		private final String str;

		@Override
		public String toString() {
			return str;
		}

	}

	@RequiredArgsConstructor
	public enum OrdLevel {

		Child(1), Parent(2), Strategy(4), Group(8);

		@Getter
		private final int code;

	}

	@RequiredArgsConstructor
	public enum OrdType {

		Invalid(-1), Limit(1), Market(2), Stop(4), StopLimit(8), FOK(16), FAK(32);

		@Getter
		private final int code;

	}

	@RequiredArgsConstructor
	public enum TrdAction {

		Invalid(-1), Open(1), Close(2), CloseToday(4), CloseYesterday(8);

		@Getter
		private final int code;

	}

	@RequiredArgsConstructor
	public enum TrdDirection {

		Invalid(-1), Long(1), Short(2);

		@Getter
		private final int code;

	}

}
