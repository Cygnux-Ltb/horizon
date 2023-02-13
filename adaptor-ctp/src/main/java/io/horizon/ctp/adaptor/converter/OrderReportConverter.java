package io.horizon.ctp.adaptor.converter;

import io.horizon.ctp.adaptor.FtdcConstMapper;
import io.horizon.ctp.gateway.rsp.FtdcInputOrder;
import io.horizon.ctp.gateway.rsp.FtdcInputOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcOrder;
import io.horizon.ctp.gateway.rsp.FtdcOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcTrade;
import io.horizon.trader.transport.outbound.TdxOrderReport;
import io.mercury.common.log.Log4j2LoggerFactory;
import org.slf4j.Logger;

import static io.horizon.ctp.adaptor.FtdcConstMapper.byDirection;
import static io.horizon.ctp.adaptor.FtdcConstMapper.byOffsetFlag;
import static io.horizon.ctp.adaptor.FtdcConstMapper.byOrderStatus;
import static io.horizon.ctp.adaptor.OrderRefKeeper.getOrdSysId;
import static io.horizon.market.instrument.futures.ChinaFutures.FixedMultiplier;
import static io.horizon.trader.order.enums.OrdStatus.NewRejected;
import static io.horizon.trader.order.enums.OrdStatus.Unprovided;
import static io.mercury.common.datetime.EpochTime.getEpochMicros;
import static io.mercury.common.util.StringSupport.removeNonDigits;

/**
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
     * @param order FtdcInputOrder
     * @return OrderReport
     */
    public TdxOrderReport withFtdcInputOrder(FtdcInputOrder order) {
        String orderRef = order.getOrderRef();
        long ordSysId = getOrdSysId(orderRef);
        var builder = TdxOrderReport.newBuilder();
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
        builder.setStatus(NewRejected.getTdxValue());
        // 买卖方向
        var direction = byDirection(order.getDirection());
        builder.setDirection(direction.getTdxValue());
        // 组合开平标志
        var action = FtdcConstMapper.byOffsetFlag(order.getCombOffsetFlag());
        builder.setAction(action.getTdxValue());
        // 委托数量
        builder.setOfferQty(order.getVolumeTotalOriginal());
        // 委托价格
        builder.setOfferPrice(FixedMultiplier.toLong(order.getLimitPrice()));

        TdxOrderReport report = builder.build();
        log.info("FtdcInputOrder conversion to OrderReport -> {}", report);
        return report;
    }

    /**
     * 订单回报消息转换<br>
     * <br>
     * FtdcOrder -> OrderReport
     *
     * @param order FtdcOrder
     * @return OrderReport
     */
    public TdxOrderReport withFtdcOrder(FtdcOrder order) {
        var orderRef = order.getOrderRef();
        long ordSysId = getOrdSysId(orderRef);
        var builder = TdxOrderReport.newBuilder();
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
        var ordStatus = byOrderStatus(order.getOrderStatus());
        builder.setStatus(ordStatus.getTdxValue());
        // 买卖方向
        var direction = byDirection(order.getDirection());
        builder.setDirection(direction.getTdxValue());
        // 组合开平标志
        var action = FtdcConstMapper.byOffsetFlag(order.getCombOffsetFlag());
        builder.setAction(action.getTdxValue());
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
     * @param trade FtdcTrade
     * @return OrderReport
     */
    public TdxOrderReport withFtdcTrade(FtdcTrade trade) {
        var orderRef = trade.getOrderRef();
        long ordSysId = getOrdSysId(orderRef);
        var builder = TdxOrderReport.newBuilder();
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
        builder.setStatus(Unprovided.getTdxValue());
        // 买卖方向
        var direction = byDirection(trade.getDirection());
        builder.setDirection(direction.getTdxValue());
        // 组合开平标志
        var action = byOffsetFlag(trade.getOffsetFlag());
        builder.setAction(action.getTdxValue());
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
     * @param inputOrderAction FtdcInputOrderAction
     * @return OrderReport
     */
    public TdxOrderReport withFtdcInputOrderAction(FtdcInputOrderAction inputOrderAction) {

        return null;
    }

    /**
     * 撤单错误回报消息转换2<br>
     * <br>
     * FtdcOrderAction -> OrderReport
     *
     * @param orderAction FtdcOrderAction
     * @return OrderReport
     */
    public TdxOrderReport withFtdcOrderAction(FtdcOrderAction orderAction) {

        return null;
    }

}
