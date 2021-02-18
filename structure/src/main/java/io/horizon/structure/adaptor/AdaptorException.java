package io.horizon.structure.adaptor;

public class AdaptorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7012414724771372952L;

	public AdaptorException(int adaptorId, String adaptorName, Throwable throwable) {
		super("Adaptor exception, adaptorId -> " + adaptorId + ", adaptorName -> " + adaptorName,
				throwable);
	}

}
