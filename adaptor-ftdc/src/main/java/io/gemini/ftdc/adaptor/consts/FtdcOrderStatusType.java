package io.gemini.ftdc.adaptor.consts;

import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OST_AllTraded;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OST_Canceled;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OST_NoTradeNotQueueing;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OST_NoTradeQueueing;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OST_NotTouched;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OST_PartTradedNotQueueing;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OST_PartTradedQueueing;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OST_Touched;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OST_Unknown;

/**
 * ///TFtdcOrderStatusType是一个报单状态类型<br>
 * <br>
 * ///全部成交<br>
 * #define THOST_FTDC_OST_AllTraded '0'<br>
 * <br>
 * ///部分成交还在队列中<br>
 * #define THOST_FTDC_OST_PartTradedQueueing '1'<br>
 * <br>
 * ///部分成交不在队列中<br>
 * #define THOST_FTDC_OST_PartTradedNotQueueing '2'<br>
 * <br>
 * ///未成交还在队列中<br>
 * #define THOST_FTDC_OST_NoTradeQueueing '3'<br>
 * <br>
 * ///未成交不在队列中<br>
 * #define THOST_FTDC_OST_NoTradeNotQueueing '4'<br>
 * <br>
 * ///撤单<br>
 * #define THOST_FTDC_OST_Canceled '5'<br>
 * <br>
 * ///未知<br>
 * #define THOST_FTDC_OST_Unknown 'a'<br>
 * <br>
 * ///尚未触发<br>
 * #define THOST_FTDC_OST_NotTouched 'b'<br>
 * <br>
 * ///已触发<br>
 * #define THOST_FTDC_OST_Touched 'c'<br>
 * 
 * @author yellow013
 *
 */
public interface FtdcOrderStatusType {

	/**
	 * 全部成交
	 */
	char AllTraded = THOST_FTDC_OST_AllTraded;

	/**
	 * 部分成交还在队列中
	 */
	char PartTradedQueueing = THOST_FTDC_OST_PartTradedQueueing;

	/**
	 * 部分成交不在队列中
	 */
	char PartTradedNotQueueing = THOST_FTDC_OST_PartTradedNotQueueing;

	/**
	 * 未成交还在队列中
	 */
	char NoTradeQueueing = THOST_FTDC_OST_NoTradeQueueing;

	/**
	 * 未成交不在队列中
	 */
	char NoTradeNotQueueing = THOST_FTDC_OST_NoTradeNotQueueing;

	/**
	 * 撤单
	 */
	char Canceled = THOST_FTDC_OST_Canceled;

	/**
	 * 未知
	 */
	char Unknown = THOST_FTDC_OST_Unknown;

	/**
	 * 尚未触发
	 */
	char NotTouched = THOST_FTDC_OST_NotTouched;

	/**
	 * 已触发
	 */
	char Touched = THOST_FTDC_OST_Touched;

}
