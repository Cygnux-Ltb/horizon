package io.horizon.definition.adaptor.bean;

public final class BalanceReport {

	private final int investorId;
	private final int balance;

	public BalanceReport(int investorId, int balance) {
		this.investorId = investorId;
		this.balance = balance;
	}

	public int getInvestorId() {
		return investorId;
	}

	public int getBalance() {
		return balance;
	}

}
