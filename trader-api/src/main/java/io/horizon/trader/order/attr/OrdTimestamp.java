package io.horizon.trader.order.attr;

import javax.annotation.Nullable;

import io.mercury.common.datetime.Timestamp;

/**
 * 
 * @author yellow013
 *
 */

public final class OrdTimestamp {

	private final Timestamp generateTime;

	@Nullable
	private Timestamp sendTime;

	@Nullable
	private Timestamp firstReportTime;

	@Nullable
	private Timestamp finishTime;

	private OrdTimestamp() {
		this.generateTime = Timestamp.now();
	}

	/**
	 * 初始化订单生成时间
	 */
	public static OrdTimestamp now() {
		return new OrdTimestamp();
	}

	public Timestamp getGenerateTime() {
		return generateTime;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public Timestamp getFirstReportTime() {
		return firstReportTime;
	}

	public Timestamp getFinishTime() {
		return finishTime;
	}

	/**
	 * 添加发送时间
	 * 
	 * @return
	 */
	public OrdTimestamp addSendTime() {
		this.sendTime = Timestamp.now();
		return this;
	}

	/**
	 * 添加首次收到订单回报的时间
	 * 
	 * @return
	 */
	public OrdTimestamp addFirstReportTime() {
		this.firstReportTime = Timestamp.now();
		return this;
	}

	/**
	 * 添加最终完成时间
	 * 
	 * @return
	 */
	public OrdTimestamp addFinishTime() {
		this.finishTime = Timestamp.now();
		return this;
	}

}
