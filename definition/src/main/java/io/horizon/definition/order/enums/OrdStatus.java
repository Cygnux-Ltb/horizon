package io.horizon.definition.order.enums;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.util.BitOperator;

public enum OrdStatus {

	Invalid(-1, true),

	PendingNew(1, false),

	New(1 << 1, false),

	PendingCancel(1 << 2, false),

	PendingReplace(1 << 3, false),

	PartiallyFilled(1 << 4, false),

	Filled(1 << 5, true),

	Canceled(1 << 6, true),

	Replaced(1 << 7, true),

	NewRejected(1 << 8, true),

	CancelRejected(1 << 9, true),

	Suspended(1 << 10, false),

	Unprovided(1 << 11, false),

	;

	private final int code;
	private final boolean finished;
	private final String fullInfo;

	/**
	 * 
	 * @param code       代码
	 * @param isFinished 是否为已结束状态
	 */
	private OrdStatus(int code, boolean finished) {
		this.code = code;
		this.finished = finished;
		this.fullInfo = name() + "[" + code + "]";
	}

	public int code() {
		return code;
	}

	public boolean finished() {
		return finished;
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
