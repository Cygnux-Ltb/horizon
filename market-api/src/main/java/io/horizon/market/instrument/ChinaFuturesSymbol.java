package io.horizon.market.instrument.futures;

import static io.horizon.market.instrument.Exchange.CFFEX;
import static io.horizon.market.instrument.Exchange.DCE;
import static io.horizon.market.instrument.Exchange.SHFE;
import static io.horizon.market.instrument.Exchange.SHINE;
import static io.horizon.market.instrument.Exchange.ZCE;
import static io.mercury.common.collections.ImmutableLists.newImmutableList;

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
import io.mercury.common.collections.MutableMaps;
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

	// Tick Size
	private int tickSize;

	// 优先平仓类型
	private final PriorityCloseType priorityCloseType;

	// 价格乘数
	private final PriceMultiplier multiplier;

	// Symbol包含的主力合约列表
	private final ImmutableList<Instrument> instruments;

	// 交易时间段
	private final ImmutableList<TradablePeriod> tradablePeriods;

	/**
	 * 
	 * @param exchange
	 * @param symbolCode
	 * @param serialInExchange
	 * @param priorityCloseType
	 * @param multiplier
	 * @param tradablePeriods
	 * @param terms
	 */
	private ChinaFuturesSymbol(Exchange exchange, String symbolCode, int serialInExchange,
			PriorityCloseType priorityCloseType, PriceMultiplier multiplier,
			ImmutableList<TradablePeriod> tradablePeriods, String... terms) {
		this.exchange = exchange;
		this.symbolId = AbstractFutures.generateSymbolId(exchange.getExchangeId(), serialInExchange);
		this.symbolCode = symbolCode;
		this.priorityCloseType = priorityCloseType;
		this.multiplier = multiplier;
		this.tradablePeriods = tradablePeriods;
		this.instruments = generateInstruments(terms);
	}

	// symbolId -> symbol映射
	private final static ImmutableIntObjectMap<ChinaFuturesSymbol> SymbolIdMap;

	// symbolCode -> symbol的映射
	private final static ImmutableMap<String, ChinaFuturesSymbol> SymbolCodeMap;

	static {
		var symbolIdMap = MutableMaps.<ChinaFuturesSymbol>newIntObjectHashMap();
		var symbolCodeMap = MutableMaps.<String, ChinaFuturesSymbol>newUnifiedMap();
		for (var symbol : ChinaFuturesSymbol.values()) {
			symbolIdMap.put(symbol.getSymbolId(), symbol);
			symbolCodeMap.put(symbol.getSymbolCode().toLowerCase(), symbol);
			symbolCodeMap.put(symbol.getSymbolCode().toUpperCase(), symbol);
		}
		SymbolIdMap = symbolIdMap.toImmutable();
		SymbolCodeMap = symbolCodeMap.toImmutable();
	}

	/**
	 * 以主力合约月份构建当年, 次年, 下一个次年的合约列表
	 * 
	 * @param terms
	 * @return
	 */
	private ImmutableList<Instrument> generateInstruments(String[] terms) {
		MutableList<Instrument> instruments = MutableLists.newFastList();
		LocalDate now = LocalDate.now(exchange.getZoneOffset());
		int year = (now.getYear() % 100);
		int yearPlus1 = (now.plusYears(1).getYear() % 100);
		int yearPlus2 = (now.plusYears(2).getYear() % 100);
		for (String term : terms) {
			int month = Integer.parseInt(term);
			int term0 = year * 100 + month;
			int term1 = yearPlus1 * 100 + month;
			int term2 = yearPlus2 * 100 + month;
			instruments.add(ChinaFuturesInstrument.newInstance(this, term0));
			instruments.add(ChinaFuturesInstrument.newInstance(this, term1));
			instruments.add(ChinaFuturesInstrument.newInstance(this, term2));
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

	public PriceMultiplier getMultiplier() {
		return multiplier;
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
		var symbol = SymbolIdMap.get(symbolId);
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
		var symbol = SymbolCodeMap.get(symbolCode);
		if (symbol == null)
			throw new IllegalArgumentException("symbolCode -> " + symbolCode + " is not mapping object");
		return symbol;
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	int acquireInstrumentId(int term) {
		if (term > 9999)
			throw new IllegalArgumentException("term > 9999, Is too much.");
		return symbolId + term;
	}

	private String cache;

	@Override
	public String format() {
		if (cache == null) {
			Map<String, Object> tempMap = new HashMap<>();
			tempMap.put("exchangeCode", exchange.getExchangeCode());
			tempMap.put("symbolId", symbolId);
			tempMap.put("symbolCode", symbolCode);
			tempMap.put("priorityCloseType", priorityCloseType);
			tempMap.put("multiplier", multiplier);
			this.cache = JsonWrapper.toJson(tempMap);
		}
		return cache;
	}

	@Override
	public String toString() {
		return format();
	}

	@Override
	public int getTickSize() {
		return tickSize;
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
