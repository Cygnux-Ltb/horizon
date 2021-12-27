package io.horizon.ctp.adaptor.converter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;

import io.horizon.ctp.gateway.rsp.FtdcDepthMarketData;
import io.horizon.market.data.impl.BasicMarketData;
import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.InstrumentKeeper;
import io.horizon.market.instrument.attr.PriceMultiplier;
import io.mercury.common.datetime.TimeConst;
import io.mercury.common.datetime.pattern.DatePattern;
import io.mercury.common.datetime.pattern.TimePattern;
import io.mercury.common.log.Log4j2LoggerFactory;

public final class MarketDataConverter {

	private static final Logger log = Log4j2LoggerFactory.getLogger(MarketDataConverter.class);

	private final DateTimeFormatter updateTimeformatter = TimePattern.HH_MM_SS.newDateTimeFormatter();

	private final DateTimeFormatter actionDayformatter = DatePattern.YYYYMMDD.newDateTimeFormatter();

	public BasicMarketData fromFtdcDepthMarketData(FtdcDepthMarketData depthMarketData) {
		LocalDate actionDay = LocalDate.parse(depthMarketData.getActionDay(), actionDayformatter);

		LocalTime updateTime = LocalTime.parse(depthMarketData.getUpdateTime(), updateTimeformatter)
				.plusNanos(depthMarketData.getUpdateMillisec() * TimeConst.NANOS_PER_MILLIS);

		Instrument instrument = InstrumentKeeper.getInstrument(depthMarketData.getInstrumentID());
		log.info("Convert depthMarketData apply -> InstrumentCode==[{}], actionDay==[{}], updateTime==[{}]",
				instrument.getInstrumentCode(), actionDay, updateTime);

		PriceMultiplier multiplier = instrument.getSymbol().getMultiplier();

		return BasicMarketData.newLevel5(
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
