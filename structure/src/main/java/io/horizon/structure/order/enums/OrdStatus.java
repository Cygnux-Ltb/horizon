package io.horizon.structure.order.enums;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.util.BitFormatter;

public enum OrdStatus {

	/**
	 * 非法
	 */
	Invalid(-1, true, "非法"),

	/**
	 * 新订单未确认
	 */
	PendingNew(1, false, "未确认新订单"),

	/**
	 * 新订单
	 */
	New(3, false, "新订单"),

	/**
	 * 新订单-已拒绝
	 */
	NewRejected(4, true, "新订单已拒绝"),

	/**
	 * 部分成交
	 */
	PartiallyFilled(5, false, "部分成交"),

	/**
	 * 全部成交
	 */
	Filled(7, true, "全部成交"),

	/**
	 * 未确认撤单
	 */
	PendingCancel(11, false, "未确认撤单"),

	/**
	 * 已撤单
	 */
	Canceled(15, true, "已撤单"),

	/**
	 * 撤单已拒绝
	 */
	CancelRejected(17, true, "撤单已拒绝"),

	/**
	 * 未确认修改订单
	 */
	PendingReplace(21, false, "未确认修改订单"),

	/**
	 * 已修改
	 */
	Replaced(25, true, "已修改"),

	/**
	 * 已暂停
	 */
	Suspended(31, false, "已暂停"),

	/**
	 * 未提供
	 */
	Unprovided(41, false, "未提供"),

	;

	private final int code;
	private final boolean finished;
	private final String desc;
	private final String fullInfo;

	/**
	 * 
	 * @param code     代码
	 * @param finished 是否为已结束状态
	 * @param desc     备注
	 */
	private OrdStatus(int code, boolean finished, String desc) {
		this.code = code;
		this.finished = finished;
		this.desc = desc;
		this.fullInfo = "[" + name() + "(" + code + "):" + desc + "]";
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

	@Override
	public String toString() {
		return fullInfo;
	}

	public static void main(String[] args) {
		System.out.println(BitFormatter.intBinaryFormat(0));
		System.out.println(BitFormatter.intBinaryFormat(Integer.MAX_VALUE));
		for (OrdStatus ordStatus : OrdStatus.values()) {
			System.out.println(ordStatus);
		}
	}

}
