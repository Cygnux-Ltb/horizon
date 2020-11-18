package io.gemini.ftdc.adaptor.consts;

import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_AF_Delete;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_AF_Modify;

/**
 * 
 * ///TFtdcActionFlagType是一个操作标志类型<br>
 * <br>
 * ///删除<br>
 * #define THOST_FTDC_AF_Delete '0'<br>
 * <br>
 * ///修改<br>
 * #define THOST_FTDC_AF_Modify '3'<br>
 *
 */
public interface FtdcActionFlag {

	/**
	 * 删除
	 */
	char Delete = THOST_FTDC_AF_Delete;

	/**
	 * 修改
	 */
	char Modify = THOST_FTDC_AF_Modify;

}
