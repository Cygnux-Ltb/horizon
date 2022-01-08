package io.horizon.ctp.adaptor.converter;

import static io.horizon.market.instrument.ChinaFutures.FixedMultiplier;
import static io.mercury.common.util.StringSupport.removeNonDigits;

import org.slf4j.Logger;

import io.horizon.ctp.adaptor.FtdcConstMapper;
import io.horizon.ctp.adaptor.OrderRefKeeper;
import io.horizon.ctp.gateway.rsp.FtdcInputOrder;
import io.horizon.ctp.gateway.rsp.FtdcInputOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcOrder;
import io.horizon.ctp.gateway.rsp.FtdcOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcTrade;
import io.horizon.trader.order.enums.OrdStatus;
import io.horizon.trader.order.enums.TrdAction;
import io.horizon.trader.order.enums.TrdDirection;
import io.horizon.trader.transport.outbound.OrderReport;
import io.mercury.common.datetime.EpochUtil;
import io.mercury.common.log.Log4j2LoggerFactory;

/**
 * OrderReportConverter
 * 
 * @author yellow013
 *
 */
public final class OrderReportConverter {

	private static final Logger log = Log4j2LoggerFactory.getLogger(OrderReportConverter.class);

	/**
	 * 报单错误消息转换
	 * 
	 * @param order
	 * @return
	 */
	public OrderReport fromFtdcInputOrder(FtdcInputOrder order) {
		String orderRef = order.getOrderRef();
		long ordSysId = OrderRefKeeper.getOrdSysId(orderRef);
		var builder = OrderReport.newBuilder();
		// 时间戳
		builder.setEpochMicros(EpochUtil.getEpochMicros());
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
		builder.setStatus(OrdStatus.NewRejected.getTOrdStatus());
		// 买卖方向
		TrdDirection direction = FtdcConstMapper.findByDirection(order.getDirection());
		builder.setDirection(direction.getTTrdDirection());
		// 组合开平标志
		TrdAction action = FtdcConstMapper.findByOffsetFlag(order.getCombOffsetFlag());
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
	 * 订单回报消息转换
	 * 
	 * @param order
	 * @return
	 */
	public OrderReport fromFtdcOrder(FtdcOrder order) {
		String orderRef = order.getOrderRef();
		long ordSysId = OrderRefKeeper.getOrdSysId(orderRef);
		var builder = OrderReport.newBuilder();
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
		builder.setBrokerSysId(order.getOrderSysID());
		// 交易所
		builder.setExchangeCode(order.getExchangeID());
		// 合约代码
		builder.setInstrumentCode(order.getInstrumentID());
		// 报单状态
		OrdStatus ordStatus = FtdcConstMapper.findByOrderStatus(order.getOrderStatus());
		builder.setStatus(ordStatus.getTOrdStatus());
		// 买卖方向
		TrdDirection direction = FtdcConstMapper.findByDirection(order.getDirection());
		builder.setDirection(direction.getTTrdDirection());
		// 组合开平标志
		TrdAction action = FtdcConstMapper.findByOffsetFlag(order.getCombOffsetFlag());
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

		OrderReport report = builder.build();
		log.info("FtdcOrder conversion to OrderReport -> {}", report);
		return report;
	}

	/**
	 * 成交回报消息转换
	 * 
	 * @param trade
	 * @return
	 */
	public OrderReport fromFtdcTrade(FtdcTrade trade) {
		String orderRef = trade.getOrderRef();
		long ordSysId = OrderRefKeeper.getOrdSysId(orderRef);
		var builder = OrderReport.newBuilder();
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
		builder.setBrokerSysId(trade.getOrderSysID());
		// 交易所
		builder.setExchangeCode(trade.getExchangeID());
		// 合约代码
		builder.setInstrumentCode(trade.getInstrumentID());
		// 报单状态
		builder.setStatus(OrdStatus.Unprovided.getTOrdStatus());
		// 买卖方向
		TrdDirection direction = FtdcConstMapper.findByDirection(trade.getDirection());
		builder.setDirection(direction.getTTrdDirection());
		// 组合开平标志
		TrdAction action = FtdcConstMapper.findByOffsetFlag(trade.getOffsetFlag());
		builder.setAction(action.getTTrdAction());
		// 完成数量
		builder.setFilledQty(trade.getVolume());
		// 成交价格
		builder.setTradePrice(FixedMultiplier.toLong(trade.getPrice()));
		// 最后修改时间
		builder.setUpdateTime(removeNonDigits(trade.getTradeDate()) + removeNonDigits(trade.getTradeTime()));

		OrderReport report = builder.build();
		log.info("FtdcTrade conversion to OrderReport -> {}", report);
		return report;
	}

	/**
	 * 撤单错误回报消息转换1
	 * 
	 * @param inputOrderAction
	 * @return
	 */
	public OrderReport fromFtdcInputOrderAction(FtdcInputOrderAction inputOrderAction) {

		return null;
	}

	/**
	 * 撤单错误回报消息转换2
	 * 
	 * @param orderAction
	 * @return
	 */
	public OrderReport fromFtdcOrderAction(FtdcOrderAction orderAction) {

		return null;
	}

}
