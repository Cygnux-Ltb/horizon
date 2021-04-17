package io.horizon.structure.order;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.structure.order.OrdEnum.OrdStatus;
import io.mercury.common.log.CommonLoggerFactory;

public final class OrderUpdater {

	/**
	 * Logger
	 */
	private static final Logger log = CommonLoggerFactory.getLogger(OrderUpdater.class);

	/**
	 * 根据订单回报处理订单状态
	 * 
	 * @param order
	 * @param report
	 */
	public static void updateWithReport(@Nonnull ChildOrder order, @Nonnull OrderReport report) {
		OrdQty qty = order.getQty();
		int reportFilledQty = report.getFilledQty();
		log.info("OrdReport ordStatus==[{}], reportFilledQty==[{}], tradePrice==[{}], order.qty() -> {}",
				report.getOrdStatus(), reportFilledQty, report.getTradePrice(), qty);
		// 处理未返回订单状态的情况, 根据成交数量判断
		if (report.getOrdStatus() == OrdStatus.Unprovided) {
			int offerQty = qty.getOfferQty();
			order.setStatus(
					// 成交数量等于委托数量, 状态为全部成交
					reportFilledQty == offerQty ? OrdStatus.Filled
							// 成交数量小于委托数量同时成交数量大于0, 状态为部分成交
							: reportFilledQty < offerQty && reportFilledQty > 0 ? OrdStatus.PartiallyFilled
									// 成交数量等于0, 状态为New
									: OrdStatus.New);
		}
		// 已返回订单状态, 直接读取
		else {
			order.setStatus(report.getOrdStatus());
		}
		switch (order.getStatus()) {
		case PartiallyFilled:
			// 处理部分成交, 设置已成交数量
			// Set FilledQty
			order.getQty().setFilledQty(reportFilledQty);
			// 新增订单成交记录
			// Add NewTrade record
			order.addTrdRecord(report.getEpochMillis(), report.getTradePrice(),
					reportFilledQty - order.getQty().getLastFilledQty());
			log.info(
					"ChildOrder current status PartiallyFilled, strategyId==[{}], ordSysId==[{}], "
							+ "filledQty==[{}], avgTradePrice==[{}]",
					order.getStrategyId(), order.getOrdSysId(), order.getQty().getFilledQty());
			break;
		case Filled:
			// 处理全部成交, 设置已成交数量
			// Set FilledQty
			order.getQty().setFilledQty(reportFilledQty);
			// 新增订单成交记录
			// Add NewTrade Record
			order.addTrdRecord(report.getEpochMillis(), report.getTradePrice(),
					reportFilledQty - order.getQty().getLastFilledQty());
			// 计算此订单成交均价
			// Calculation AvgPrice
			long avgTradePrice = order.fillAndGetAvgTradePrice();
			log.info(
					"ChildOrder current status Filled, strategyId==[{}], ordSysId==[{}], "
							+ "filledQty==[{}], avgTradePrice==[{}]",
					order.getStrategyId(), order.getOrdSysId(), order.getQty().getFilledQty(), avgTradePrice);
			break;
		default:
			// 记录其他情况, 打印详细信息
			log.info("Order updateWithReport finish, switch in default, order status==[{}], "
					+ "order -> {}, report -> {}", order.getStatus(), order, report);
			break;
		}
	}

}
