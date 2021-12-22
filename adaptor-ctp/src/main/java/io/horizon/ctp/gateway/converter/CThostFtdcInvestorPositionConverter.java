package io.horizon.ctp.gateway.converter;

import java.util.function.Function;

import ctp.thostapi.CThostFtdcInvestorPositionField;
import io.horizon.ctp.gateway.rsp.FtdcInvestorPosition;

public class CThostFtdcInvestorPositionConverter
		implements Function<CThostFtdcInvestorPositionField, FtdcInvestorPosition> {

	@Override
	public FtdcInvestorPosition apply(CThostFtdcInvestorPositionField field) {
		return new FtdcInvestorPosition()
				/// 合约代码
				.setInstrumentID(field.getInstrumentID())
				/// 经纪公司代码
				.setBrokerID(field.getBrokerID())
				/// 投资者代码
				.setInvestorID(field.getInvestorID())
				/// 持仓多空方向
				.setPosiDirection(field.getPosiDirection())
				/// 投机套保标志
				.setHedgeFlag(field.getHedgeFlag())
				/// 持仓日期
				.setPositionDate(field.getPositionDate())
				/// 上日持仓
				.setYdPosition(field.getYdPosition())
				/// 今日持仓
				.setPosition(field.getPosition())
				/// 多头冻结
				.setLongFrozen(field.getLongFrozen())
				/// 空头冻结
				.setShortFrozen(field.getShortFrozen())
				/// 开仓冻结金额
				.setLongFrozenAmount(field.getLongFrozenAmount())
				/// 开仓冻结金额
				.setShortFrozenAmount(field.getShortFrozenAmount())
				/// 开仓量
				.setOpenVolume(field.getOpenVolume())
				/// 平仓量
				.setCloseVolume(field.getCloseVolume())
				/// 开仓金额
				.setOpenAmount(field.getOpenAmount())
				/// 平仓金额
				.setCloseAmount(field.getCloseAmount())
				/// 持仓成本
				.setPositionCost(field.getPositionCost())
				/// 上次占用的保证金
				.setPreMargin(field.getPreMargin())
				/// 占用的保证金
				.setUseMargin(field.getUseMargin())
				/// 冻结的保证金
				.setFrozenMargin(field.getFrozenMargin())
				/// 冻结的资金
				.setFrozenCash(field.getFrozenCash())
				/// 冻结的手续费
				.setFrozenCommission(field.getFrozenCommission())
				/// 资金差额
				.setCashIn(field.getCashIn())
				/// 手续费
				.setCommission(field.getCommission())
				/// 平仓盈亏
				.setCloseProfit(field.getCloseProfit())
				/// 持仓盈亏
				.setPositionProfit(field.getPositionProfit())
				/// 上次结算价
				.setPreSettlementPrice(field.getPreSettlementPrice())
				/// 本次结算价
				.setSettlementPrice(field.getSettlementPrice())
				/// 交易日
				.setTradingDay(field.getTradingDay())
				/// 结算编号
				.setSettlementID(field.getSettlementID())
				/// 开仓成本
				.setOpenCost(field.getOpenCost())
				/// 交易所保证金
				.setExchangeMargin(field.getExchangeMargin())
				/// 组合成交形成的持仓
				.setCombPosition(field.getCombPosition())
				/// 组合多头冻结
				.setCombLongFrozen(field.getCombLongFrozen())
				/// 组合空头冻结
				.setCombShortFrozen(field.getCombShortFrozen())
				/// 逐日盯市平仓盈亏
				.setCloseProfitByDate(field.getCloseProfitByDate())
				/// 逐笔对冲平仓盈亏
				.setCloseProfitByTrade(field.getCloseProfitByTrade())
				/// 今日持仓
				.setTodayPosition(field.getTodayPosition())
				/// 保证金率
				.setMarginRateByMoney(field.getMarginRateByMoney())
				/// 保证金率(按手数)
				.setMarginRateByVolume(field.getMarginRateByVolume())
				/// 执行冻结
				.setStrikeFrozen(field.getStrikeFrozen())
				/// 执行冻结金额
				.setStrikeFrozenAmount(field.getStrikeFrozenAmount())
				/// 放弃执行冻结
				.setAbandonFrozen(field.getAbandonFrozen())
				/// 交易所代码
				.setExchangeID(field.getExchangeID())
				/// 执行冻结的昨仓
				.setYdStrikeFrozen(field.getYdStrikeFrozen())
				/// 投资单元代码
				.setInvestUnitID(field.getInvestUnitID())
				/// 大商所持仓成本差值, 只有大商所使用
				// 6.3.15 版本使用
				// .setPositionCostOffset(field.getPositionCostOffset())
		;
	}

}
