package io.horizon.market.instrument;

import static io.horizon.market.instrument.Exchange.CFFEX;
import static io.horizon.market.instrument.Exchange.DCE;
import static io.horizon.market.instrument.Exchange.SHFE;
import static io.horizon.market.instrument.Exchange.SHINE;
import static io.horizon.market.instrument.Exchange.ZCE;
import static io.mercury.common.collections.ImmutableLists.newImmutableList;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

import io.horizon.market.instrument.attr.PriceMultiplier;
import io.horizon.market.instrument.attr.PriorityCloseType;
import io.horizon.market.instrument.attr.TradablePeriod;
import io.horizon.market.instrument.base.AbstractFutures;
import io.mercury.common.collections.MutableLists;
import io.mercury.common.collections.MutableMaps;
import io.mercury.common.datetime.DateTimeUtil;
import io.mercury.common.util.StringSupport;
import io.mercury.serialization.json.JsonWrapper;

/**
 * 此class仅作为namespace使用
 * 
 * @author yellow013
 */
public final class ChinaFutures {

	private ChinaFutures() {
	}

	/**
	 * 固定价格乘数
	 */
	public static final PriceMultiplier FixedMultiplier = PriceMultiplier.MULTIPLIER_10000;

	/**
	 * 交易日分割点
	 */
	public static final LocalTime TradingDayDividingLine = LocalTime.of(17, 00);

	/**
	 * 夜盘交易开盘时间
	 */
	public static final LocalTime NIGHT_OPEN = LocalTime.of(21, 00);

	/**
	 * 夜盘交易最后收盘时间
	 */
	public static final LocalTime NIGHT_CLOSE = LocalTime.of(2, 30);

	/**
	 * 白天交易开盘时间
	 */
	public static final LocalTime DAY_OPEN = LocalTime.of(9, 00);

	/**
	 * 白天交易收盘时间
	 */
	public static final LocalTime DAY_CLOSE = LocalTime.of(15, 00);

	/**
	 * 
	 * ChinaFutures Symbol
	 * 
	 * @author yellow013
	 */
	public static enum ChinaFuturesSymbol implements Symbol {

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
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
		),

		/**
		 * 铝 al
		 */
		AL(SHFE, "al", 2, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 铝期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
		),

		/**
		 * 锌 zn
		 */
		ZN(SHFE, "zn", 3, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 锌期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
		),

		/**
		 * 镍
		 */
		NI(SHFE, "ni", 4, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 镍期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
		),

		/**
		 * 黄金
		 */
		AU(SHFE, "au", 5, PriorityCloseType.NONE, PriceMultiplier.MULTIPLIER_10000,
				// 黄金期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(2, 30, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		/// "06", "12"
		),

