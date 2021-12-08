package io.horizon.trader.adaptor.bean;

import io.horizon.market.instrument.PriceMultiplier;

public class BalanceReport {

	private final int investorId;
	private final long balance;

	public static final PriceMultiplier MULTIPLIER = PriceMultiplier.MULTIPLIER_10000;

	public BalanceReport(int investorId, long balance) {
		super();
		this.investorId = investorId;
		this.balance = balance;
	}

	public int getInvestorId() {
		return investorId;
	}

	public long getBalance() {
		return balance;
	}

}
