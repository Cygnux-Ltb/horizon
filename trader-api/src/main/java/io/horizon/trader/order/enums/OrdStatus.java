package io.horizon.trader.order.enums;

import org.slf4j.Logger;

import io.horizon.trader.transport.enums.TOrdStatus;
import io.mercury.common.log.Log4j2LoggerFactory;

public enum OrdStatus {

	/**
	 * 无效
	 */
	Invalid(OrdStatusCode.INVALID, TOrdStatus.INVALID, true),

	/**
	 * 新订单未确认
	 */
	PendingNew(OrdStatusCode.PENDING_NEW, TOrdStatus.PENDING_NEW, false),
	/**
	 * 新订单
	 */
	New(OrdStatusCode.NEW, TOrdStatus.NEW, false),
	/**
	 * 新订单已拒绝
	 */
	NewRejected(OrdStatusCode.NEW_REJECTED, TOrdStatus.NEW_REJECTED, true),

	/**
	 * 部分成交
	 */
	PartiallyFilled(OrdStatusCode.PARTIALLY_FILLED, TOrdStatus.PARTIALLY_FILLED, false),
	/**
	 * 全部成交
	 */
	Filled(OrdStatusCode.FILLED, TOrdStatus.FILLED, true),

	/**
	 * 未确认撤单
	 */
	PendingCancel(OrdStatusCode.PENDING_CANCEL, TOrdStatus.PENDING_CANCEL, false),
	/**
	 * 已撤单
	 */
	Canceled(OrdStatusCode.CANCELED, TOrdStatus.CANCELED, true),
	/**
	 * 撤单已拒绝
	 */
	CancelRejected(OrdStatusCode.CANCEL_REJECTED, TOrdStatus.CANCEL_REJECTED, true),

	/**
	 * 未确认修改订单
	 */
	PendingReplace(OrdStatusCode.PENDING_REPLACE, TOrdStatus.PENDING_REPLACE, false),

	/**
	 * 已修改
	 */
	Replaced(OrdStatusCode.REPLACED, TOrdStatus.REPLACED, true),
	/**
	 * 已暂停
	 */
	Suspended(OrdStatusCode.SUSPENDED, TOrdStatus.SUSPENDED, false),

	/**
	 * 未提供
	 */
	Unprovided(OrdStatusCode.UNPROVIDED, TOrdStatus.UNPROVIDED, false),

	;

	private final char code;

	private final TOrdStatus tOrdStatus;

	private final boolean finished;

	private final String str;

	private static final Logger log = Log4j2LoggerFactory.getLogger(OrdStatus.class);

	/**
	 * 
	 * @param code
	 * @param finished
	 */
	/**
	 * 
	 * @param code       代码
	 * @param tOrdStatus Avro状态
	 * @param finished   是否为已结束状态
	 */
	private OrdStatus(char code, TOrdStatus tOrdStatus, boolean finished) {
		this.code = code;
		this.tOrdStatus = tOrdStatus;
		this.finished = finished;
		this.str = name() + "[" + code + "-" + (finished ? "Finished" : "Unfinished") + "]";
	}

	public char getCode() {
		return code;
	}

	public boolean isFinished() {
		return finished;
	}

	public TOrdStatus getTOrdStatus() {
		return tOrdStatus;
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
		case OrdStatusCode.PENDING_NEW:
			return PendingNew;
		// 新订单
		case OrdStatusCode.NEW:
			return New;
		// 新订单已拒绝
		case OrdStatusCode.NEW_REJECTED:
			return NewRejected;
		// 部分成交
		case OrdStatusCode.PARTIALLY_FILLED:
			return PartiallyFilled;
		// 全部成交
		case OrdStatusCode.FILLED:
			return Filled;
		// 未确认撤单
		case OrdStatusCode.PENDING_CANCEL:
			return PendingCancel;
		// 已撤单
		case OrdStatusCode.CANCELED:
			return Canceled;
		// 撤单已拒绝
		case OrdStatusCode.CANCEL_REJECTED:
			return CancelRejected;
		// 未确认修改订单
		case OrdStatusCode.PENDING_REPLACE:
			return PendingReplace;
		// 已修改
		case OrdStatusCode.REPLACED:
			return Replaced;
		// 已暂停
		case OrdStatusCode.SUSPENDED:
			return Suspended;
		// 未提供
		case OrdStatusCode.UNPROVIDED:
			return Unprovided;
		// 没有匹配项
		default:
			log.error("OrdStatus valueOf error, return OrdStatus -> [Invalid], input code==[{}]", code);
			return Invalid;
		}
	}

	/**
	 * 
	 * @param status
	 * @return
	 */
	public static OrdStatus valueOf(TOrdStatus status) {
		switch (status) {
		// 未确认新订单
		case PENDING_NEW:
			return PendingNew;
		// 新订单
		case NEW:
			return New;
		// 新订单已拒绝
		case NEW_REJECTED:
			return NewRejected;
		// 部分成交
		case PARTIALLY_FILLED:
			return PartiallyFilled;
		// 全部成交
		case FILLED:
			return Filled;
		// 未确认撤单
		case PENDING_CANCEL:
			return PendingCancel;
		// 已撤单
		case CANCELED:
			return Canceled;
		// 撤单已拒绝
		case CANCEL_REJECTED:
			return CancelRejected;
		// 未确认修改订单
		case PENDING_REPLACE:
			return PendingReplace;
		// 已修改
		case REPLACED:
			return Replaced;
		// 已暂停
		case SUSPENDED:
			return Suspended;
		// 未提供
		case UNPROVIDED:
			return Unprovided;
		// 没有匹配项
		default:
			log.error("OrdStatus valueOf error, return OrdStatus -> [Invalid], input EStatus==[{}]", status);
			return Invalid;
		}
	}

	private interface OrdStatusCode {
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
		// 已停止
		// char STOPPED = '7';
		// 已过期
		// char EXPIRED = 'C';
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