		/**
		 * 白银
		 */
		AG(SHFE, "ag", 6, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 白银期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(2, 30, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "06", "12"
		),

		/**
		 * 螺纹钢
		 */
		RB(SHFE, "rb", 7, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 螺纹钢期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "05", "10"
		),

		/**
		 * 热卷扎板
		 */
		HC(SHFE, "hc", 8, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 热卷扎板期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "05", "10"
		),

		/**
		 * 橡胶
		 */
		RU(SHFE, "ru", 9, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 橡胶期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),

		/**
		 * 燃油
		 */
		FU(SHFE, "fu", 10, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 燃油期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),

		// ************************上海能源交易所************************//
		/**
		 * 原油
		 */
		SC(SHINE, "sc", 1, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 原油期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(1, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
		),

		// **************************大连商品交易所*************************//
		/**
		 * 大豆 a
		 */
		A(DCE, "a", 1, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 大豆期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),

		/**
		 * 豆粕 m
		 */
		M(DCE, "m", 2, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 豆粕期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),

		/**
		 * 豆油 y
		 */
		Y(DCE, "y", 3, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 豆油期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),

		/**
		 * 棕榈油 p
		 */
		P(DCE, "p", 4, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 棕榈油期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),

		/**
		 * 玉米 p
		 */
		C(DCE, "c", 4, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 棕榈油期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),

		/**
		 * 铁矿石 i
		 */
		I(DCE, "i", 5, PriorityCloseType.NONE, PriceMultiplier.MULTIPLIER_100,
				// 铁矿石期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),
		// 大商所品种 : 塑料, PVC, PP,

		// *****************************郑州商品交易所***********************************//
		/**
		 * 棉花 cf
		 */
		CF(ZCE, "CF", 1, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 棉花交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(3, LocalTime.of(13, 30, 00), LocalTime.of(15, 00, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),

		/**
		 * 白糖 sr
		 */
		SR(ZCE, "SR", 2, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 白糖交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),

		/**
		 * PTA
		 */
		TA(ZCE, "TA", 3, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// PTA交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),

		/**
		 * 乙醇
		 */
		MA(ZCE, "MA", 4, PriorityCloseType.NONE, PriceMultiplier.NONE,

				// 乙醇交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),

		/**
		 * 菜粕
		 */
		RM(ZCE, "RM", 5, PriorityCloseType.NONE, PriceMultiplier.NONE,
				// 菜粕交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00)),
						new TradablePeriod(1, LocalTime.of(9, 00, 00), LocalTime.of(10, 15, 00)),
						new TradablePeriod(2, LocalTime.of(10, 30, 00), LocalTime.of(15, 15, 00)))
		// 主力合约月份代码
		// "01", "05", "09"
		),

		// ************************中国金融交易所************************//
		/**
		 * 沪深300期货
		 */
		IF(CFFEX, "IF", 1, PriorityCloseType.NONE, PriceMultiplier.MULTIPLIER_100,
				// 股指期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00)))
		// 主力合约月份代码
		// "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
		),

		/**
		 * 上证50期货
		 */
		IH(CFFEX, "IH", 2, PriorityCloseType.NONE, PriceMultiplier.MULTIPLIER_100,
				// 股指期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00)))
		// 主力合约月份代码
		// "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
		),

		/**
		 * 中证500期货
		 */
		IC(CFFEX, "IC", 3, PriorityCloseType.NONE, PriceMultiplier.MULTIPLIER_100,
				// 股指期货交易时段
				newImmutableList(new TradablePeriod(0, LocalTime.of(9, 15, 00), LocalTime.of(11, 30, 00)),
						new TradablePeriod(1, LocalTime.of(13, 00, 00), LocalTime.of(15, 15, 00)))
		// 主力合约月份代码
		// "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
		),

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
				ImmutableList<TradablePeriod> tradablePeriods) {
			this.exchange = exchange;
			this.symbolId = AbstractFutures.generateSymbolId(exchange.getExchangeId(), serialInExchange);
			this.symbolCode = symbolCode;
			this.priorityCloseType = priorityCloseType;
			this.multiplier = multiplier;
			this.tradablePeriods = tradablePeriods;
			this.instruments = generateInstruments();
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
		private ImmutableList<Instrument> generateInstruments() {
			MutableList<Instrument> instruments = MutableLists.newFastList();
			LocalDate date = LocalDate.now(exchange.getZoneOffset());
			// 生成未来25个月的期货合约
			for (int i = 0; i < 25; i++) {
				int term = DateTimeUtil.dateOfMonth(date.plusMonths(i)) % 10000;
				instruments.add(ChinaFuturesInstrument.newInstance(this, term));
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

		@Override
		public PriceMultiplier getMultiplier() {
			return multiplier;
		}

		@Override
		public Exchange getExchange() {
			return exchange;
		}

		@Override
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

		@Override
		public boolean isSymbolCode(String code) {
			if (StringSupport.isNullOrEmpty(code))
				return false;
			return symbolCode.equalsIgnoreCase(code);
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

	/**
	 * 
	 * @author yellow013
	 * 
	 */
	public static final class ChinaFuturesInstrument extends AbstractFutures {

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

		@Override
		public PriceMultiplier getMultiplier() {
			return FixedMultiplier;
		}

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

	}

	/**
	 * 工具类
	 * 
	 * @author yellow013
	 *
	 */
	public static class ChinaFuturesUtil {

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

		/**
		 * 获取下一次关闭运行的时间点
		 * 
		 * @return
		 */
		public static final LocalDateTime nextCloseTime() {
			return nextCloseTime(LocalDateTime.now());
		}

		static final LocalDateTime nextCloseTime(LocalDateTime datetime) {
			// 夜盘收盘时间
			LocalDateTime nightClose = LocalDateTime.of(datetime.toLocalDate(), ChinaFutures.NIGHT_CLOSE);
			// 输入时间在前一个夜盘中
			if (datetime.isBefore(nightClose)) {
				// 夜盘结束后10分钟
				return nightClose.plusMinutes(10);
			}

			// 白天交易收盘时间
			LocalDateTime dayClose = LocalDateTime.of(datetime.toLocalDate(), ChinaFutures.DAY_CLOSE);
			// 输入时间在夜盘收盘后, 在白天收盘前
			if (datetime.isAfter(nightClose) && datetime.isBefore(dayClose)) {
				// 白天收盘后10分钟
				return dayClose.plusMinutes(15);
			}

			// 获取下一个夜盘收盘时间
			LocalDateTime nextNightClose = LocalDateTime.of(datetime.toLocalDate().plusDays(1),
					ChinaFutures.NIGHT_CLOSE);
			// 如果输入时间在白天交易之后, 在下一个夜盘收盘结束前
			if ((datetime.isAfter(dayClose) && datetime.isBefore(nextNightClose))) {
				// 夜盘结束后10分钟
				return nextNightClose.plusMinutes(15);
			}

			return nextNightClose.plusMinutes(10);
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

}
