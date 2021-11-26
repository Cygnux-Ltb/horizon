package io.horizon.ftdc.adaptor.consts;

import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_D_Buy;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_D_Sell;

/**
 * TFtdcDirectionType是一个买卖方向类型 <br>
 * <br>
 * ///买<br>
 * #define THOST_FTDC_D_Buy '0'<br>
 * <br>
 * ///卖<br>
 * #define THOST_FTDC_D_Sell '1'<br>
 */
public interface FtdcDirection {

	/**
	 * 买
	 */
	char Buy = THOST_FTDC_D_Buy;

	/**
	 * 卖
	 */
	char Sell = THOST_FTDC_D_Sell;

}
