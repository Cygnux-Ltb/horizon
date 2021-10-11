package io.horizon.trader.order.attr;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;

public enum OrdStatus {

	// 无效
	Invalid(Const.INVALID, true),

	// 新订单未确认
	PendingNew(Const.PENDING_NEW, false),
	// 新订单
	New(Const.NEW, false),
	// 新订单已拒绝
	NewRejected(Const.NEW_REJECTED, true),

	// 部分成交
	PartiallyFilled(Const.PARTIALLY_FILLED, false),
	// 全部成交
	Filled(Const.FILLED, true),

	// 未确认撤单
	PendingCancel(Const.PENDING_CANCEL, false),
	// 已撤单
	Canceled(Const.CANCELED, true),
	// 撤单已拒绝
	CancelRejected(Const.CANCEL_REJECTED, true),

	// 未确认修改订单
	PendingReplace(Const.PENDING_REPLACE, false),

	// 已修改
	Replaced(Const.REPLACED, true),
	// 已暂停
	Suspended(Const.SUSPENDED, false),

	// 未提供
	Unprovided(Const.UNPROVIDED, false),

	;

	private final char code;

	private final boolean finished;

	private static final Logger log = CommonLoggerFactory.getLogger(OrdStatus.class);

	/**
	 * 
	 * @param code     代码
	 * @param finished 是否为已结束状态
	 */
	private OrdStatus(char code, boolean finished) {
		this.code = code;
		this.finished = finished;
		this.str = name() + "[" + code + "-" + (finished ? "Finished" : "Unfinished") + "]";
	}

	public char getCode() {
		return code;
	}

	public boolean isFinished() {
		return finished;
	}

	public static OrdStatus valueOf(char code) {
		// 未确认新订单
		if (code == Const.PENDING_NEW)
			return PendingNew;
		// 新订单
		if (code == Const.NEW)
			return New;
		// 新订单已拒绝
		if (code == Const.NEW_REJECTED)
			return NewRejected;
		// 部分成交
		if (code == Const.PARTIALLY_FILLED)
			return PartiallyFilled;
		// 全部成交
		if (code == Const.FILLED)
			return Filled;
		// 未确认撤单
		if (code == Const.PENDING_CANCEL)
			return PendingCancel;
		// 已撤单
		if (code == Const.CANCELED)
			return Canceled;
		// 撤单已拒绝
		if (code == Const.CANCEL_REJECTED)
			return CancelRejected;
		// 未确认修改订单
		if (code == Const.PENDING_REPLACE)
			return PendingReplace;
		// 已修改
		if (code == Const.REPLACED)
			return Replaced;
		// 已暂停
		if (code == Const.SUSPENDED)
			return Suspended;
		// 未提供
		if (code == Const.UNPROVIDED)
			return Unprovided;
		// 没有匹配项
		log.error("OrdStatus valueOf error, return OrdStatus -> [Invalid], param is {}", code);
		return Invalid;

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

	private interface Const {
		// 无效
		char INVALID = 'I';

		// 新订单未确认
		char PENDING_NEW = 'P';
		// 新订单
		char NEW = 'N';
		// 新订单已拒绝
		char NEW_REJECTED = 'R';

		// 部分成交
		char PARTIALLY_FILLED = 'D';
		// 全部成交
		char FILLED = 'F';

		// 未确认撤单
		char PENDING_CANCEL = 'C';
		// 已撤单
		char CANCELED = 'X';
		// 撤单已拒绝
		char CANCEL_REJECTED = 'Y';

		// 未确认修改订单
		char PENDING_REPLACE = 'Q';
		// 已修改
		char REPLACED = 'R';

		// 已暂停
		char SUSPENDED = 'S';
		// 未提供
		char UNPROVIDED = 'U';
	};

}
