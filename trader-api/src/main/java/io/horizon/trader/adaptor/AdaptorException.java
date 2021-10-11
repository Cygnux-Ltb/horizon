package io.horizon.trader.adaptor;

public class AdaptorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7012414724771372952L;

	private final String adaptorId;

	public AdaptorException(String adaptorId, Throwable throwable) {
		super("Adaptor exception from adaptorId -> " + adaptorId, throwable);
		this.adaptorId = adaptorId;
	}

	public String getAdaptorId() {
		return adaptorId;
	}

}
