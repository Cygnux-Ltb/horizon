package io.horizon.ftdc.gateway;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public final class FtdcConfig {

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

}
