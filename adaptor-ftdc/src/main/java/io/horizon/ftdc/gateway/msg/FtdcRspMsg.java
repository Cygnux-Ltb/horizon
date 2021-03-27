package io.horizon.ftdc.gateway;

import io.horizon.ftdc.gateway.bean.FtdcDepthMarketData;
import io.horizon.ftdc.gateway.bean.FtdcInputOrder;
import io.horizon.ftdc.gateway.bean.FtdcInputOrderAction;
import io.horizon.ftdc.gateway.bean.FtdcInvestorPosition;
import io.horizon.ftdc.gateway.bean.FtdcMdConnect;
import io.horizon.ftdc.gateway.bean.FtdcOrder;
import io.horizon.ftdc.gateway.bean.FtdcOrderAction;
import io.horizon.ftdc.gateway.bean.FtdcTrade;
import io.horizon.ftdc.gateway.bean.FtdcTraderConnect;
import lombok.Getter;

public final class FtdcRspMsg {

	@Getter
	private final RspType rspType;

	// 返回交易接口连接信息
	@Getter
	private FtdcTraderConnect ftdcTraderConnect;

	// 返回行情接口连接信息
	@Getter
	private FtdcMdConnect ftdcMdConnect;

	// 返回行情
	@Getter
	private FtdcDepthMarketData ftdcDepthMarketData;

	// 返回持仓
	@Getter
	private FtdcInvestorPosition ftdcInvestorPosition;

	// 报单推送
	@Getter
	private FtdcOrder ftdcOrder;

	// 成交推送
	@Getter
	private FtdcTrade ftdcTrade;

	// 返回报单错误
	@Getter
	private FtdcInputOrder ftdcInputOrder;

	// 返回撤单错误
	@Getter
	private FtdcInputOrderAction ftdcInputOrderAction;

	// 返回撤单错误
	@Getter
	private FtdcOrderAction ftdcOrderAction;

	// 是否最后一条
	@Getter
	private boolean isLast = true;

	public FtdcRspMsg(FtdcTraderConnect ftdcTraderConnect) {
		this.rspType = RspType.FtdcTraderConnect;
		this.ftdcTraderConnect = ftdcTraderConnect;
	}

	public FtdcRspMsg(FtdcMdConnect ftdcMdConnect) {
		this.rspType = RspType.FtdcMdConnect;
		this.ftdcMdConnect = ftdcMdConnect;
	}

	public FtdcRspMsg(FtdcDepthMarketData ftdcDepthMarketData) {
		this.rspType = RspType.FtdcDepthMarketData;
		this.ftdcDepthMarketData = ftdcDepthMarketData;
	}

	public FtdcRspMsg(FtdcInvestorPosition ftdcInvestorPosition, boolean isLast) {
		this.rspType = RspType.FtdcInvestorPosition;
		this.ftdcInvestorPosition = ftdcInvestorPosition;
		this.isLast = isLast;
	}

	public FtdcRspMsg(FtdcOrder ftdcOrder, boolean isLast) {
		this.rspType = RspType.FtdcOrder;
		this.ftdcOrder = ftdcOrder;
		this.isLast = isLast;
	}

	public FtdcRspMsg(FtdcTrade ftdcTrade) {
		this.rspType = RspType.FtdcTrade;
		this.ftdcTrade = ftdcTrade;
	}

	public FtdcRspMsg(FtdcInputOrder ftdcInputOrder) {
		this.rspType = RspType.FtdcInputOrder;
		this.ftdcInputOrder = ftdcInputOrder;
	}

	public FtdcRspMsg(FtdcInputOrderAction ftdcInputOrderAction) {
		this.rspType = RspType.FtdcInputOrderAction;
		this.ftdcInputOrderAction = ftdcInputOrderAction;
	}

	public FtdcRspMsg(FtdcOrderAction ftdcOrderAction) {
		this.rspType = RspType.FtdcOrderAction;
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

		Other,

		;

	}

}
