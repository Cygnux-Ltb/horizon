package io.horizon.ctp.adaptor.consts;

import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OF_Close;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OF_CloseToday;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OF_CloseYesterday;
import static ctp.thostapi.thosttraderapiConstants.THOST_FTDC_OF_Open;

/**
 * ///TFtdcOffsetFlagType是一个开平标志类型<br>
 * <br>
 * ///开仓<br>
 * #define THOST_FTDC_OF_Open '0'<br>
 * <br>
 * ///平仓<br>
 * #define THOST_FTDC_OF_Close '1'<br>
 * <br>
 * ///强平<br>
 * #define THOST_FTDC_OF_ForceClose '2'<br>
 * <br>
 * ///平今<br>
 * #define THOST_FTDC_OF_CloseToday '3'<br>
 * <br>
 * ///平昨<br>
 * #define THOST_FTDC_OF_CloseYesterday '4'<br>
 * <br>
 * ///强减<br>
 * #define THOST_FTDC_OF_ForceOff '5'<br>
 * <br>
 * ///本地强平<br>
 * #define THOST_FTDC_OF_LocalForceClose '6'<br>
 */
public interface FtdcOffsetFlag {

    /**
     * 组合开平标识, 开仓, [char]
     */
    char OPEN = THOST_FTDC_OF_Open;

    /**
     * 组合开平标识, 开仓, [String]
     */
    String OPEN_STR = String.valueOf(THOST_FTDC_OF_Open);

    /**
     * 组合开平标识, 平仓, [char]
     */
    char CLOSE = THOST_FTDC_OF_Close;

    /**
     * 组合开平标识, 平仓, [String]
     */
    String CLOSE_STR = String.valueOf(THOST_FTDC_OF_Close);

    /**
     * 组合开平标识, 平今, [char]
     */
    char CLOSE_TODAY = THOST_FTDC_OF_CloseToday;

    /**
     * 组合开平标识, 平今, [String]
     */
    String CLOSE_TODAY_STR = String.valueOf(THOST_FTDC_OF_CloseToday);

    /**
     * 组合开平标识, 平昨, [char]
     */
    char CLOSE_YESTERDAY = THOST_FTDC_OF_CloseYesterday;

    /**
     * 组合开平标识, 平昨, [String]
     */
    String CLOSE_YESTERDAY_STR = String.valueOf(THOST_FTDC_OF_CloseYesterday);

}
