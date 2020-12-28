package io.horizon.definition.adaptor;

import io.horizon.definition.event.ControlEvent;
import io.mercury.serialization.json.JsonWrapper;

public final class AdaptorEvent implements ControlEvent {

	/**
	 * adaptorId
	 */
	private final int adaptorId;

	/**
	 * status
	 */
	private final AdaptorStatus status;

	public AdaptorEvent(int adaptorId, AdaptorStatus status) {
		this.adaptorId = adaptorId;
		this.status = status;
	}

	public int adaptorId() {
		return adaptorId;
	}

	public AdaptorStatus status() {
		return status;
	}

	@Override
	public int code() {
		return status.code;
	}

	private String toStringCache;

	@Override
	public String toString() {
		if (toStringCache == null) {
			toStringCache = JsonWrapper.toJson(this);
		}
		return toStringCache;
	}

	/**
	 * 
	 * @author yellow013
	 *
	 */
	public static enum AdaptorStatus {

		MdEnable(10),

		MdDisable(11),

		TraderEnable(12),

		TraderDisable(13),

		;

		private int code;

		private AdaptorStatus(int code) {
			this.code = code;
		}

		public int code() {
			return code;
		}

	}

}
