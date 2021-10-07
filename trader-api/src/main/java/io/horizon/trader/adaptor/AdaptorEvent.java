package io.horizon.trader.adaptor;

import io.horizon.trader.event.ControlEvent;
import io.mercury.serialization.json.JsonWrapper;

public class AdaptorEvent implements ControlEvent {

	/**
	 * adaptorId
	 */

	private final String adaptorId;

	/**
	 * status
	 */

	private final AdaptorStatus status;

	@Override
	public int getCode() {
		return status.code;
	}

	private String toStrCache;

	@Override
	public String toString() {
		return (toStrCache == null) ? toStrCache = JsonWrapper.toJson(this) : toStrCache;
	}

	public AdaptorEvent(String adaptorId, AdaptorStatus status) {
		this.adaptorId = adaptorId;
		this.status = status;
	}

	public String getAdaptorId() {
		return adaptorId;
	}

	public AdaptorStatus getStatus() {
		return status;
	}

	/**
	 * 
	 * @author yellow013
	 */

	public static enum AdaptorStatus {

		// 行情启用
		MdEnable(10),
		// 行情禁用
		MdDisable(11),
		// 交易启用
		TraderEnable(12),
		// 交易禁用
		TraderDisable(13),

		;

		private final int code;

		private AdaptorStatus(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

	}

}
