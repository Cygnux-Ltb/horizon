package io.horizon.ctp.gateway.msg;

import com.lmax.disruptor.EventFactory;
import io.horizon.ctp.gateway.rsp.FtdcDepthMarketData;
import io.horizon.ctp.gateway.rsp.FtdcInputOrder;
import io.horizon.ctp.gateway.rsp.FtdcInputOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcInvestorPosition;
import io.horizon.ctp.gateway.rsp.FtdcMdConnect;
import io.horizon.ctp.gateway.rsp.FtdcOrder;
import io.horizon.ctp.gateway.rsp.FtdcOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcRspInfo;
import io.horizon.ctp.gateway.rsp.FtdcTrade;
import io.horizon.ctp.gateway.rsp.FtdcTraderConnect;
import lombok.Getter;

import static io.horizon.ctp.gateway.msg.FtdcRspMsg.FtdcRspType.DepthMarketData;
import static io.horizon.ctp.gateway.msg.FtdcRspMsg.FtdcRspType.InputOrder;
import static io.horizon.ctp.gateway.msg.FtdcRspMsg.FtdcRspType.InputOrderAction;
import static io.horizon.ctp.gateway.msg.FtdcRspMsg.FtdcRspType.InvestorPosition;
import static io.horizon.ctp.gateway.msg.FtdcRspMsg.FtdcRspType.MdConnect;
import static io.horizon.ctp.gateway.msg.FtdcRspMsg.FtdcRspType.Order;
import static io.horizon.ctp.gateway.msg.FtdcRspMsg.FtdcRspType.OrderAction;
import static io.horizon.ctp.gateway.msg.FtdcRspMsg.FtdcRspType.RspInfo;
import static io.horizon.ctp.gateway.msg.FtdcRspMsg.FtdcRspType.Trade;
import static io.horizon.ctp.gateway.msg.FtdcRspMsg.FtdcRspType.TraderConnect;

/**
 * @author yellow013
 */
@Getter
public final class FtdcRspMsg {

    public static final EventFactory<FtdcRspMsg> FACTORY = FtdcRspMsg::new;

    private FtdcRspType type;

    // 返回交易接口连接信息
    private FtdcTraderConnect traderConnect;

    // 返回行情接口连接信息
    private FtdcMdConnect mdConnect;

    // 返回行情
    private FtdcDepthMarketData depthMarketData;

    // 返回持仓
    private FtdcInvestorPosition investorPosition;

    // 报单推送
    private FtdcOrder order;

    // 成交推送
    private FtdcTrade trade;

    // 返回报单错误
    private FtdcInputOrder inputOrder;

    // 返回撤单提交错误
    private FtdcInputOrderAction inputOrderAction;

    // 返回撤单错误
    private FtdcOrderAction orderAction;

    // 错误消息
    private FtdcRspInfo rspInfo;

    // 是否最后一条
    private boolean isLast = true;

    private FtdcRspMsg() {
        // For EventFactory call
    }

    private FtdcRspMsg(FtdcMdConnect mdConnect) {
        this.type = MdConnect;
        this.mdConnect = mdConnect;
    }

    public static FtdcRspMsg with(FtdcMdConnect mdConnect) {
        return new FtdcRspMsg(mdConnect);
    }

    private FtdcRspMsg(FtdcTraderConnect traderConnect) {
        this.type = TraderConnect;
        this.traderConnect = traderConnect;
    }

    public static FtdcRspMsg with(FtdcTraderConnect traderConnect) {
        return new FtdcRspMsg(traderConnect);
    }

    private FtdcRspMsg(FtdcDepthMarketData depthMarketData) {
        this.type = DepthMarketData;
        this.depthMarketData = depthMarketData;
    }

    public static FtdcRspMsg with(FtdcDepthMarketData depthMarketData) {
        return new FtdcRspMsg(depthMarketData);
    }

    private FtdcRspMsg(FtdcInvestorPosition investorPosition, boolean isLast) {
        this.type = InvestorPosition;
        this.investorPosition = investorPosition;
        this.isLast = isLast;
    }

    public static FtdcRspMsg with(FtdcInvestorPosition investorPosition, boolean isLast) {
        return new FtdcRspMsg(investorPosition, isLast);
    }

    private FtdcRspMsg(FtdcOrder order, boolean isLast) {
        this.type = Order;
        this.order = order;
        this.isLast = isLast;
    }

    public static FtdcRspMsg with(FtdcOrder order, boolean isLast) {
        return new FtdcRspMsg(order, isLast);
    }

    private FtdcRspMsg(FtdcTrade trade) {
        this.type = Trade;
        this.trade = trade;
    }

    public static FtdcRspMsg with(FtdcTrade trade) {
        return new FtdcRspMsg(trade);
    }

    private FtdcRspMsg(FtdcInputOrder inputOrder) {
        this.type = InputOrder;
        this.inputOrder = inputOrder;
    }

    public static FtdcRspMsg with(FtdcInputOrder inputOrder) {
        return new FtdcRspMsg(inputOrder);
    }

    private FtdcRspMsg(FtdcInputOrderAction inputOrderAction) {
        this.type = InputOrderAction;
        this.inputOrderAction = inputOrderAction;
    }

    public static FtdcRspMsg with(FtdcInputOrderAction inputOrderAction) {
        return new FtdcRspMsg(inputOrderAction);
    }

    private FtdcRspMsg(FtdcOrderAction orderAction) {
        this.type = OrderAction;
        this.orderAction = orderAction;
    }

    public static FtdcRspMsg with(FtdcOrderAction orderAction) {
        return new FtdcRspMsg(orderAction);
    }

    private FtdcRspMsg(FtdcRspInfo rspInfo, boolean isLast) {
        this.type = RspInfo;
        this.rspInfo = rspInfo;
        this.isLast = isLast;
    }

    public static FtdcRspMsg with(FtdcRspInfo rspInfo, boolean isLast) {
        return new FtdcRspMsg(rspInfo, isLast);
    }

    /**
     * @author yellow013
     */
    public enum FtdcRspType {

        DepthMarketData,

        TraderConnect,

        MdConnect,

        InvestorPosition,

        Order,

        Trade,

        InputOrder,

        InputOrderAction,

        OrderAction,

        RspInfo,

        Other

    }

}
