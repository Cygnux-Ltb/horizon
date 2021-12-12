package io.horizon.ctp.gateway;

public class FtdcRspException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4330136354361431411L;

	public FtdcRspException(String func, int errorId, String errorMsg) {
		super("Function -> " + func + " : ErrorId == [" + errorId + "], ErrorMsg == [" + errorMsg + "]");
	}

}
