package io.horizon.ftdc.gateway.converter;

import java.util.function.Function;

import ctp.thostapi.CThostFtdcTradingAccountField;
import io.horizon.ftdc.gateway.msg.rsp.FtdcTradingAccount;

public class CThostFtdcTradingAccountConverter implements Function<CThostFtdcTradingAccountField, FtdcTradingAccount> {

	@Override
	public FtdcTradingAccount apply(CThostFtdcTradingAccountField field) {
		return new FtdcTradingAccount()

				.setBrokerID(field.getBrokerID())

				.setAccountID(field.getAccountID())

				.setPreMortgage(field.getPreMortgage())

				.setPreCredit(field.getPreCredit())

				.setPreDeposit(field.getPreDeposit())

				.setPreBalance(field.getPreBalance())

				.setPreMargin(field.getPreMargin())

				.setInterestBase(field.getInterestBase())

				.setInterest(field.getInterest())

				.setDeposit(field.getDeposit())

				.setWithdraw(field.getWithdraw())

				.setFrozenMargin(field.getFrozenMargin())

				.setFrozenCash(field.getFrozenCash())

				.setFrozenCommission(field.getFrozenCommission())

				.setCurrMargin(field.getCurrMargin())

				.setCashIn(field.getCashIn())

				.setCommission(field.getCommission())

				.setCloseProfit(field.getCloseProfit())

				.setPositionProfit(field.getPositionProfit())

				.setBalance(field.getBalance())

				.setAvailable(field.getAvailable())

				.setWithdrawQuota(field.getWithdrawQuota())

				.setReserve(field.getReserve())

				.setTradingDay(field.getTradingDay())

				.setSettlementID(field.getSettlementID())

				.setCredit(field.getCredit())

				.setMortgage(field.getMortgage())

				.setExchangeMargin(field.getExchangeMargin())

				.setDeliveryMargin(field.getDeliveryMargin())

				.setExchangeDeliveryMargin(field.getExchangeDeliveryMargin())

				.setReserveBalance(field.getReserveBalance())

				.setCurrencyID(field.getCurrencyID())

				.setPreFundMortgageIn(field.getPreFundMortgageIn())

				.setPreFundMortgageOut(field.getPreFundMortgageOut())

				.setFundMortgageIn(field.getFundMortgageIn())

				.setFundMortgageOut(field.getFundMortgageOut())

				.setFundMortgageAvailable(field.getFundMortgageAvailable())

				.setMortgageableFund(field.getMortgageableFund())

				.setSpecProductMargin(field.getSpecProductMargin())

				.setSpecProductFrozenMargin(field.getSpecProductFrozenMargin())

				.setSpecProductCommission(field.getSpecProductCommission())

				.setSpecProductFrozenCommission(field.getSpecProductFrozenCommission())

				.setSpecProductPositionProfit(field.getSpecProductPositionProfit())

				.setSpecProductCloseProfit(field.getSpecProductCloseProfit())

				.setSpecProductPositionProfitByAlg(field.getSpecProductPositionProfitByAlg())

				.setSpecProductExchangeMargin(field.getSpecProductExchangeMargin())

				.setBizType(field.getBizType())

				.setFrozenSwap(field.getFrozenSwap())

				.setRemainSwap(field.getRemainSwap());
	}

}
