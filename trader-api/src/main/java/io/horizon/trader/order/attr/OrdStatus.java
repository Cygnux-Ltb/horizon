package io.horizon.trader.order.attr;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;
import lombok.Getter;

public enum OrdStatus {

	// 无效
	Invalid(OrdStatusConst.INVALID, true),

	// 新订单未确认
	PendingNew(OrdStatusConst.PENDING_NEW, false),
	// 新订单
	New(OrdStatusConst.NEW, false),
	// 新订单已拒绝
	NewRejected(OrdStatusConst.NEW_REJECTED, true),

	// 部分成交
	PartiallyFilled(OrdStatusConst.PARTIALLY_FILLED, false),
	// 全部成交
	Filled(OrdStatusConst.FILLED, true),

	// 未确认撤单
	PendingCancel(OrdStatusConst.PENDING_CANCEL, false),
	// 已撤单
	Canceled(OrdStatusConst.CANCELED, true),
	// 撤单已拒绝
	CancelRejected(OrdStatusConst.CANCEL_REJECTED, true),

	// 未确认修改订单
	PendingReplace(OrdStatusConst.PENDING_REPLACE, false),

	// 已修改
	Replaced(OrdStatusConst.REPLACED, true),
	// 已暂停
	Suspended(OrdStatusConst.SUSPENDED, false),

	// 未提供
	Unprovided(OrdStatusConst.UNPROVIDED, false),

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
		case OrdStatusConst.PENDING_NEW:
			return PendingNew;
		// 新订单
		case OrdStatusConst.NEW:
			return New;
		// 新订单已拒绝
		case OrdStatusConst.NEW_REJECTED:
			return NewRejected;
		// 部分成交
		case OrdStatusConst.PARTIALLY_FILLED:
			return PartiallyFilled;
		// 全部成交
		case OrdStatusConst.FILLED:
			return Filled;
		// 未确认撤单
		case OrdStatusConst.PENDING_CANCEL:
			return PendingCancel;
		// 已撤单
		case OrdStatusConst.CANCELED:
			return Canceled;
		// 撤单已拒绝
		case OrdStatusConst.CANCEL_REJECTED:
			return CancelRejected;
		// 未确认修改订单
		case OrdStatusConst.PENDING_REPLACE:
			return PendingReplace;
		// 已修改
		case OrdStatusConst.REPLACED:
			return Replaced;
		// 已暂停
		case OrdStatusConst.SUSPENDED:
			return Suspended;
		// 未提供
		case OrdStatusConst.UNPROVIDED:
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

	public static class OrdStatusException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4772495541311633988L;

		public OrdStatusException(String message) {
			super(message);
		}

	}

	private interface OrdStatusConst {
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
	};

}
