package io.horizon.ftdc.gateway;

import io.horizon.ftdc.gateway.rsp.FtdcDepthMarketData;
import io.horizon.ftdc.gateway.rsp.FtdcInputOrder;
import io.horizon.ftdc.gateway.rsp.FtdcInputOrderAction;
import io.horizon.ftdc.gateway.rsp.FtdcInvestorPosition;
import io.horizon.ftdc.gateway.rsp.FtdcMdConnect;
import io.horizon.ftdc.gateway.rsp.FtdcOrder;
import io.horizon.ftdc.gateway.rsp.FtdcOrderAction;
import io.horizon.ftdc.gateway.rsp.FtdcTrade;
import io.horizon.ftdc.gateway.rsp.FtdcTraderConnect;
import lombok.Getter;

@Getter
public final class RspMsg {

	private final RspType type;

	// 返回交易接口连接信息
	private FtdcTraderConnect ftdcTraderConnect;

	// 返回行情接口连接信息
	private FtdcMdConnect ftdcMdConnect;

	// 返回行情
	private FtdcDepthMarketData ftdcDepthMarketData;

	// 返回持仓
	private FtdcInvestorPosition ftdcInvestorPosition;

	// 报单推送
	private FtdcOrder ftdcOrder;

	// 成交推送
	private FtdcTrade ftdcTrade;

	// 返回报单错误
	private FtdcInputOrder ftdcInputOrder;

	// 返回撤单错误
	private FtdcInputOrderAction ftdcInputOrderAction;

	// 返回撤单错误
	private FtdcOrderAction ftdcOrderAction;

	// 是否最后一条
	private boolean isLast = true;

	public RspMsg(FtdcTraderConnect ftdcTraderConnect) {
		this.type = RspType.FtdcTraderConnect;
		this.ftdcTraderConnect = ftdcTraderConnect;
	}

	public RspMsg(FtdcMdConnect ftdcMdConnect) {
		this.type = RspType.FtdcMdConnect;
		this.ftdcMdConnect = ftdcMdConnect;
	}

	public RspMsg(FtdcDepthMarketData ftdcDepthMarketData) {
		this.type = RspType.FtdcDepthMarketData;
		this.ftdcDepthMarketData = ftdcDepthMarketData;
	}

	public RspMsg(FtdcInvestorPosition ftdcInvestorPosition, boolean isLast) {
		this.type = RspType.FtdcInvestorPosition;
		this.ftdcInvestorPosition = ftdcInvestorPosition;
		this.isLast = isLast;
	}

	public RspMsg(FtdcOrder ftdcOrder, boolean isLast) {
		this.type = RspType.FtdcOrder;
		this.ftdcOrder = ftdcOrder;
		this.isLast = isLast;
	}

	public RspMsg(FtdcTrade ftdcTrade) {
		this.type = RspType.FtdcTrade;
		this.ftdcTrade = ftdcTrade;
	}

	public RspMsg(FtdcInputOrder ftdcInputOrder) {
		this.type = RspType.FtdcInputOrder;
		this.ftdcInputOrder = ftdcInputOrder;
	}

	public RspMsg(FtdcInputOrderAction ftdcInputOrderAction) {
		this.type = RspType.FtdcInputOrderAction;
		this.ftdcInputOrderAction = ftdcInputOrderAction;
	}

	public RspMsg(FtdcOrderAction ftdcOrderAction) {
		this.type = RspType.FtdcOrderAction;
		this.ftdcOrderAction = ftdcOrderAction;
	}

	public static enum RspType {

		FtdcDepthMarketData,

		FtdcTraderConnect,

		FtdcMdConnect,

		FtdcInvestorPosition,

		FtdcOrder,

		FtdcTrade,

		FtdcInputOrder,

		FtdcInputOrderAction,

		FtdcOrderAction,

		Other;

	}

}
