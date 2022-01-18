package io.horizon.trader.order.attr;

import javax.annotation.Nullable;

import io.mercury.common.datetime.Epochs;

/**
 * 时间单位为Epoch微秒
 * 
 * @author yellow013
 */
public final class OrdTimestamp {

	private final long generateTime;

	@Nullable
	private long sendTime;

	@Nullable
	private long firstReportTime;

	@Nullable
	private long finishTime;

	private OrdTimestamp() {
		this.generateTime = Epochs.getEpochMicros();
	}

	/**
	 * 初始化订单生成时间
	 */
	public static OrdTimestamp now() {
		return new OrdTimestamp();
	}

	public long getGenerateTime() {
		return generateTime;
	}

	public long getSendTime() {
		return sendTime;
	}

	public long getFirstReportTime() {
		return firstReportTime;
	}

	public long getFinishTime() {
		return finishTime;
	}

	/**
	 * 添加发送时间
	 * 
	 * @return
	 */
	public OrdTimestamp addSendTime() {
		this.sendTime = Epochs.getEpochMicros();
		return this;
	}

	/**
	 * 添加首次收到订单回报的时间
	 * 
	 * @return
	 */
	public OrdTimestamp addFirstReportTime() {
		this.firstReportTime = Epochs.getEpochMicros();
		return this;
	}

	/**
	 * 添加最终完成时间
	 * 
	 * @return
	 */
	public OrdTimestamp addFinishTime() {
		this.finishTime = Epochs.getEpochMicros();
		return this;
	}

}
