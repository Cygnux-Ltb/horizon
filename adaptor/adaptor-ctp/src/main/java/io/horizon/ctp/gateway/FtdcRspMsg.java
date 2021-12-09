package io.horizon.ctp.gateway;

import io.horizon.ctp.gateway.rsp.FtdcDepthMarketData;
import io.horizon.ctp.gateway.rsp.FtdcInputOrder;
import io.horizon.ctp.gateway.rsp.FtdcInputOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcInvestorPosition;
import io.horizon.ctp.gateway.rsp.FtdcMdConnect;
import io.horizon.ctp.gateway.rsp.FtdcOrder;
import io.horizon.ctp.gateway.rsp.FtdcOrderAction;
import io.horizon.ctp.gateway.rsp.FtdcTrade;
import io.horizon.ctp.gateway.rsp.FtdcTraderConnect;
import lombok.Getter;

@Getter
public final class FtdcRspMsg {

	private final FtdcRspType rspType;

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

	// 返回撤单错误
	private FtdcInputOrderAction inputOrderAction;

	// 返回撤单错误
	private FtdcOrderAction orderAction;

	// 是否最后一条
	private boolean isLast = true;

	public FtdcRspMsg(FtdcTraderConnect traderConnect) {
		this.rspType = FtdcRspType.TraderConnect;
		this.traderConnect = traderConnect;
	}

	public FtdcRspMsg(FtdcMdConnect mdConnect) {
		this.rspType = FtdcRspType.MdConnect;
		this.mdConnect = mdConnect;
	}

	public FtdcRspMsg(FtdcDepthMarketData depthMarketData) {
		this.rspType = FtdcRspType.DepthMarketData;
		this.depthMarketData = depthMarketData;
	}

	public FtdcRspMsg(FtdcInvestorPosition investorPosition, boolean isLast) {
		this.rspType = FtdcRspType.InvestorPosition;
		this.investorPosition = investorPosition;
		this.isLast = isLast;
	}

	public FtdcRspMsg(FtdcOrder order, boolean isLast) {
		this.rspType = FtdcRspType.Order;
		this.order = order;
		this.isLast = isLast;
	}

	public FtdcRspMsg(FtdcTrade trade) {
		this.rspType = FtdcRspType.Trade;
		this.trade = trade;
	}

	public FtdcRspMsg(FtdcInputOrder inputOrder) {
		this.rspType = FtdcRspType.InputOrder;
		this.inputOrder = inputOrder;
	}

	public FtdcRspMsg(FtdcInputOrderAction inputOrderAction) {
		this.rspType = FtdcRspType.InputOrderAction;
		this.inputOrderAction = inputOrderAction;
	}

	public FtdcRspMsg(FtdcOrderAction orderAction) {
		this.rspType = FtdcRspType.OrderAction;
		this.orderAction = orderAction;
	}

	public static enum FtdcRspType {

		DepthMarketData,

		TraderConnect,

		MdConnect,

		InvestorPosition,

		Order,

		Trade,

		InputOrder,

		InputOrderAction,

		OrderAction,

		Other;

	}

}
