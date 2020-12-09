package io.horizon.ftdc.gateway.base;

public class FtdcRespException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4330136354361431411L;

	public FtdcRespException(String spiFuncName, int errId, String errMsg) {
		super("SPI Function -> " + spiFuncName + " | ErrorId -> " + errId + " | ErrorMsg -> " + errMsg);
	}

}
