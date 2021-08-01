package io.horizon.ftdc.gateway.msg.rsp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public final class FtdcOrder {

	/// 经纪公司代码
	private String BrokerID;

	/// 投资者代码
	private String InvestorID;

	/// 合约代码
	private String InstrumentID;

	/// 报单引用
	private String OrderRef;

	/// 用户代码
	private String UserID;

	/// 报单价格条件
	private char OrderPriceType;

	/// 买卖方向
	private char Direction;

	/// 组合开平标志
	private String CombOffsetFlag;

	/// 组合投机套保标志
	private String CombHedgeFlag;

	/// 价格
	private double LimitPrice;

	/// 数量
	private int VolumeTotalOriginal;

	/// 有效期类型
	private char TimeCondition;

	/// GTD日期
	private String GTDDate;

	/// 成交量类型
	private char VolumeCondition;

	/// 最小成交量
	private int MinVolume;

	/// 触发条件
	private char ContingentCondition;

	/// 止损价
	private double StopPrice;

	/// 强平原因
	private char ForceCloseReason;

	/// 自动挂起标志
	private int IsAutoSuspend;

	/// 业务单元
	private String BusinessUnit;

	/// 请求编号
	private int RequestID;

	/// 本地报单编号
	private String OrderLocalID;

	/// 交易所代码
	private String ExchangeID;

	/// 会员代码
	private String ParticipantID;

	/// 客户代码
	private String ClientID;

	/// 合约在交易所的代码
	private String ExchangeInstID;

	/// 交易所交易员代码
	private String TraderID;

	/// 安装编号
	private int InstallID;

	/// 报单提交状态
	private char OrderSubmitStatus;

	/// 报单提示序号
	private int NotifySequence;

	/// 交易日
	private String TradingDay;

	/// 结算编号
	private int SettlementID;

	/// 报单编号
	private String OrderSysID;

	/// 报单来源
	private char OrderSource;

	/// 报单状态
	private char OrderStatus;

	/// 报单类型
	private char OrderType;

	/// 今成交数量
	private int VolumeTraded;

	/// 剩余数量
	private int VolumeTotal;

	/// 报单日期
	private String InsertDate;

	/// 委托时间
	private String InsertTime;

	/// 激活时间
	private String ActiveTime;

	/// 挂起时间
	private String SuspendTime;

	/// 最后修改时间
	private String UpdateTime;

	/// 撤销时间
	private String CancelTime;

	/// 最后修改交易所交易员代码
	private String ActiveTraderID;

	/// 结算会员编号
	private String ClearingPartID;

	/// 序号
	private int SequenceNo;

	/// 前置编号
	private int FrontID;

	/// 会话编号
	private int SessionID;

	/// 用户端产品信息
	private String UserProductInfo;

	/// 状态信息
	private String StatusMsg;

	/// 用户强评标志
	private int UserForceClose;

	/// 操作用户代码
	private String ActiveUserID;

	/// 经纪公司报单编号
	private int BrokerOrderSeq;

	/// 相关报单
	private String RelativeOrderSysID;

	/// 郑商所成交数量
	private int ZCETotalTradedVolume;

	/// 互换单标志
	private int IsSwapOrder;

	/// 营业部编号
	private String BranchID;

	/// 投资单元代码
	private String InvestUnitID;

	/// 资金账号
	private String AccountID;

	/// 币种代码
	private String CurrencyID;

	/// IP地址
	private String IPAddress;

	/// MAC地址
	private String MacAddress;

}
