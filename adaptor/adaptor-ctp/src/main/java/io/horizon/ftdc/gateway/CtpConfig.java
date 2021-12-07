package io.horizon.ftdc.gateway;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public final class CtpConfig {

	@Value("")
	private String traderAddr;
	@Value("")
	private String mdAddr;

	@Value("")
	private String appId;
	@Value("")
	private String brokerId;
	@Value("")
	private String investorId;
	@Value("")
	private String accountId;
	@Value("")
	private String userId;

	@Value("")
	private String password;
	@Value("")
	private String authCode;

	@Value("")
	private String ipAddr;
	@Value("")
	private String macAddr;

	@Value("")
	private String tradingDay;
	@Value("")
	private String currencyId;

}
