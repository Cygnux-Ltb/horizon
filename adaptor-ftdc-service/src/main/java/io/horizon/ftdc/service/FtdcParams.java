package io.horizon.ftdc.service;

import io.mercury.serialization.json.JsonWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class FtdcParams {

	private String traderAddr;
	private String mdAddr;

	private String appId;
	private String brokerId;
	private String investorId;
	private String accountId;
	private String userId;

	private String password;
	private String authCode;

	private String ipAddr;
	private String macAddr;

	private String tradingDay;
	private String currencyId;

	@Override
	public String toString() {
		return JsonWrapper.toJson(this);
	}

}
