package io.gemini.definition.market.instrument.futures.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.gemini.definition.market.instrument.Exchange;
import io.gemini.definition.market.instrument.Instrument;
import io.gemini.definition.market.instrument.PriceMultiplier;
import io.gemini.definition.market.instrument.Symbol;
import io.gemini.definition.market.instrument.Instrument.PriorityClose;
import io.gemini.definition.market.vector.TradingPeriod;
import io.mercury.common.collections.ImmutableMaps;
import io.mercury.common.collections.ImmutableSets;
import io.mercury.common.collections.MutableLists;

public enum ChinaFuturesSymbol implements Symbol {

	// ************************上海期货交易所************************//
	/**
	 * 铜 cu
	 */
	CU(1, "cu", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" },
			// 铜期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 铝 al
	 */
	AL(2, "al", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" },
			// 铝期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 锌 zn
	 */
	ZN(3, "zn", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" },
			// 锌期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 镍
	 */
	NI(4, "ni", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" },
			// 镍期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 黄金
	 */
	AU(5, "au", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.TEN_THOUSAND,
			// 主力合约月份
			new String[] { "06", "12" },
			// 黄金期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(2, 30, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 白银
	 */
	AG(6, "ag", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "06", "12" },
			// 白银期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(2, 30, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 螺纹钢
	 */
	RB(7, "rb", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "10" },
			// 螺纹钢期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 热卷扎板
	 */
	HC(8, "hc", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "10" },
			// 热卷扎板期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 橡胶
	 */
	RU(9, "ru", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// 橡胶期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 燃油
	 */
	FU(10, "fu", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// 燃油期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	// ************************上海能源交易所************************//
	/**
	 * 原油
	 */
	SC(1, "sc", Exchange.SHINE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" },
			// 原油期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	// **************************大连商品交易所*************************//
	/**
	 * 大豆 a
	 */
	A(1, "a", Exchange.DCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// 大豆期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 豆粕 m
	 */
	M(2, "m", Exchange.DCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// 豆粕期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 豆油 y
	 */
	Y(3, "y", Exchange.DCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// 豆油期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 棕榈油 p
	 */
	P(4, "p", Exchange.DCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// 棕榈油期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 玉米 p
	 */
	C(4, "c", Exchange.DCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// 棕榈油期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 铁矿石 i
	 */
	I(5, "i", Exchange.DCE, PriorityClose.NONE, PriceMultiplier.HUNDRED,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// 铁矿石期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
	// TODO 大商所品种 : 塑料, PVC, PP,

	// *****************************郑州商品交易所***********************************//
	/**
	 * 棉花 cf
	 */
	CF(1, "CF", Exchange.ZCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// 棉花交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 白糖 sr
	 */
	SR(2, "SR", Exchange.ZCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// 白糖交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00))),

	/**
	 * PTA
	 */
	TA(3, "TA", Exchange.ZCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// PTA交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00))),

	/**
	 * 乙醇
	 */
	MA(4, "MA", Exchange.ZCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// 乙醇交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00))),

	/**
	 * 菜粕
	 */
	RM(5, "RM", Exchange.ZCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 主力合约月份
			new String[] { "01", "05", "09" },
			// 菜粕交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00))),

	// ************************中国金融交易所************************//
	/**
	 * 沪深300期货
	 */
	IF(1, "IF", Exchange.CFFEX, PriorityClose.NONE, PriceMultiplier.HUNDRED,
			// 主力合约月份
			new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" },
			// 股指期货交易时段
			new TradingPeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00))),

	/**
	 * 上证50期货
	 */
	IH(2, "IH", Exchange.CFFEX, PriorityClose.NONE, PriceMultiplier.HUNDRED,
			// 主力合约月份
			new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" },
			// 股指期货交易时段
			new TradingPeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00))),

	/**
	 * 中证500期货
	 */
	IC(3, "IC", Exchange.CFFEX, PriorityClose.NONE, PriceMultiplier.HUNDRED,
			// 主力合约月份
			new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" },
			// 股指期货交易时段
			new TradingPeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00))),

	;

	/*
	 * ID
	 */
	private int id;

	/*
	 * 交易所
	 */
	private Exchange exchange;

	/*
	 * 代码
	 */
	private String code;

	/*
	 * 优先平仓类型
	 */
	private PriorityClose priorityClose;

	/*
	 * 价格乘数
	 */
	private PriceMultiplier priceMultiplier;

	/*
	 * symbol包含的主力合约列表
	 */
	private ImmutableList<Instrument> instruments;

	/*
	 * 交易时间段
	 */
	private ImmutableSortedSet<TradingPeriod> tradingPeriodSet;

	private ChinaFuturesSymbol(int exchangeSerial, String code, Exchange exchange, PriorityClose priorityClose,
			PriceMultiplier priceMultiplier, String[] termMonths, TradingPeriod... tradingPeriods) {
		this.id = exchange.id() + exchangeSerial * 10000;
		this.code = code;
		this.exchange = exchange;
		this.priorityClose = priorityClose;
		this.priceMultiplier = priceMultiplier;
		this.instruments = generateInstruments(termMonths);
		this.tradingPeriodSet = ImmutableSets.newImmutableSortedSet(tradingPeriods);

	}

	private ImmutableList<Instrument> generateInstruments(String[] termMonths) {
		MutableList<Instrument> instruments = MutableLists.newFastList();
		LocalDate now = LocalDate.now(exchange.zoneId());
		int thisYear = (now.getYear() % 100);
		int nextYear = (now.plusYears(1).getYear() % 100);
		for (String termMonth : termMonths) {
			int month = Integer.parseInt(termMonth);
			int term0 = thisYear * 100 + month;
			int term1 = nextYear * 100 + month;
			String code0;
			String code1;
			if (exchange == Exchange.ZCE) {
				code0 = String.valueOf(thisYear % 10) + termMonth;
				code1 = String.valueOf(nextYear % 10) + termMonth;
			} else {
				code0 = String.valueOf(term0);
				code1 = String.valueOf(term1);
			}
			instruments.add(new ChinaFutures(this, term0, code0));
			instruments.add(new ChinaFutures(this, term1, code1));
		}
		return instruments.toImmutable();
	}

	@Override
	public int id() {
		return id;
	}

	@Override
	public String code() {
		return code;
	}

	/**
	 * 交易所
	 */
	@Override
	public Exchange exchange() {
		return exchange;
	}

	/**
	 * 优先平仓类型
	 * 
	 * @return
	 */
	public PriorityClose getPriorityClose() {
		return priorityClose;
	}

	/**
	 * 价格乘数
	 */
	@Override
	public PriceMultiplier getPriceMultiplier() {
		return priceMultiplier;
	}

	/**
	 * 返回当前symbol可用Instrument
	 * 
	 * @return
	 */
	public ImmutableList<Instrument> getInstruments() {
		return instruments;
	}

	/**
	 * 交易时间段
	 */
	@Override
	public ImmutableSortedSet<TradingPeriod> getTradingPeriodSet() {
		return tradingPeriodSet;
	}

	/*
	 * 建立symbolId -> symbol的映射
	 */
	private final static ImmutableIntObjectMap<ChinaFuturesSymbol> SymbolIdMap = ImmutableMaps.getIntObjectMapFactory()
			.from(
					// 将ChinaFuturesSymbol转换为Iterable
					MutableLists.newFastList(ChinaFuturesSymbol.values()),
					// 取Symbol::id为Key
					ChinaFuturesSymbol::id, symbol -> symbol);

	/*
	 * 建立symbolCode -> symbol的映射
	 */
	private final static ImmutableMap<String, ChinaFuturesSymbol> SymbolCodeMap = ImmutableMaps.newImmutableMap(
			// 将ChinaFuturesSymbol转换为Map
			Stream.of(ChinaFuturesSymbol.values()).collect(Collectors.toMap(
					// 取Symbol::code为Key
					ChinaFuturesSymbol::code, symbol -> symbol)));

	/**
	 * 
	 * @param symbolId
	 * @return
	 */
	public static ChinaFuturesSymbol of(int symbolId) {
		ChinaFuturesSymbol symbol = SymbolIdMap.get(symbolId);
		if (symbol == null)
			throw new IllegalArgumentException("symbolId -> " + symbolId + " is not mapping object");
		return symbol;
	}

	/**
	 * 
	 * @param symbolCode
	 * @return
	 */
	public static ChinaFuturesSymbol of(String symbolCode) {
		ChinaFuturesSymbol symbol = SymbolCodeMap.get(symbolCode);
		if (symbol == null) {
			symbol = SymbolCodeMap.get(symbolCode.toUpperCase());
			if (symbol == null)
				throw new IllegalArgumentException("symbolCode -> " + symbolCode + " is not mapping object");
		}
		return symbol;
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public int acquireInstrumentId(int term) {
		if (term > 9999)
			throw new IllegalArgumentException("Term > 9999, Is too much.");
		return id + term;
	}

	public static void main(String[] args) {
		for (Symbol symbol : ChinaFuturesSymbol.values()) {
			symbol.getTradingPeriodSet()
					.each(tradingPeriod -> tradingPeriod
							.segmentation(symbol.exchange().zoneId(), Duration.ofSeconds(30))
							.each(timePeriod -> System.out.println(symbol.code() + " | " + timePeriod)));

			symbol.getTradingPeriodSet().stream().map(
					tradingPeriod -> tradingPeriod.segmentation(symbol.exchange().zoneId(), Duration.ofSeconds(30)));
		}

		System.out.println(ChinaFuturesSymbol.AG.exchange.id());
		System.out.println(ChinaFuturesSymbol.AG.id());

		for (ChinaFuturesSymbol symbol : ChinaFuturesSymbol.values()) {
			symbol.getInstruments().each(instrument -> System.out.println(instrument));
		}

	}

	@Override
	public String fmtText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {

		return super.toString();
	}

}
