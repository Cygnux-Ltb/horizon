package io.horizon.trader.order.attr;

import javax.annotation.Nullable;

import io.mercury.common.datetime.Timestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author yellow013
 *
 */
@RequiredArgsConstructor
public final class OrdTimestamp {

	@Getter
	private final Timestamp generateTime;

	@Getter
	@Nullable
	private Timestamp sendingTime;

	@Getter
	@Nullable
	private Timestamp firstReportTime;

	@Getter
	@Nullable
	private Timestamp finishTime;

	private OrdTimestamp() {
		this.generateTime = Timestamp.now();
	}

	/**
	 * 初始化订单生成时间
	 */
	public static OrdTimestamp newInstance() {
		return new OrdTimestamp();
	}

	/**
	 * 添加发送时间
	 * 
	 * @return
	 */
	public OrdTimestamp addSendingTime() {
		this.sendingTime = Timestamp.now();
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
