package io.horizon.structure.order;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.mercury.common.datetime.Timestamp;

public final class OrdTimestamp {

	private final Timestamp generateTime;
	private Timestamp sendingTime;
	private Timestamp firstReportTime;
	private Timestamp finishTime;

	private OrdTimestamp() {
		this.generateTime = Timestamp.newWithNow();
	}

	/**
	 * 初始化订单生成时间
	 */
	public static OrdTimestamp generate() {
		return new OrdTimestamp();
	}

	/**
	 * 补充发送时间
	 * 
	 * @return
	 */
	public OrdTimestamp fillSendingTime() {
		this.sendingTime = Timestamp.newWithNow();
		return this;
	}

	/**
	 * 补充首次收到订单回报的时间
	 * 
	 * @return
	 */
	public OrdTimestamp fillFirstReportTime() {
		this.firstReportTime = Timestamp.newWithNow();
		return this;
	}

	/**
	 * 补充最终完成时间
	 * 
	 * @return
	 */
	public OrdTimestamp fillFinishTime() {
		this.finishTime = Timestamp.newWithNow();
		return this;
	}

	@Nonnull
	public Timestamp generateTime() {
		return generateTime;
	}

	@Nullable
	public Timestamp sendingTime() {
		return sendingTime;
	}

	@Nullable
	public Timestamp firstReportTime() {
		return firstReportTime;
	}

	@Nullable
	public Timestamp finishTime() {
		return finishTime;
	}

}
