package io.horizon.market.instrument.misc;

import static io.horizon.market.instrument.spec.ChinaFuturesSymbol.of;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.spec.ChinaFutures;
import io.horizon.market.instrument.spec.ChinaFuturesSymbol;
import io.mercury.common.util.StringUtil;

public final class ChinaFuturesSupporter {

	private ChinaFuturesSupporter() {
	}

	/**
	 * 交易日分割点
	 */
	public static final LocalTime TradingDayDividingLine = LocalTime.of(16, 00);

	/**
	 * 分析<b> [Instrument]</b>
	 * 
	 * @param instrumentStr
	 * @return
	 */
	public static final Instrument analyzeInstrument(String instrumentStr) {
		return analyzeInstrumentList(instrumentStr)[0];
	}

	/**
	 * 分析<b> [Instrument] </b>列表
	 * 
	 * @param instrumentList
	 * @return
	 */
	public static final Instrument[] analyzeInstrumentList(String instrumentList) {
		return analyzeInstrumentList(instrumentList, ";");
	}

	/**
	 * 分析<b> [Instrument] </b>列表
	 * 
	 * @param instrumentList
	 * @return
	 */
	public static final Instrument[] analyzeInstrumentList(String instrumentList, String separator) {
		String[] instrumentArray = instrumentList.split(separator);
		Instrument[] instruments = new Instrument[instrumentArray.length];
		for (int i = 0; i < instrumentArray.length; i++) {
			String instrumentStr = instrumentArray[i];
			// 分析symbol
			ChinaFuturesSymbol symbol = of(analyzeSymbolCode(instrumentStr));
			// 分析期限
			int term = analyzeInstrumentTerm(instrumentStr);
			// 创建Instrument
			instruments[i] = new ChinaFutures(symbol, term);
		}
		return instruments;
	}

	/**
	 * 分析指定时间的所属交易日
	 * 
	 * @param dateTime
	 * @return
	 */
	public static final LocalDate analyzeTradingDay(LocalDateTime dateTime) {
		DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
		// 判断是否是夜盘
		if (isNightTrading(dateTime.toLocalTime())) {
			// 判断是否周五, 如果是加3天, 否则加1天
			if (dayOfWeek.equals(DayOfWeek.FRIDAY))
				return dateTime.plusDays(3).toLocalDate();
			else
				return dateTime.plusDays(1).toLocalDate();
		} else {
			// 判断是否周六, 如果是加2天, 否则不加.
			if (dayOfWeek.equals(DayOfWeek.SATURDAY))
				return dateTime.plusDays(2).toLocalDate();
			else
				return dateTime.toLocalDate();
		}
	}

	/**
	 * 判断时间是否在交易日时间线之前
	 * 
	 * @param time
	 * @return
	 */
	public static final boolean isNightTrading(LocalTime time) {
		if (time.isAfter(TradingDayDividingLine))
			return true;
		else
			return false;
	}

	/**
	 * 分析[instrumentCode]中的[symbol]代码
	 * 
	 * @param instrumentCode
	 * @return
	 */
	public static final String analyzeSymbolCode(String instrumentCode) {
		if (StringUtil.isNullOrEmpty(instrumentCode))
			return instrumentCode;
		return instrumentCode.replaceAll("[\\d]", "").trim();
	}

	/**
	 * 分析[instrumentCode]中的日期期限
	 * 
	 * @param instrumentCode
	 * @return
	 */
	public static final int analyzeInstrumentTerm(String instrumentCode) {
		if (StringUtil.isNullOrEmpty(instrumentCode))
			return 0;
		return Integer.parseInt(instrumentCode.replaceAll("[^\\d]", "").trim());
	}

	public static void main(String[] args) {

		System.out.println(Integer.MAX_VALUE);
		System.out.println(ChinaFuturesSymbol.AG.getExchange().getExchangeId());
		System.out.println(ChinaFuturesSymbol.AG.getSymbolId());
		System.out.println(ChinaFuturesSymbol.AG.acquireInstrumentId(1906));
		System.out.println(analyzeSymbolCode("rb1901"));
		System.out.println(analyzeInstrumentTerm("rb1901"));
		ChinaFuturesSymbol rb1901 = ChinaFuturesSymbol.of(analyzeSymbolCode("rb1901"));
		System.out.println(rb1901);

	}

}
