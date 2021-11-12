package io.horizon.market.instrument.impl.futures;

import static io.horizon.market.instrument.impl.futures.ChinaFuturesExchange.CFFEX;
import static io.horizon.market.instrument.impl.futures.ChinaFuturesExchange.DCE;
import static io.horizon.market.instrument.impl.futures.ChinaFuturesExchange.SHFE;
import static io.horizon.market.instrument.impl.futures.ChinaFuturesExchange.SHINE;
import static io.horizon.market.instrument.impl.futures.ChinaFuturesExchange.ZCE;
import static io.mercury.common.collections.ImmutableLists.newImmutableList;
import static io.mercury.common.collections.ImmutableMaps.getIntObjectMapFactory;
import static io.mercury.common.collections.ImmutableMaps.getMapFactory;
import static io.mercury.common.collections.MutableLists.newFastList;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

import io.horizon.market.instrument.Exchange;
import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.PriceMultiplier;
import io.horizon.market.instrument.PriorityCloseType;
import io.horizon.market.instrument.Symbol;
import io.horizon.market.instrument.TradablePeriod;
import io.mercury.common.collections.MutableLists;
import io.mercury.serialization.json.JsonWrapper;

public enum ChinaFuturesSymbol implements Symbol {

	// ************************上海期货交易所************************//
	/**
	 * 铜 cu
	 * 
	 * @return
	 */
	CU(SHFE, "cu", 1, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 铜期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"),

	/**
	 * 铝 al
	 */
	AL(SHFE, "al", 2, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 铝期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"),

	/**
	 * 锌 zn
	 */
	ZN(SHFE, "zn", 3, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 锌期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"),

	/**
	 * 镍
	 */
	NI(SHFE, "ni", 4, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 镍期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"),

	/**
	 * 黄金
	 */
	AU(SHFE, "au", 5, PriorityCloseType.NONE, PriceMultiplier.MULTIPLIER_10000,
			// 黄金期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(2, 30, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"06", "12"),

	/**
	 * 白银
	 */
	AG(SHFE, "ag", 6, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 白银期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(2, 30, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"06", "12"),

	/**
	 * 螺纹钢
	 */
	RB(SHFE, "rb", 7, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 螺纹钢期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "05", "10"),

	/**
	 * 热卷扎板
	 */
	HC(SHFE, "hc", 8, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 热卷扎板期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "05", "10"),

	/**
	 * 橡胶
	 */
	RU(SHFE, "ru", 9, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 橡胶期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "05", "09"),

	/**
	 * 燃油
	 */
	FU(SHFE, "fu", 10, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 燃油期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "05", "09"),

	// ************************上海能源交易所************************//
	/**
	 * 原油
	 */
	SC(SHINE, "sc", 1, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 原油期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"),

	// **************************大连商品交易所*************************//
	/**
	 * 大豆 a
	 */
	A(DCE, "a", 1, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 大豆期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "05", "09"),

	/**
	 * 豆粕 m
	 */
	M(DCE, "m", 2, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 豆粕期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "05", "09"),

	/**
	 * 豆油 y
	 */
	Y(DCE, "y", 3, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 豆油期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "05", "09"),

	/**
	 * 棕榈油 p
	 */
	P(DCE, "p", 4, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 棕榈油期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "05", "09"),

	/**
	 * 玉米 p
	 */
	C(DCE, "c", 4, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 棕榈油期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "05", "09"),

	/**
	 * 铁矿石 i
	 */
	I(DCE, "i", 5, PriorityCloseType.NONE, PriceMultiplier.MULTIPLIER_100,
			// 铁矿石期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "05", "09"),
	// TODO 大商所品种 : 塑料, PVC, PP,

	// *****************************郑州商品交易所***********************************//
	/**
	 * 棉花 cf
	 */
	CF(ZCE, "CF", 1, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 棉花交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
			// 主力合约月份代码
			"01", "05", "09"),

	/**
	 * 白糖 sr
	 */
	SR(ZCE, "SR", 2, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 白糖交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00))),
			// 主力合约月份代码
			"01", "05", "09"),

	/**
	 * PTA
	 */
	TA(ZCE, "TA", 3, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// PTA交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00))),
			// 主力合约月份代码
			"01", "05", "09"),

	/**
	 * 乙醇
	 */
	MA(ZCE, "MA", 4, PriorityCloseType.NONE, PriceMultiplier.NONE,

			// 乙醇交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00))),
			// 主力合约月份代码
			"01", "05", "09"),

	/**
	 * 菜粕
	 */
	RM(ZCE, "RM", 5, PriorityCloseType.NONE, PriceMultiplier.NONE,
			// 菜粕交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
					new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
					new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00))),
			// 主力合约月份代码
			"01", "05", "09"),

	// ************************中国金融交易所************************//
	/**
	 * 沪深300期货
	 */
	IF(CFFEX, "IF", 1, PriorityCloseType.NONE, PriceMultiplier.MULTIPLIER_100,
			// 主力合约月份代码
			// 股指期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00))),
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"),

	/**
	 * 上证50期货
	 */
	IH(CFFEX, "IH", 2, PriorityCloseType.NONE, PriceMultiplier.MULTIPLIER_100,
			// 股指期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00))),
			// 主力合约月份代码
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"),

	/**
	 * 中证500期货
	 */
	IC(CFFEX, "IC", 3, PriorityCloseType.NONE, PriceMultiplier.MULTIPLIER_100,
			// 股指期货交易时段
			newImmutableList(new TradablePeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
					new TradablePeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00))),
			// 主力合约月份代码
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"),

	;

	// SymbolId
	private final int symbolId;

	// Symbol代码
	private final String symbolCode;

	// 交易所
	private final Exchange exchange;

	// 优先平仓类型
	private final PriorityCloseType priorityCloseType;

	// 价格乘数
	private final PriceMultiplier priceMultiplier;

	// Symbol包含的主力合约列表
	private final ImmutableList<Instrument> instruments;

	// 交易时间段
	private final ImmutableList<TradablePeriod> tradablePeriods;

	/**
	 * 
	 * @param symbolId
	 * @param symbolCode
	 * @param exchange
	 * @param priorityCloseType
	 * @param priceMultiplier
	 * @param termMonths
	 * @param tradingPeriods
	 */
	private ChinaFuturesSymbol(Exchange exchange, String symbolCode, int seqOfExchange,
			PriorityCloseType priorityCloseType, PriceMultiplier priceMultiplier,
			ImmutableList<TradablePeriod> tradablePeriods, String... terms) {
		this.exchange = exchange;
		this.symbolId = exchange.genSymbolId(seqOfExchange);
		this.symbolCode = symbolCode;
		this.priorityCloseType = priorityCloseType;
		this.priceMultiplier = priceMultiplier;
		this.instruments = genInstruments(terms);
		this.tradablePeriods = tradablePeriods;
	}

	// symbolId -> symbol映射
	private final static ImmutableIntObjectMap<ChinaFuturesSymbol> SymbolIdMap = getIntObjectMapFactory().from(
			// 将ChinaFuturesSymbol转换为List
			newFastList(ChinaFuturesSymbol.values()),
			// 取symbolId为Key
			ChinaFuturesSymbol::getSymbolId, symbol -> symbol);

	// symbolCode -> symbol的映射
	private final static ImmutableMap<String, ChinaFuturesSymbol> SymbolCodeMap = getMapFactory().ofMap(
			// 将ChinaFuturesSymbol转换为List, 再转换为Map
			newFastList(ChinaFuturesSymbol.values()).toMap(
					// 取symbolCode为Key
					ChinaFuturesSymbol::getSymbolCode, symbol -> symbol));

	/**
	 * 以主力合约月份构建合约列表
	 * 
	 * @param terms
	 * @return
	 */
	private ImmutableList<Instrument> genInstruments(String[] terms) {
		MutableList<Instrument> instruments = MutableLists.newFastList();
		LocalDate now = LocalDate.now(exchange.getZoneOffset());
		int thisYear = (now.getYear() % 100);
		int nextYear = (now.plusYears(1).getYear() % 100);
		for (String term : terms) {
			int month = Integer.parseInt(term);
			int term0 = thisYear * 100 + month;
			int term1 = nextYear * 100 + month;
			String code0;
			String code1;
			// 对郑商所合约代码特殊处理
			if (exchange == ChinaFuturesExchange.ZCE) {
				code0 = String.valueOf(thisYear % 10) + term;
				code1 = String.valueOf(nextYear % 10) + term;
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
	public int getSymbolId() {
		return symbolId;
	}

	@Override
	public String getSymbolCode() {
		return symbolCode;
	}

	public PriorityCloseType getPriorityCloseType() {
		return priorityCloseType;
	}

	public PriceMultiplier getPriceMultiplier() {
		return priceMultiplier;
	}

	public Exchange getExchange() {
		return exchange;
	}

	public ImmutableList<Instrument> getInstruments() {
		return instruments;
	}

	public ImmutableList<TradablePeriod> getTradablePeriods() {
		return tradablePeriods;
	}

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
			throw new IllegalArgumentException("term > 9999, Is too much.");
		return symbolId + term;
	}

	private String formatText;

	@Override
	public String format() {
		if (formatText == null) {
			Map<String, Object> tempMap = new HashMap<>();
			tempMap.put("exchangeCode", exchange.getCode());
			tempMap.put("symbolId", symbolId);
			tempMap.put("symbolCode", symbolCode);
			tempMap.put("priorityCloseType", priorityCloseType);
			tempMap.put("priceMultiplier", priceMultiplier);
			this.formatText = JsonWrapper.toJson(tempMap);
		}
		return formatText;
	}

	@Override
	public String toString() {
		return format();
	}

	public int getTickSize() {
		return 0;
	}

	public static void main(String[] args) {
		for (ChinaFuturesSymbol symbol : ChinaFuturesSymbol.values()) {
			symbol.getTradablePeriods().each(tradingPeriod -> {
				System.out.println(tradingPeriod);
				tradingPeriod
						.segmentation(LocalDate.now(), symbol.getExchange().getZoneOffset(), Duration.ofSeconds(30))
						.each(timePeriod -> System.out.println(symbol.getSymbolCode() + " | " + timePeriod));
			});

			symbol.getTradablePeriods().stream().map(tradingPeriod -> tradingPeriod.segmentation(LocalDate.now(),
					symbol.getExchange().getZoneOffset(), Duration.ofSeconds(30)));
		}
		System.out.println(ChinaFuturesSymbol.AG.format());
		System.out.println(ChinaFuturesSymbol.AG.getExchange().getExchangeId());
		System.out.println(ChinaFuturesSymbol.AG.getSymbolId());

		for (ChinaFuturesSymbol symbol : ChinaFuturesSymbol.values()) {
			symbol.getInstruments().each(instrument -> System.out.println(instrument.format()));
		}

	}

}
