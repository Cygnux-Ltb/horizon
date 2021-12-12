package io.horizon.ctp.adaptor.converter;

import static io.mercury.common.util.StringSupport.removeNonDigits;

import org.slf4j.Logger;

import io.horizon.ctp.adaptor.FtdcConstMapper;
import io.horizon.ctp.adaptor.OrderRefKeeper;
import io.horizon.ctp.gateway.rsp.FtdcInputOrder;
import io.horizon.ctp.gateway.rsp.FtdcInputOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcOrder;
import io.horizon.ctp.gateway.rsp.FtdcOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcTrade;
import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.InstrumentKeeper;
import io.horizon.market.instrument.PriceMultiplier;
import io.horizon.trader.order.enums.OrdStatus;
import io.horizon.trader.order.enums.TrdAction;
import io.horizon.trader.order.enums.TrdDirection;
import io.horizon.trader.report.OrderReport;
import io.horizon.trader.report.OrderReport.Builder;
import io.mercury.common.datetime.EpochUtil;
import io.mercury.common.log.CommonLoggerFactory;

/**
 * OrderReportConverter
 * 
 * @author yellow013
 *
 */
public final class OrderReportConverter {

	private static final Logger log = CommonLoggerFactory.getLogger(OrderReportConverter.class);

	/**
	 * 
	 * @param inputOrder
	 * @return
	 */
	public OrderReport fromFtdcInputOrder(FtdcInputOrder inputOrder) {

		return null;
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	public OrderReport fromFtdcOrder(FtdcOrder order) {
		String orderRef = order.getOrderRef();
		long ordSysId = OrderRefKeeper.getOrdSysId(orderRef);
		Builder builder = OrderReport.newBuilder();
		// 时间戳
		builder.setEpochMicros(EpochUtil.getEpochMicros());
		// OrdSysId
		builder.setOrdSysId(ordSysId);
		// 交易日
		builder.setTradingDay(order.getTradingDay());
		// 投资者ID
		builder.setInvestorId(order.getInvestorID());
		// 报单引用
		builder.setOrderRef(orderRef);
		// 报单编号
		builder.setBrokerUniqueId(order.getOrderSysID());
		// 交易所
		builder.setExchangeCode(order.getExchangeID());
		// 合约代码
		builder.setInstrumentCode(order.getInstrumentID());
		// 报单状态
		OrdStatus ordStatus = FtdcConstMapper.withOrderStatus(order.getOrderStatus());
		builder.setStatus(ordStatus.getCode());
		// 买卖方向
		TrdDirection direction = FtdcConstMapper.withDirection(order.getDirection());
		builder.setDirection(direction.getCode());
		// 组合开平标志
		TrdAction action = FtdcConstMapper.fromOffsetFlag(order.getCombOffsetFlag());
		builder.setAction(action.getCode());
		// 委托数量
		builder.setOfferQty(order.getVolumeTotalOriginal());
		// 完成数量
		builder.setFilledQty(order.getVolumeTraded());
		// 委托价格
		Instrument instrument = InstrumentKeeper.getInstrument(order.getInstrumentID());
		PriceMultiplier multiplier = instrument.getSymbol().getMultiplier();
		builder.setOfferPrice(multiplier.toLong(order.getLimitPrice()));
		// 报单日期 + 委托时间
		builder.setOfferTime(removeNonDigits(order.getInsertDate()) + removeNonDigits(order.getInsertTime()));
		// 更新时间
		builder.setUpdateTime(order.getUpdateTime());

		OrderReport report = builder.build();
		log.info("FtdcTrade conversion function return OrderReport -> {}", report);
		return report;
	}

	/**
	 * 
	 * @param trade
	 * @return
	 */
	public OrderReport fromFtdcTrade(FtdcTrade trade) {
		String orderRef = trade.getOrderRef();
		long ordSysId = OrderRefKeeper.getOrdSysId(orderRef);
		Builder builder = OrderReport.newBuilder();
		// 微秒时间戳
		builder.setEpochMicros(EpochUtil.getEpochMicros());
		// OrdSysId
		builder.setOrdSysId(ordSysId);
		// 交易日
		builder.setTradingDay(trade.getTradingDay());
		// 投资者ID
		builder.setInvestorId(trade.getInvestorID());
		// 报单引用
		builder.setOrderRef(orderRef);
		// 报单编号
		builder.setBrokerUniqueId(trade.getOrderSysID());
		// 交易所
		builder.setExchangeCode(trade.getExchangeID());
		// 合约代码
		builder.setInstrumentCode(trade.getInstrumentID());
		// 报单状态
		builder.setStatus(OrdStatus.Unprovided.getCode());
		// 买卖方向
		TrdDirection direction = FtdcConstMapper.withDirection(trade.getDirection());
		builder.setDirection(direction.getCode());
		// 组合开平标志
		TrdAction action = FtdcConstMapper.withOffsetFlag(trade.getOffsetFlag());
		builder.setAction(action.getCode());
		// 完成数量
		builder.setFilledQty(trade.getVolume());
		// 成交价格
		Instrument instrument = InstrumentKeeper.getInstrument(trade.getInstrumentID());
		PriceMultiplier multiplier = instrument.getSymbol().getMultiplier();
		builder.setTradePrice(multiplier.toLong(trade.getPrice()));
		// 最后修改时间
		builder.setUpdateTime(removeNonDigits(trade.getTradeDate()) + removeNonDigits(trade.getTradeTime()));

		OrderReport report = builder.build();
		log.info("FtdcTrade conversion function return OrderReport -> {}", report);
		return report;
	}

	/**
	 * 
	 * @param inputOrderAction
	 * @return
	 */
	public OrderReport fromFtdcInputOrderAction(FtdcInputOrderAction inputOrderAction) {

		return null;
	}

	/**
	 * 
	 * @param orderAction
	 * @return
	 */
	public OrderReport fromFtdcOrderAction(FtdcOrderAction orderAction) {

		return null;
	}

}
