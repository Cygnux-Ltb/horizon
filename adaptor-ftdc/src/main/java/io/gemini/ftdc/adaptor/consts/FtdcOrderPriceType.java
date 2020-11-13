package io.gemini.ftdc.adaptor.consts;

import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OPT_AnyPrice;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OPT_AskPrice1;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OPT_BestPrice;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OPT_BidPrice1;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OPT_LastPrice;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OPT_LimitPrice;

/**
 * ///TFtdcOrderPriceTypeType是一个报单价格条件类型 <br>
 * <br>
 * ///任意价<br>
 * #define THOST_FTDC_OPT_AnyPrice '1'<br>
 * <br>
 * ///限价<br>
 * #define THOST_FTDC_OPT_LimitPrice '2'<br>
 * <br>
 * ///最优价<br>
 * #define THOST_FTDC_OPT_BestPrice '3'<br>
 * <br>
 * ///最新价<br>
 * #define THOST_FTDC_OPT_LastPrice '4'<br>
 * <br>
 * ///最新价浮动上浮1个ticks<br>
 * #define THOST_FTDC_OPT_LastPricePlusOneTicks '5'<br>
 * <br>
 * ///最新价浮动上浮2个ticks<br>
 * #define THOST_FTDC_OPT_LastPricePlusTwoTicks '6'<br>
 * <br>
 * ///最新价浮动上浮3个ticks<br>
 * #define THOST_FTDC_OPT_LastPricePlusThreeTicks '7'<br>
 * <br>
 * ///卖一价<br>
 * #define THOST_FTDC_OPT_AskPrice1 '8'<br>
 * <br>
 * ///卖一价浮动上浮1个ticks<br>
 * #define THOST_FTDC_OPT_AskPrice1PlusOneTicks '9'<br>
 * <br>
 * ///卖一价浮动上浮2个ticks<br>
 * #define THOST_FTDC_OPT_AskPrice1PlusTwoTicks 'A'<br>
 * <br>
 * ///卖一价浮动上浮3个ticks<br>
 * #define THOST_FTDC_OPT_AskPrice1PlusThreeTicks 'B'<br>
 * <br>
 * ///买一价<br>
 * #define THOST_FTDC_OPT_BidPrice1 'C'<br>
 * <br>
 * ///买一价浮动上浮1个ticks<br>
 * #define THOST_FTDC_OPT_BidPrice1PlusOneTicks 'D'<br>
 * <br>
 * ///买一价浮动上浮2个ticks<br>
 * #define THOST_FTDC_OPT_BidPrice1PlusTwoTicks 'E'<br>
 * <br>
 * ///买一价浮动上浮3个ticks<br>
 * #define THOST_FTDC_OPT_BidPrice1PlusThreeTicks 'F'<br>
 * <br>
 * ///五档价<br>
 * #define THOST_FTDC_OPT_FiveLevelPrice 'G'<br>
 */
public interface FtdcOrderPriceType {

	/**
	 * 任意价
	 */
	char AnyPrice = THOST_FTDC_OPT_AnyPrice;

	/**
	 * 限价
	 */
	char LimitPrice = THOST_FTDC_OPT_LimitPrice;

	/**
	 * 最优价
	 */
	char BestPrice = THOST_FTDC_OPT_BestPrice;

	/**
	 * 最新价
	 */
	char LastPrice = THOST_FTDC_OPT_LastPrice;

	/**
	 * 卖一价
	 */
	char AskPrice1 = THOST_FTDC_OPT_AskPrice1;

	/**
	 * 买一价
	 */
	char BidPrice1 = THOST_FTDC_OPT_BidPrice1;

}
