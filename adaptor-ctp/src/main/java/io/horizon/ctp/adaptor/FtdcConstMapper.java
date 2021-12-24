package io.horizon.ctp.adaptor;

import static io.horizon.ctp.adaptor.consts.FtdcDirection.Buy;
import static io.horizon.ctp.adaptor.consts.FtdcDirection.Sell;
import static io.horizon.ctp.adaptor.consts.FtdcOffsetFlag.Close;
import static io.horizon.ctp.adaptor.consts.FtdcOffsetFlag.CloseToday;
import static io.horizon.ctp.adaptor.consts.FtdcOffsetFlag.CloseYesterday;
import static io.horizon.ctp.adaptor.consts.FtdcOffsetFlag.Open;
import static io.horizon.ctp.adaptor.consts.FtdcOrderStatus.AllTraded;
import static io.horizon.ctp.adaptor.consts.FtdcOrderStatus.Canceled;
import static io.horizon.ctp.adaptor.consts.FtdcOrderStatus.NoTradeNotQueueing;
import static io.horizon.ctp.adaptor.consts.FtdcOrderStatus.NoTradeQueueing;
import static io.horizon.ctp.adaptor.consts.FtdcOrderStatus.PartTradedNotQueueing;
import static io.horizon.ctp.adaptor.consts.FtdcOrderStatus.PartTradedQueueing;

import javax.annotation.Nonnull;

import io.horizon.trader.order.enums.OrdStatus;
import io.horizon.trader.order.enums.TrdAction;
import io.horizon.trader.order.enums.TrdDirection;

/**
 * 
 * @author yellow013
 *
 */
public final class FtdcConstMapper {

	/**
	 * 根据<b> [FTDC返回] </b>订单状态, 映射<b> [系统自定义] </b>订单状态
	 * 
	 * @param orderStatus
	 * @return
	 */
	@Nonnull
	public static final OrdStatus findByOrderStatus(char orderStatus) {
		return
		// 未成交不在队列中 or 未成交还在队列中 return [OrdStatus.New]
		NoTradeNotQueueing == orderStatus || NoTradeQueueing == orderStatus ? OrdStatus.New
				// 部分成交不在队列中 or 部分成交还在队列中 return [OrdStatus.PartiallyFilled]
				: PartTradedNotQueueing == orderStatus || PartTradedQueueing == orderStatus ? OrdStatus.PartiallyFilled
						// 全部成交 return [OrdStatus.Filled]
						: AllTraded == orderStatus ? OrdStatus.Filled
								// 撤单 return [OrdStatus.Canceled]
								: Canceled == orderStatus ? OrdStatus.Canceled
										// return [OrdStatus.Invalid]
										: OrdStatus.Invalid;
	}

	/**
	 * 根据<b> [FTDC返回] </b>开平仓类型, 映射<b> [系统自定义] </b>开平仓类型
	 * 
	 * @param combOffsetFlag
	 * @return
	 */
	@Nonnull
	public static final TrdAction findByOffsetFlag(@Nonnull String combOffsetFlag) {
		return findByOffsetFlag(combOffsetFlag.charAt(0));
	}

	/**
	 * 根据<b> [FTDC返回] </b>开平仓类型, 映射<b> [系统自定义] </b>开平仓类型
	 * 
	 * @param offsetFlag
	 * @return
	 */
	@Nonnull
	public static final TrdAction findByOffsetFlag(char offsetFlag) {
		return
		// 开仓
		Open == offsetFlag ? TrdAction.Open
				// 平仓
				: Close == offsetFlag ? TrdAction.Close
						// 平今
						: CloseToday == offsetFlag ? TrdAction.CloseToday
								// 平昨
								: CloseYesterday == offsetFlag ? TrdAction.CloseYesterday
										// 未知
										: TrdAction.Invalid;
	}

	/**
	 * 根据<b>[FTDC返回]</b>买卖方向类型, 映射<b>[系统自定义]</b>买卖方向类型类型
	 * 
	 * @param direction
	 * @return
	 */
	public static final TrdDirection findByDirection(char direction) {
		return
		// 买
		Buy == direction ? TrdDirection.Long
				// 卖
				: Sell == direction ? TrdDirection.Short
						// 未知
						: TrdDirection.Invalid;
	}

}
