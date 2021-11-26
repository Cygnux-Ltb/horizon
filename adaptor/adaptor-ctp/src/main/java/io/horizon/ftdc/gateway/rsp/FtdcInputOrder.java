package io.horizon.ftdc.gateway.rsp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public final class FtdcInputOrder {

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

	/// 用户强评标志
	private int UserForceClose;

	/// 互换单标志
	private int IsSwapOrder;

	/// 交易所代码
	private String ExchangeID;

	/// 投资单元代码
	private String InvestUnitID;

	/// 资金账号
	private String AccountID;

	/// 币种代码
	private String CurrencyID;

	/// 交易编码
	private String ClientID;

	/// IP地址
	private String IPAddress;

	/// MAC地址
	private String MacAddress;

}
