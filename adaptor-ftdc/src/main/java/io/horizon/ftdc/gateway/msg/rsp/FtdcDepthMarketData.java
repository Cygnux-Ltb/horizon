package io.horizon.ftdc.gateway.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public final class FtdcDepthMarketData {

	// 交易日
	private String TradingDay;

	// InstrumentID
	private String InstrumentID;

	// 交易所ID
	private String ExchangeID;

	// 合约在交易所的代码
	private String ExchangeInstID;

	// 最新价
	private double LastPrice;

	// 上次结算价
	private double PreSettlementPrice;

	// 昨收盘
	private double PreClosePrice;

	// 昨持仓量
	private double PreOpenInterest;

	// 开盘价
	private double OpenPrice;

	// 最高价
	private double HighestPrice;

	// 最低价
	private double LowestPrice;

	// 成交量
	private int Volume;

	// 成交金额
	private double Turnover;

	// 持仓量
	private double OpenInterest;

	// 收盘价
	private double ClosePrice;

	// 结算价
	private double SettlementPrice;

	// 涨停板价
	private double UpperLimitPrice;

	// 跌停板价
	private double LowerLimitPrice;

	// 昨Delta
	private double PreDelta;

	// 今Delta
	private double CurrDelta;

	/* 五档买价卖价及买量卖量 v */
	private double BidPrice1;
	private int BidVolume1;
	private double AskPrice1;
	private int AskVolume1;
	private double BidPrice2;
	private int BidVolume2;
	private double AskPrice2;
	private int AskVolume2;
	private double BidPrice3;
	private int BidVolume3;
	private double AskPrice3;
	private int AskVolume3;
	private double BidPrice4;
	private int BidVolume4;
	private double AskPrice4;
	private int AskVolume4;
	private double BidPrice5;
	private int BidVolume5;
	private double AskPrice5;
	private int AskVolume5;
	/* 五档买价卖价及买量卖量 ^ */

	// 平均价格
	private double AveragePrice;

	// 更新时间
	private String UpdateTime;

	// 更新毫秒数
	private int UpdateMillisec;

	// 业务日期
	private String ActionDay;

}
