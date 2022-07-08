package io.horizon.ctp.adaptor;

import static io.horizon.ctp.adaptor.consts.FtdcDirection.BUY;
import static io.horizon.ctp.adaptor.consts.FtdcDirection.SELL;
import static io.horizon.ctp.adaptor.consts.FtdcOffsetFlag.CLOSE;
import static io.horizon.ctp.adaptor.consts.FtdcOffsetFlag.CLOSE_TODAY;
import static io.horizon.ctp.adaptor.consts.FtdcOffsetFlag.CLOSE_YESTERDAY;
import static io.horizon.ctp.adaptor.consts.FtdcOffsetFlag.OPEN;
import static io.horizon.ctp.adaptor.consts.FtdcOrderStatus.ALL_TRADED;
import static io.horizon.ctp.adaptor.consts.FtdcOrderStatus.CANCELED;
import static io.horizon.ctp.adaptor.consts.FtdcOrderStatus.NO_TRADE_NOT_QUEUEING;
import static io.horizon.ctp.adaptor.consts.FtdcOrderStatus.NO_TRADE_QUEUEING;
import static io.horizon.ctp.adaptor.consts.FtdcOrderStatus.PART_TRADED_NOT_QUEUEING;
import static io.horizon.ctp.adaptor.consts.FtdcOrderStatus.PART_TRADED_QUEUEING;

import javax.annotation.Nonnull;

import io.horizon.trader.order.enums.OrdStatus;
import io.horizon.trader.order.enums.TrdAction;
import io.horizon.trader.order.enums.TrdDirection;

/**
 * @author yellow013
 */
public final class FtdcConstMapper {

    /**
     * 根据<b> [FTDC返回] </b>订单状态, 映射<b> [系统自定义] </b>订单状态
     *
     * @param orderStatus
     * @return
     */
    @Nonnull
    public static OrdStatus byOrderStatus(char orderStatus) {
        return
                // 未成交不在队列中 or 未成交还在队列中 return [OrdStatus.New]
                NO_TRADE_NOT_QUEUEING == orderStatus || NO_TRADE_QUEUEING == orderStatus ? OrdStatus.New
                        // 部分成交不在队列中 or 部分成交还在队列中 return [OrdStatus.PartiallyFilled]
                        : PART_TRADED_NOT_QUEUEING == orderStatus || PART_TRADED_QUEUEING == orderStatus ? OrdStatus.PartiallyFilled
                        // 全部成交 return [OrdStatus.Filled]
                        : ALL_TRADED == orderStatus ? OrdStatus.Filled
                        // 撤单 return [OrdStatus.Canceled]
                        : CANCELED == orderStatus ? OrdStatus.Canceled
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
    public static TrdAction byOffsetFlag(@Nonnull String combOffsetFlag) {
        return byOffsetFlag(combOffsetFlag.charAt(0));
    }

    /**
     * 根据<b> [FTDC返回] </b>开平仓类型, 映射<b> [系统自定义] </b>开平仓类型
     *
     * @param offsetFlag char
     * @return
     */
    @Nonnull
    public static TrdAction byOffsetFlag(char offsetFlag) {
        return
                // 开仓
                OPEN == offsetFlag ? TrdAction.Open
                        // 平仓
                        : CLOSE == offsetFlag ? TrdAction.Close
                        // 平今
                        : CLOSE_TODAY == offsetFlag ? TrdAction.CloseToday
                        // 平昨
                        : CLOSE_YESTERDAY == offsetFlag ? TrdAction.CloseYesterday
                        // 未知
                        : TrdAction.Invalid;
    }

    /**
     * 根据<b>[FTDC返回]</b>买卖方向类型, 映射<b>[系统自定义]</b>买卖方向类型类型
     *
     * @param direction
     * @return
     */
    public static TrdDirection byDirection(char direction) {
        return
                // 买
                BUY == direction ? TrdDirection.Long
                        // 卖
                        : SELL == direction ? TrdDirection.Short
                        // 未知
                        : TrdDirection.Invalid;
    }

}
