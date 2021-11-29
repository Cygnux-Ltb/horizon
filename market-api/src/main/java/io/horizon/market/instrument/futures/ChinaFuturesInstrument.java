package io.horizon.market.instrument.futures;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.PriorityCloseType;
import io.mercury.common.util.StringSupport;

/**
 * 
 * @author yellow013
 * 
 */
public final class ChinaFuturesInstrument extends AbstractFutures {

	private final PriorityCloseType priorityCloseType;

	/**
	 * 
	 * @param symbol
	 * @param instrumentId
	 * @param instrumentCode
	 */
	private ChinaFuturesInstrument(ChinaFuturesSymbol symbol, int instrumentId, String instrumentCode) {
		super(instrumentId, instrumentCode, symbol);
		this.priorityCloseType = symbol.getPriorityCloseType();
	}

	@Override
	public PriorityCloseType getPriorityCloseType() {
		return priorityCloseType;
	}

	@Override
	public boolean isAvailableImmediately() {
		return true;
	}

	@Override
	public int getTickSize() {
		return 1;
	}

	/**
	 * 交易日分割点
	 */
	public static final LocalTime TradingDayDividingLine = LocalTime.of(17, 00);

	/**
	 * 
	 * @param symbol
	 * @param term
	 * @return
	 */
	public static Instrument newInstance(ChinaFuturesSymbol symbol, int term) {
		int instrumentId = symbol.acquireInstrumentId(term);
		String instrumentCode;
		switch (symbol.getExchange()) {
		// 对郑商所合约代码做特殊处理, 去除最高位年份, 比如CF2105需要处理为CF105
		case ZCE:
			instrumentCode = symbol.getSymbolCode() + String.valueOf(term).substring(1);
			break;
		default:
			instrumentCode = symbol.getSymbolCode() + term;
			break;
		}
		return new ChinaFuturesInstrument(symbol, instrumentId, instrumentCode);
	}

	/**
	 * 分析<b> [Instrument]</b>
	 * 
	 * @param instrument
	 * @return
	 */
	public static final Instrument parseInstrument(String instrument) {
		return parseInstrumentList(instrument)[0];
	}

	/**
	 * 分析<b> [Instrument] </b>列表
	 * 
	 * @param instrumentList
	 * @return
	 */
	public static final Instrument[] parseInstrumentList(String instrumentList) {
		return parseInstrumentList(instrumentList, ";");
	}

	/**
	 * 分析<b> [Instrument] </b>列表
	 * 
	 * @param instrumentList
	 * @param separator
	 * @return
	 */
	public static final Instrument[] parseInstrumentList(String instrumentList, String separator) {
		String[] instrumentArray = instrumentList.split(separator);
		Instrument[] instruments = new Instrument[instrumentArray.length];
		for (int i = 0; i < instrumentArray.length; i++) {
			String instrument = instrumentArray[i];
			// 分析symbol
			ChinaFuturesSymbol symbol = ChinaFuturesSymbol.of(parseSymbolCode(instrument));
			// 分析期限
			int term = parseInstrumentTerm(instrument);
			// 创建Instrument
			instruments[i] = ChinaFuturesInstrument.newInstance(symbol, term);
		}
		return instruments;
	}

	/**
	 * 分析指定时间的所属交易日
	 * 
	 * @param dateTime
	 * @return
	 */
	public static final LocalDate parseTradingDay(LocalDateTime dateTime) {
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
	public static final String parseSymbolCode(String instrumentCode) {
		if (StringSupport.isNullOrEmpty(instrumentCode))
			return instrumentCode;
		return instrumentCode.replaceAll("[\\d]", "").trim();
	}

	/**
	 * 分析[instrumentCode]中的日期期限
	 * 
	 * @param instrumentCode
	 * @return
	 */
	public static final int parseInstrumentTerm(String instrumentCode) {
		if (StringSupport.isNullOrEmpty(instrumentCode))
			return 0;
		return Integer.parseInt(instrumentCode.replaceAll("[^\\d]", "").trim());
	}

	public static void main(String[] args) {

		System.out.println(Integer.MAX_VALUE);
		System.out.println(ChinaFuturesSymbol.AG.getExchange().getExchangeId());
		System.out.println(ChinaFuturesSymbol.AG.getSymbolId());
		System.out.println(ChinaFuturesSymbol.AG.acquireInstrumentId(2112));
		System.out.println(parseSymbolCode("rb1901"));
		System.out.println(parseInstrumentTerm("rb1901"));
		ChinaFuturesSymbol rb1901 = ChinaFuturesSymbol.of(parseSymbolCode("rb1901"));
		System.out.println(rb1901);

	}

}
