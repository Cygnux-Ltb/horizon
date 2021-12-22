package io.horizon.ctp.gateway.rsp;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public final class FtdcTrade {

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

	/// 交易所代码
	private String ExchangeID;

	/// 成交编号
	private String TradeID;

	/// 买卖方向
	private char Direction;

	/// 报单编号
	private String OrderSysID;

	/// 会员代码
	private String ParticipantID;

	/// 客户代码
	private String ClientID;

	/// 交易角色
	private char TradingRole;

	/// 合约在交易所的代码
	private String ExchangeInstID;

	/// 开平标志
	private char OffsetFlag;

	/// 投机套保标志
	private char HedgeFlag;

	/// 价格
	private double Price;

	/// 数量
	private int Volume;

	/// 成交日期
	private String TradeDate;

	/// 成交时间
	private String TradeTime;

	/// 成交类型
	private char TradeType;

	/// 成交价来源
	private char PriceSource;

	/// 交易所交易员代码
	private String TraderID;

	/// 本地报单编号
	private String OrderLocalID;

	/// 结算会员编号
	private String ClearingPartID;

	/// 业务单元
	private String BusinessUnit;

	/// 序号
	private int SequenceNo;

	/// 交易日
	private String TradingDay;

	/// 结算编号
	private int SettlementID;

	/// 经纪公司报单编号
	private int BrokerOrderSeq;

	/// 成交来源
	private char TradeSource;

	/// 投资单元代码
	private String InvestUnitID;

}
