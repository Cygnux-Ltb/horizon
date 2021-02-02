package io.horizon.structure.order.enums;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.util.BitFormatter;
import lombok.Getter;

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
		this.toStringInfo = name() + "(" + code + ")";
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

	private final String toStringInfo;

	@Override
	public String toString() {
		return toStringInfo;
	}

	public static void main(String[] args) {
		System.out.println(BitFormatter.intBinaryFormat(0));
		System.out.println(BitFormatter.intBinaryFormat(Integer.MAX_VALUE));
		for (OrdStatus ordStatus : OrdStatus.values()) {
			System.out.println(ordStatus);
		}
	}

}
