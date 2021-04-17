package io.horizon.transaction.order;

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
		this.generateTime = Timestamp.newWithNow();
	}

	/**
	 * 初始化订单生成时间
	 */
	public static OrdTimestamp newInstance() {
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

}
