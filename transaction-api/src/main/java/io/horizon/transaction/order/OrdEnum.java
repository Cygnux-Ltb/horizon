package io.horizon.transaction.order;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface OrdEnum {

	public enum OrdStatus {

		// 无效
		Invalid(-1, true),

		// 新订单未确认
		PendingNew(1, false),
		// 新订单
		New(3, false),
		// 新订单已拒绝
		NewRejected(4, true),

		// 部分成交
		PartiallyFilled(5, false),
		// 全部成交
		Filled(7, true),

		// 未确认撤单
		PendingCancel(11, false),
		// 已撤单
		Canceled(15, true),
		// 撤单已拒绝
		CancelRejected(17, true),

		// 未确认修改订单
		PendingReplace(21, false),

		// 已修改
		Replaced(25, true),
		// 已暂停
		Suspended(31, false),

		// 未提供
		Unprovided(41, false),

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
			this.toString = name() + "(" + code + ")";
		}

		private static final Logger log = CommonLoggerFactory.getLogger(OrdStatus.class);

		public static OrdStatus valueOf(int code) {
			switch (code) {
			// 非法
			case -1:
				return Invalid;
			// 未确认新订单
			case 1:
				return PendingNew;
			// 新订单
			case 3:
				return New;
			// 新订单已拒绝
			case 4:
				return NewRejected;
			// 部分成交
			case 5:
				return PartiallyFilled;
			// 全部成交
			case 7:
				return Filled;
			// 未确认撤单
			case 11:
				return PendingCancel;
			// 已撤单
			case 15:
				return Canceled;
			// 撤单已拒绝
			case 17:
				return CancelRejected;
			// 未确认修改订单
			case 21:
				return PendingReplace;
			// 已修改
			case 25:
				return Replaced;
			// 已暂停
			case 31:
				return Suspended;
			// 未提供
			case 41:
				return Unprovided;
			// 没有匹配项
			default:
				log.error("OrdStatus.valueOf(code=={}) is no matches, return OrdStatus -> Invalid", Invalid, code);
				return Invalid;
			}
		}

		private final String toString;

		@Override
		public String toString() {
			return toString;
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

	@RequiredArgsConstructor
	public enum OrdSide {

		Invalid(-1, TrdDirection.Invalid),

		Buy(1, TrdDirection.Long),

		Sell(2, TrdDirection.Short),

		MarginBuy(4, TrdDirection.Long),

		ShortSell(8, TrdDirection.Short),

		;

		@Getter
		private final int code;

		@Getter
		private final TrdDirection direction;

		private static final Logger log = CommonLoggerFactory.getLogger(OrdSide.class);

		public static OrdSide valueOf(int code) {
			switch (code) {
			case 1:
				return Buy;
			case 2:
				return Sell;
			case 4:
				return MarginBuy;
			case 8:
				return ShortSell;
			default:
				log.error("OrdSide.valueOf(code=={}) -> is no matches, return OrdSide.Invalid", code);
				return Invalid;
			}
		}

	}

	@RequiredArgsConstructor
	public enum OrdLevel {

		Group(1), Strategy(2), Parent(4), Child(8);

		@Getter
		private final int code;

	}

	@RequiredArgsConstructor
	public enum OrdType {

		Invalid(-1), Limit(1), Market(1 << 1), Stop(1 << 2), StopLimit(1 << 3), FOK(1 << 4), FAK(1 << 5);

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
