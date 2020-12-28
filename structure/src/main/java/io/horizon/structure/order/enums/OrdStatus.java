package io.horizon.definition.order.enums;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.util.BitOperator;

public enum OrdStatus {

	/**
	 * 非法
	 */
	Invalid(-1, true, "非法"),

	/**
	 * 新订单-未确认
	 */
	PendingNew(1, false, "未确认新订单"),

	/**
	 * 新订单
	 */
	New(1 << 1, false, "新订单"),

	/**
	 * 撤单-未确认
	 */
	PendingCancel(1 << 2, false, "未确认撤单"),

	/**
	 * 修改订单-未确认
	 */
	PendingReplace(1 << 3, false, "未确认修改订单"),

	/**
	 * 部分成交
	 */
	PartiallyFilled(1 << 4, false, "部分成交"),

	/**
	 * 全部成交
	 */
	Filled(1 << 5, true, "全部成交"),

	/**
	 * 已撤单
	 */
	Canceled(1 << 6, true, "已撤单"),

	/**
	 * 已修改
	 */
	Replaced(1 << 7, true, "已修改"),

	/**
	 * 新订单已拒绝
	 */
	NewRejected(1 << 8, true, "新订单已拒绝"),

	/**
	 * 撤单已拒绝
	 */
	CancelRejected(1 << 9, true, "撤单已拒绝"),

	/**
	 * 已暂停
	 */
	Suspended(1 << 10, false, "已暂停"),

	/**
	 * 未提供
	 */
	Unprovided(1 << 11, false, "未提供"),

	;

	private final int code;
	private final boolean finished;
	private final String desc;
	private final String fullInfo;

	/**
	 * 
	 * @param code       代码
	 * @param isFinished 是否为已结束状态
	 */
	private OrdStatus(int code, boolean finished, String desc) {
		this.code = code;
		this.finished = finished;
		this.desc = desc;
		this.fullInfo = name() + "::" + code + "[" + desc + "]";
	}

	public int code() {
		return code;
	}

	public boolean finished() {
		return finished;
	}

	public String desc() {
		return desc;
	}

	private static final Logger log = CommonLoggerFactory.getLogger(OrdStatus.class);

	public static OrdStatus valueOf(int code) {
		switch (code) {
		case -1:
			return Invalid;
		case 1:
			return PendingNew;
		case 1 << 1:
			return New;
		case 1 << 2:
			return PendingCancel;
		case 1 << 3:
			return PendingReplace;
		case 1 << 4:
			return PartiallyFilled;
		case 1 << 5:
			return Filled;
		case 1 << 6:
			return Canceled;
		case 1 << 7:
			return Replaced;
		case 1 << 8:
			return NewRejected;
		case 1 << 9:
			return CancelRejected;
		case 1 << 10:
			return Suspended;
		case 1 << 11:
			return Unprovided;
		default:
			log.error("OrdStatus.valueOf(code=={}) is no matches, return OrdStatus -> {} ", Invalid, code);
			return Invalid;
		}
	}

	@Override
	public String toString() {
		return fullInfo;
	}

	public static void main(String[] args) {
		System.out.println(BitOperator.intBinaryFormat(0));
		System.out.println(BitOperator.intBinaryFormat(Integer.MAX_VALUE));
		for (OrdStatus ordStatus : OrdStatus.values()) {
			System.out.println(ordStatus + " -> \n" + BitOperator.intBinaryFormat(ordStatus.code));
		}
	}

}
