package io.horizon.ctp.exception;

public final class NativeLibraryLoadException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5877471358569299269L;

	public NativeLibraryLoadException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
