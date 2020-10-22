package io.gemini.ftdc.gateway.base;

public class FtdcRespException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4330136354361431411L;

	public FtdcRespException(String spiFunctionName, int errId, String errMsg) {
		super("SPI Function -> " + spiFunctionName + " | ErrorId -> " + errId + " | ErrorMsg -> " + errMsg);
	}

}
