package io.horizon.definition.market.instrument.futures.impl;

import java.time.Duration;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.horizon.definition.market.instrument.Exchange;
import io.horizon.definition.market.instrument.PriceMultiplier;
import io.horizon.definition.market.instrument.Symbol;
import io.horizon.definition.market.instrument.Instrument.PriorityClose;
import io.horizon.definition.vector.TradingPeriod;
import io.mercury.common.collections.ImmutableMaps;
import io.mercury.common.collections.ImmutableSets;
import io.mercury.common.collections.MutableLists;

public enum JapanFuturesSymbol implements Symbol {

	// ************************上海期货交易所************************//
	/**
	 * 铜 cu
	 */
	CU(1, "cu", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 铜期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 铝 al
	 */
	AL(2, "al", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 铝期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 锌 zn
	 */
	ZN(3, "zn", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 锌期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 镍
	 */
	NI(5, "ni", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 镍期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 锡
	 */
	SN(6, "sn", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 锡期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 黄金
	 */
	AU(7, "au", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.TEN_THOUSAND,
			// 黄金期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(2, 30, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 白银
	 */
	AG(8, "ag", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 白银期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(2, 30, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 螺纹钢
	 */
	RB(9, "rb", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 螺纹钢期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 热卷扎板
	 */
	HC(10, "hc", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 热卷扎板期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 橡胶
	 */
	RU(12, "ru", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 橡胶期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 沥青
	 */
	BU(11, "bu", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 沥青期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 燃油
	 */
	FU(13, "fu", Exchange.SHFE, PriorityClose.NONE, PriceMultiplier.NONE,
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
			// 原油期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	// **************************大连商品交易所*************************//
	/**
	 * 大豆 a
	 */
	A(3, "a", Exchange.DCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 大豆期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 豆粕 m
	 */
	M(3, "m", Exchange.DCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 豆粕期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 豆油 y
	 */
	Y(3, "y", Exchange.DCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 豆油期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 棕榈油 p
	 */
	P(3, "p", Exchange.DCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 棕榈油期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 铁矿石 i
	 */
	I(3, "i", Exchange.DCE, PriorityClose.NONE, PriceMultiplier.HUNDRED,
			// 铁矿石期货交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),
	// TODO 大商所品种 : 玉米, 鸡蛋, 塑料, PVC, PP,

	// *****************************郑州商品交易所***********************************//
	/**
	 * 棉花 cf
	 */
	CF(1, "CF", Exchange.ZCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 棉花交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00))),

	/**
	 * 白糖 sr
	 */
	SR(2, "SR", Exchange.ZCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 白糖交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00))),

	/**
	 * PTA
	 */
	TA(3, "TA", Exchange.ZCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// PTA交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00))),

	/**
	 * 乙醇
	 */
	MA(4, "MA", Exchange.ZCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 乙醇交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00))),

	/**
	 * 菜粕
	 */
	RM(5, "RM", Exchange.ZCE, PriorityClose.NONE, PriceMultiplier.NONE,
			// 菜粕交易时段
			new TradingPeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
			new TradingPeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
			new TradingPeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00))),

	// ************************中国金融交易所************************//
	/**
	 * 沪深300期货
	 */
	IF(1, "IF", Exchange.CFFEX, PriorityClose.NONE, PriceMultiplier.NONE,
			// 股指期货交易时段
			new TradingPeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00))),

	/**
	 * 上证50期货
	 */
	IH(2, "IH", Exchange.CFFEX, PriorityClose.NONE, PriceMultiplier.NONE,
			// 股指期货交易时段
			new TradingPeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00))),

	/**
	 * 中证500期货
	 */
	IC(3, "IC", Exchange.CFFEX, PriorityClose.NONE, PriceMultiplier.NONE,
			// 股指期货交易时段
			new TradingPeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00))),

	/**
	 * 十年债券期货
	 */
	T(3, "T", Exchange.CFFEX, PriorityClose.NONE, PriceMultiplier.TEN_THOUSAND,
			// 股指期货交易时段
			new TradingPeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
			new TradingPeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00))),

	;

	// ID
	private int id;

	// 交易所
	private Exchange exchange;

	// 代码
	private String code;

	// 优先平仓类型
	private PriorityClose priorityClose;

	// 价格乘数
	private PriceMultiplier priceMultiplier;

	// 交易时间段
	private ImmutableSortedSet<TradingPeriod> tradingPeriodSet;

	private JapanFuturesSymbol(int exchangeSerial, String code, Exchange exchange, PriorityClose priorityClose,
			PriceMultiplier priceMultiplier, TradingPeriod... tradingPeriods) {
		this.id = exchange.id() + exchangeSerial * 10000;
		this.code = code;
		this.exchange = exchange;
		this.priorityClose = priorityClose;
		this.priceMultiplier = priceMultiplier;
		this.tradingPeriodSet = ImmutableSets.newImmutableSortedSet(tradingPeriods);
	}

	@Override
	public int id() {
		return id;
	}

	@Override
	public String code() {
		return code;
	}

	@Override
	public Exchange exchange() {
		return exchange;
	}

	public PriorityClose getPriorityClose() {
		return priorityClose;
	}

	@Override
	public PriceMultiplier getPriceMultiplier() {
		return priceMultiplier;
	}

	@Override
	public ImmutableSortedSet<TradingPeriod> getTradingPeriodSet() {
		return tradingPeriodSet;
	}

	// 建立symbolId -> symbol的映射
	private final static ImmutableIntObjectMap<JapanFuturesSymbol> SymbolIdMap = ImmutableMaps.getIntObjectMapFactory()
			.from(
					// 将ChinaFuturesSymbol转换为Iterable
					MutableLists.newFastList(JapanFuturesSymbol.values()),
					// 取Symbol::id为Key
					JapanFuturesSymbol::id, symbol -> symbol);

	// 建立symbolCode -> symbol的映射
	private final static ImmutableMap<String, JapanFuturesSymbol> SymbolCodeMap = ImmutableMaps.newImmutableMap(
			// 将ChinaFuturesSymbol转换为Map
			Stream.of(JapanFuturesSymbol.values()).collect(Collectors.toMap(
					// 取Symbol::code为Key
					JapanFuturesSymbol::code, symbol -> symbol)));

	/**
	 * 
	 * @param symbolId
	 * @return
	 */
	public static JapanFuturesSymbol of(int symbolId) {
		JapanFuturesSymbol symbol = SymbolIdMap.get(symbolId);
		if (symbol == null)
			throw new IllegalArgumentException("Symbol Id -> " + symbolId + " is no mapping object");
		return symbol;
	}

	/**
	 * 
	 * @param symbolCode
	 * @return
	 */
	public static JapanFuturesSymbol of(String symbolCode) {
		String key = symbolCode.toUpperCase();
		JapanFuturesSymbol symbol = SymbolCodeMap.get(key);
		if (symbol == null)
			throw new IllegalArgumentException("Symbol Code -> " + symbolCode + " is no mapping object");
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
		for (Symbol symbol : JapanFuturesSymbol.values()) {
			symbol.getTradingPeriodSet()
					.each(tradingPeriod -> tradingPeriod
							.segmentation(symbol.exchange().zoneId(), Duration.ofSeconds(30))
							.each(timePeriod -> System.out.println(symbol.code() + " | " + timePeriod)));

			symbol.getTradingPeriodSet().stream().map(
					tradingPeriod -> tradingPeriod.segmentation(symbol.exchange().zoneId(), Duration.ofSeconds(30)));
		}
		System.out.println(JapanFuturesSymbol.AG.exchange.id());
		System.out.println(JapanFuturesSymbol.AG.id());
	}

	@Override
	public String fmtText() {
		return "";
	}

}
