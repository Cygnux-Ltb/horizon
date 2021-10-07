package io.horizon.trader.adaptor.bean;

public class BalanceReport {

	private final int investorId;
	private final long balance;

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
