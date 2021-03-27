package io.horizon.ftdc.gateway.msg.rsp;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public final class FtdcOrderAction {

	/// 经纪公司代码
	private String BrokerID;

	/// 投资者代码
	private String InvestorID;

	/// 报单操作引用
	private int OrderActionRef;

	/// 报单引用
	private String OrderRef;

	/// 请求编号
	private int RequestID;

	/// 前置编号
	private int FrontID;

	/// 会话编号
	private int SessionID;

	/// 交易所代码
	private String ExchangeID;

	/// 报单编号
	private String OrderSysID;

	/// 操作标志
	private char ActionFlag;

	/// 价格
	private double LimitPrice;

	/// 数量变化
	private int VolumeChange;

	/// 操作日期
	private String ActionDate;

	/// 操作时间
	private String ActionTime;

	/// 交易所交易员代码
	private String TraderID;

	/// 安装编号
	private int InstallID;

	/// 本地报单编号
	private String OrderLocalID;

	/// 操作本地编号
	private String ActionLocalID;

	/// 会员代码
	private String ParticipantID;

	/// 客户代码
	private String ClientID;

	/// 业务单元
	private String BusinessUnit;

	/// 报单操作状态
	private char OrderActionStatus;

	/// 用户代码
	private String UserID;

	/// 状态信息
	private String StatusMsg;

	/// 合约代码
	private String InstrumentID;

	/// 营业部编号
	private String BranchID;

	/// 投资单元代码
	private String InvestUnitID;

	/// IP地址
	private String IPAddress;

	/// MAC地址
	private String MacAddress;

}
