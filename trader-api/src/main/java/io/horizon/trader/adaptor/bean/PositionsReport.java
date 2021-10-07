package io.horizon.trader.adaptor.bean;

public class PositionsReport {

	private final int investorId;
	private final String instrumentCode;
	private final int currentQty;

	public PositionsReport(int investorId, String instrumentCode, int currentQty) {
		super();
		this.investorId = investorId;
		this.instrumentCode = instrumentCode;
		this.currentQty = currentQty;
	}

	public int getInvestorId() {
		return investorId;
	}

	public String getInstrumentCode() {
		return instrumentCode;
	}

	public int getCurrentQty() {
		return currentQty;
	}

}
