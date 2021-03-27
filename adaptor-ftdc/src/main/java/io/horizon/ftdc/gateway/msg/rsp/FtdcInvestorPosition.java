package io.horizon.ftdc.gateway.msg.rsp;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public final class FtdcInvestorPosition {

	/// 合约代码
	private String InstrumentID;

	/// 经纪公司代码
	private String BrokerID;

	/// 投资者代码
	private String InvestorID;

	/// 持仓多空方向
	private char PosiDirection;

	/// 投机套保标志
	private char HedgeFlag;

	/// 持仓日期
	private char PositionDate;

	/// 上日持仓
	private int YdPosition;

	/// 今日持仓
	private int Position;

	/// 多头冻结
	private int LongFrozen;

	/// 空头冻结
	private int ShortFrozen;

	/// 开仓冻结金额
	private double LongFrozenAmount;

	/// 开仓冻结金额
	private double ShortFrozenAmount;

	/// 开仓量
	private int OpenVolume;

	/// 平仓量
	private int CloseVolume;

	/// 开仓金额
	private double OpenAmount;

	/// 平仓金额
	private double CloseAmount;

	/// 持仓成本
	private double PositionCost;

	/// 上次占用的保证金
	private double PreMargin;

	/// 占用的保证金
	private double UseMargin;

	/// 冻结的保证金
	private double FrozenMargin;

	/// 冻结的资金
	private double FrozenCash;

	/// 冻结的手续费
	private double FrozenCommission;

	/// 资金差额
	private double CashIn;

	/// 手续费
	private double Commission;

	/// 平仓盈亏
	private double CloseProfit;

	/// 持仓盈亏
	private double PositionProfit;

	/// 上次结算价
	private double PreSettlementPrice;

	/// 本次结算价
	private double SettlementPrice;

	/// 交易日
	private String TradingDay;

	/// 结算编号
	private int SettlementID;

	/// 开仓成本
	private double OpenCost;

	/// 交易所保证金
	private double ExchangeMargin;

	/// 组合成交形成的持仓
	private int CombPosition;

	/// 组合多头冻结
	private int CombLongFrozen;

	/// 组合空头冻结
	private int CombShortFrozen;

	/// 逐日盯市平仓盈亏
	private double CloseProfitByDate;

	/// 逐笔对冲平仓盈亏
	private double CloseProfitByTrade;

	/// 今日持仓
	private int TodayPosition;

	/// 保证金率
	private double MarginRateByMoney;

	/// 保证金率(按手数)
	private double MarginRateByVolume;

	/// 执行冻结
	private int StrikeFrozen;

	/// 执行冻结金额
	private double StrikeFrozenAmount;

	/// 放弃执行冻结
	private int AbandonFrozen;

	/// 交易所代码
	private String ExchangeID;

	/// 执行冻结的昨仓
	private int YdStrikeFrozen;

	/// 投资单元代码
	private String InvestUnitID;

	/// 大商所持仓成本差值，只有大商所使用
	private double PositionCostOffset;

}
