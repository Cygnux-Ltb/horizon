package io.horizon.ctp.gateway.base;

public class FtdcRespException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4330136354361431411L;

	public FtdcRespException(String spiFuncName, int errorId, String errorMsg) {
		super("SPI Function -> " + spiFuncName + " : ErrorId == [" + errorId + "], ErrorMsg == [" + errorMsg + "]");
	}

}
