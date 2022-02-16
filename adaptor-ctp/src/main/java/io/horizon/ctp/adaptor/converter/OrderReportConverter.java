package io.horizon.ctp.adaptor.converter;

import static io.horizon.ctp.adaptor.FtdcConstMapper.findByDirection;
import static io.horizon.ctp.adaptor.FtdcConstMapper.findByOffsetFlag;
import static io.horizon.ctp.adaptor.FtdcConstMapper.findByOrderStatus;
import static io.horizon.ctp.adaptor.OrderRefKeeper.getOrdSysId;
import static io.horizon.market.instrument.ChinaFutures.FixedMultiplier;
import static io.horizon.trader.order.enums.OrdStatus.NewRejected;
import static io.horizon.trader.order.enums.OrdStatus.Unprovided;
import static io.mercury.common.datetime.EpochTime.getEpochMicros;
import static io.mercury.common.util.StringSupport.removeNonDigits;

import org.slf4j.Logger;

import io.horizon.ctp.gateway.rsp.FtdcInputOrder;
import io.horizon.ctp.gateway.rsp.FtdcInputOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcOrder;
import io.horizon.ctp.gateway.rsp.FtdcOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcTrade;
import io.horizon.trader.transport.outbound.OrderReport;
import io.mercury.common.log.Log4j2LoggerFactory;

/**
 * 
 * OrderReportConverter
 * 
 * @author yellow013
 */
public final class OrderReportConverter {

	private static final Logger log = Log4j2LoggerFactory.getLogger(OrderReportConverter.class);

	/**
	 * 报单错误消息转换 <br>
	 * <br>
	 * FtdcInputOrder -> OrderReport
	 * 
	 * @param FtdcInputOrder
	 * @return OrderReport
	 */
	public OrderReport withFtdcInputOrder(FtdcInputOrder order) {
		String orderRef = order.getOrderRef();
		long ordSysId = getOrdSysId(orderRef);
		var builder = OrderReport.newBuilder();
		// 时间戳
		builder.setEpochMicros(getEpochMicros());
		// OrdSysId
		builder.setOrdSysId(ordSysId);
		// 投资者ID
		builder.setInvestorId(order.getInvestorID());
		// 报单引用
		builder.setOrderRef(orderRef);
		// 交易所
		builder.setExchangeCode(order.getExchangeID());
		// 合约代码
		builder.setInstrumentCode(order.getInstrumentID());
		// 报单状态
		builder.setStatus(NewRejected.getTOrdStatus());
		// 买卖方向
		var direction = findByDirection(order.getDirection());
		builder.setDirection(direction.getTTrdDirection());
		// 组合开平标志
		var action = findByOffsetFlag(order.getCombOffsetFlag());
		builder.setAction(action.getTTrdAction());
		// 委托数量
		builder.setOfferQty(order.getVolumeTotalOriginal());
		// 委托价格
		builder.setOfferPrice(FixedMultiplier.toLong(order.getLimitPrice()));

		OrderReport report = builder.build();
		log.info("FtdcInputOrder conversion to OrderReport -> {}", report);
		return report;
	}

	/**
	 * 订单回报消息转换<br>
	 * <br>
	 * FtdcOrder -> OrderReport
	 * 
	 * @param FtdcOrder
	 * @return OrderReport
	 */
	public OrderReport withFtdcOrder(FtdcOrder order) {
		var orderRef = order.getOrderRef();
		long ordSysId = getOrdSysId(orderRef);
		var builder = OrderReport.newBuilder();
		// 时间戳
		builder.setEpochMicros(getEpochMicros());
		// OrdSysId
		builder.setOrdSysId(ordSysId);
		// 交易日
		builder.setTradingDay(order.getTradingDay());
		// 投资者ID
		builder.setInvestorId(order.getInvestorID());
		// 报单引用
		builder.setOrderRef(orderRef);
		// 报单编号
		builder.setBrokerOrdSysId(order.getOrderSysID());
		// 交易所
		builder.setExchangeCode(order.getExchangeID());
		// 合约代码
		builder.setInstrumentCode(order.getInstrumentID());
		// 报单状态
		var ordStatus = findByOrderStatus(order.getOrderStatus());
		builder.setStatus(ordStatus.getTOrdStatus());
		// 买卖方向
		var direction = findByDirection(order.getDirection());
		builder.setDirection(direction.getTTrdDirection());
		// 组合开平标志
		var action = findByOffsetFlag(order.getCombOffsetFlag());
		builder.setAction(action.getTTrdAction());
		// 委托数量
		builder.setOfferQty(order.getVolumeTotalOriginal());
		// 完成数量
		builder.setFilledQty(order.getVolumeTraded());
		// 委托价格
		builder.setOfferPrice(FixedMultiplier.toLong(order.getLimitPrice()));
		// 报单日期 + 委托时间
		builder.setOfferTime(removeNonDigits(order.getInsertDate()) + removeNonDigits(order.getInsertTime()));
		// 更新时间
		builder.setUpdateTime(order.getUpdateTime());

		var report = builder.build();
		log.info("FtdcOrder conversion to OrderReport -> {}", report);
		return report;
	}

	/**
	 * 成交回报消息转换<br>
	 * <br>
	 * FtdcTrade -> OrderReport
	 * 
	 * @param FtdcTrade
	 * @return OrderReport
	 */
	public OrderReport withFtdcTrade(FtdcTrade trade) {
		var orderRef = trade.getOrderRef();
		long ordSysId = getOrdSysId(orderRef);
		var builder = OrderReport.newBuilder();
		// 微秒时间戳
		builder.setEpochMicros(getEpochMicros());
		// OrdSysId
		builder.setOrdSysId(ordSysId);
		// 交易日
		builder.setTradingDay(trade.getTradingDay());
		// 投资者ID
		builder.setInvestorId(trade.getInvestorID());
		// 报单引用
		builder.setOrderRef(orderRef);
		// 报单编号
		builder.setBrokerOrdSysId(trade.getOrderSysID());
		// 交易所
		builder.setExchangeCode(trade.getExchangeID());
		// 合约代码
		builder.setInstrumentCode(trade.getInstrumentID());
		// 报单状态
		builder.setStatus(Unprovided.getTOrdStatus());
		// 买卖方向
		var direction = findByDirection(trade.getDirection());
		builder.setDirection(direction.getTTrdDirection());
		// 组合开平标志
		var action = findByOffsetFlag(trade.getOffsetFlag());
		builder.setAction(action.getTTrdAction());
		// 完成数量
		builder.setFilledQty(trade.getVolume());
		// 成交价格
		builder.setTradePrice(FixedMultiplier.toLong(trade.getPrice()));
		// 最后修改时间
		builder.setUpdateTime(removeNonDigits(trade.getTradeDate()) + removeNonDigits(trade.getTradeTime()));

		var report = builder.build();
		log.info("FtdcTrade conversion to OrderReport -> {}", report);
		return report;
	}

	/**
	 * 撤单错误回报消息转换1<br>
	 * <br>
	 * FtdcInputOrderAction -> OrderReport
	 * 
	 * @param FtdcInputOrderAction
	 * @return OrderReport
	 */
	public OrderReport withFtdcInputOrderAction(FtdcInputOrderAction inputOrderAction) {

		return null;
	}

	/**
	 * 撤单错误回报消息转换2<br>
	 * <br>
	 * FtdcOrderAction -> OrderReport
	 * 
	 * @param FtdcOrderAction
	 * @return OrderReport
	 */
	public OrderReport withFtdcOrderAction(FtdcOrderAction orderAction) {

		return null;
	}

}
