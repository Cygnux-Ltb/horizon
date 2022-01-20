package io.horizon.ctp.adaptor.converter;

import static io.horizon.market.data.impl.BasicMarketData.newLevel5;
import static io.mercury.common.datetime.pattern.DatePattern.YYYYMMDD;
import static io.mercury.common.datetime.pattern.TimePattern.HH_MM_SS;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;

import io.horizon.ctp.gateway.rsp.FtdcDepthMarketData;
import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.instrument.InstrumentKeeper;
import io.mercury.common.datetime.TimeConst;
import io.mercury.common.log.Log4j2LoggerFactory;

/**
 * 
 * FtdcDepthMarketData -> BasicMarketData
 * 
 * @author yellow013
 */
public final class MarketDataConverter {

	private static final Logger log = Log4j2LoggerFactory.getLogger(MarketDataConverter.class);

	private final DateTimeFormatter updateTimeformatter = HH_MM_SS.newFormatter();

	private final DateTimeFormatter actionDayformatter = YYYYMMDD.newFormatter();

	/**
	 * 
	 * 转换
	 * 
	 * @param depthMarketData
	 * 
	 *                        <pre>
	///深度行情
	struct CThostFtdcDepthMarketDataField
	{
	///交易日
	TThostFtdcDateType	TradingDay;
	///合约代码
	TThostFtdcInstrumentIDType	InstrumentID;
	///交易所代码
	TThostFtdcExchangeIDType	ExchangeID;
	///合约在交易所的代码
	TThostFtdcExchangeInstIDType	ExchangeInstID;
	///最新价
	TThostFtdcPriceType	LastPrice;
	///上次结算价
	TThostFtdcPriceType	PreSettlementPrice;
	///昨收盘
	TThostFtdcPriceType	PreClosePrice;
	///昨持仓量
	TThostFtdcLargeVolumeType	PreOpenInterest;
	///今开盘
	TThostFtdcPriceType	OpenPrice;
	///最高价
	TThostFtdcPriceType	HighestPrice;
	///最低价
	TThostFtdcPriceType	LowestPrice;
	///数量
	TThostFtdcVolumeType	Volume;
	///成交金额
	TThostFtdcMoneyType	Turnover;
	///持仓量
	TThostFtdcLargeVolumeType	OpenInterest;
	///今收盘
	TThostFtdcPriceType	ClosePrice;
	///本次结算价
	TThostFtdcPriceType	SettlementPrice;
	///涨停板价
	TThostFtdcPriceType	UpperLimitPrice;
	///跌停板价
	TThostFtdcPriceType	LowerLimitPrice;
	///昨虚实度
	TThostFtdcRatioType	PreDelta;
	///今虚实度
	TThostFtdcRatioType	CurrDelta;
	///最后修改时间
	TThostFtdcTimeType	UpdateTime;
	///最后修改毫秒
	TThostFtdcMillisecType	UpdateMillisec;
	///申买价一
	TThostFtdcPriceType	BidPrice1;
	///申买量一
	TThostFtdcVolumeType	BidVolume1;
	///申卖价一
	TThostFtdcPriceType	AskPrice1;
	///申卖量一
	TThostFtdcVolumeType	AskVolume1;
	///申买价二
	TThostFtdcPriceType	BidPrice2;
	///申买量二
	TThostFtdcVolumeType	BidVolume2;
	///申卖价二
	TThostFtdcPriceType	AskPrice2;
	///申卖量二
	TThostFtdcVolumeType	AskVolume2;
	///申买价三
	TThostFtdcPriceType	BidPrice3;
	///申买量三
	TThostFtdcVolumeType	BidVolume3;
	///申卖价三
	TThostFtdcPriceType	AskPrice3;
	///申卖量三
	TThostFtdcVolumeType	AskVolume3;
	///申买价四
	TThostFtdcPriceType	BidPrice4;
	///申买量四
	TThostFtdcVolumeType	BidVolume4;
	///申卖价四
	TThostFtdcPriceType	AskPrice4;
	///申卖量四
	TThostFtdcVolumeType	AskVolume4;
	///申买价五
	TThostFtdcPriceType	BidPrice5;
	///申买量五
	TThostFtdcVolumeType	BidVolume5;
	///申卖价五
	TThostFtdcPriceType	AskPrice5;
	///申卖量五
	TThostFtdcVolumeType	AskVolume5;
	///当日均价
	TThostFtdcPriceType	AveragePrice;
	///业务日期
	TThostFtdcDateType	ActionDay;
	};
	 *                        </pre>
	 * 
	 * @return BasicMarketData
	 */
	public BasicMarketData withFtdcDepthMarketData(FtdcDepthMarketData depthMarketData) {
		// 业务日期
		var actionDay = LocalDate.parse(depthMarketData.getActionDay(), actionDayformatter);
		// 最后修改时间
		var updateTime = LocalTime.parse(depthMarketData.getUpdateTime(), updateTimeformatter)
				.plusNanos(depthMarketData.getUpdateMillisec() * TimeConst.NANOS_PER_MILLIS);

		var instrument = InstrumentKeeper.getInstrument(depthMarketData.getInstrumentID());

		log.info("Convert depthMarketData apply -> InstrumentCode==[{}], actionDay==[{}], updateTime==[{}]",
				instrument.getInstrumentCode(), actionDay, updateTime);
		var multiplier = instrument.getSymbol().getMultiplier();

		return newLevel5(
				// 交易标的
				instrument,
				// 交易日
				actionDay,
				// 时间
				updateTime)
						// 最新价
						.setLastPrice(multiplier.toLong(depthMarketData.getLastPrice()))
						// 成交量
						.setVolume(depthMarketData.getVolume())
						// 成交额
						.setTurnover(multiplier.toLong(depthMarketData.getTurnover()))
						// 买一价和买一量
						.setBidPrice1(multiplier.toLong(depthMarketData.getBidPrice1()))
						.setBidVolume1(depthMarketData.getBidVolume1())
						// 买二价和买二量
						.setBidPrice2(multiplier.toLong(depthMarketData.getBidPrice2()))
						.setBidVolume2(depthMarketData.getBidVolume2())
						// 买三价和买三量
						.setBidPrice3(multiplier.toLong(depthMarketData.getBidPrice3()))
						.setBidVolume3(depthMarketData.getBidVolume3())
						// 买四价和买四量
						.setBidPrice4(multiplier.toLong(depthMarketData.getBidPrice4()))
						.setBidVolume4(depthMarketData.getBidVolume4())
						// 买五价和买五量
						.setBidPrice5(multiplier.toLong(depthMarketData.getBidPrice5()))
						.setBidVolume5(depthMarketData.getBidVolume5())
						// 卖一价和卖一量
						.setAskPrice1(multiplier.toLong(depthMarketData.getAskPrice1()))
						.setAskVolume1(depthMarketData.getAskVolume1())
						// 卖二价和卖二量
						.setAskPrice2(multiplier.toLong(depthMarketData.getAskPrice2()))
						.setAskVolume2(depthMarketData.getAskVolume2())
						// 卖三价和卖三量
						.setAskPrice3(multiplier.toLong(depthMarketData.getAskPrice3()))
						.setAskVolume3(depthMarketData.getAskVolume3())
						// 卖四价和卖四量
						.setAskPrice4(multiplier.toLong(depthMarketData.getAskPrice4()))
						.setAskVolume4(depthMarketData.getAskVolume4())
						// 卖五价和卖五量
						.setAskPrice5(multiplier.toLong(depthMarketData.getAskPrice5()))
						.setAskVolume5(depthMarketData.getAskVolume5());
	}

}
