package io.horizon.trader.order.enums;

import org.slf4j.Logger;

import io.mercury.common.log.Log4j2LoggerFactory;

public enum OrdStatus {

	/**
	 * 无效
	 */
	Invalid(Code.INVALID, true),

	/**
	 * 新订单未确认
	 */
	PendingNew(Code.PENDING_NEW, false),
	/**
	 * 新订单
	 */
	New(Code.NEW, false),
	/**
	 * 新订单已拒绝
	 */
	NewRejected(Code.NEW_REJECTED, true),

	/**
	 * 部分成交
	 */
	PartiallyFilled(Code.PARTIALLY_FILLED, false),
	/**
	 * 全部成交
	 */
	Filled(Code.FILLED, true),

	/**
	 * 未确认撤单
	 */
	PendingCancel(Code.PENDING_CANCEL, false),
	/**
	 * 已撤单
	 */
	Canceled(Code.CANCELED, true),
	/**
	 * 撤单已拒绝
	 */
	CancelRejected(Code.CANCEL_REJECTED, true),

	/**
	 * 未确认修改订单
	 */
	PendingReplace(Code.PENDING_REPLACE, false),

	/**
	 * 已修改
	 */
	Replaced(Code.REPLACED, true),
	/**
	 * 已暂停
	 */
	Suspended(Code.SUSPENDED, false),

	/**
	 * 未提供
	 */
	Unprovided(Code.UNPROVIDED, false),

	;

	private final char code;

	private final boolean finished;

	private final String str;

	private static final Logger log = Log4j2LoggerFactory.getLogger(OrdStatus.class);

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

	@Override
	public String toString() {
		return str;
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public static OrdStatus valueOf(int code) {
		switch (code) {
		// 未确认新订单
		case Code.PENDING_NEW:
			return PendingNew;
		// 新订单
		case Code.NEW:
			return New;
		// 新订单已拒绝
		case Code.NEW_REJECTED:
			return NewRejected;
		// 部分成交
		case Code.PARTIALLY_FILLED:
			return PartiallyFilled;
		// 全部成交
		case Code.FILLED:
			return Filled;
		// 未确认撤单
		case Code.PENDING_CANCEL:
			return PendingCancel;
		// 已撤单
		case Code.CANCELED:
			return Canceled;
		// 撤单已拒绝
		case Code.CANCEL_REJECTED:
			return CancelRejected;
		// 未确认修改订单
		case Code.PENDING_REPLACE:
			return PendingReplace;
		// 已修改
		case Code.REPLACED:
			return Replaced;
		// 已暂停
		case Code.SUSPENDED:
			return Suspended;
		// 未提供
		case Code.UNPROVIDED:
			return Unprovided;
		// 没有匹配项
		default:
			log.error("OrdStatus valueOf error, return OrdStatus -> [Invalid], input code==[{}]", code);
			return Invalid;
		}
	}

	private interface Code {
		// 无效
		char INVALID = 'I';

		// 新订单未确认
		char PENDING_NEW = 'P';
		// 新订单
		char NEW = 'N';
		// 新订单已拒绝
		char NEW_REJECTED = 'R';

		// 部分成交
		char PARTIALLY_FILLED = 'A';
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
		char REPLACED = 'E';

		// 已暂停
		char SUSPENDED = 'S';
		// 未提供
		char UNPROVIDED = 'U';
	}

	/**
	 * OrdStatusException
	 * 
	 * @author yellow013
	 */
	public static class OrdStatusException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4772495541311633988L;

		public OrdStatusException(String message) {
			super(message);
		}

	}

}
