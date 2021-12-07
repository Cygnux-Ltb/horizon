package io.horizon.ctp.adaptor;

import org.springframework.beans.factory.annotation.Value;

import io.mercury.serialization.json.JsonWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public final class CtpConfig {

	@Value("traderAddr")
	private String traderAddr;
	@Value("mdAddr")
	private String mdAddr;

	@Value("appId")
	private String appId;
	@Value("brokerId")
	private String brokerId;
	@Value("investorId")
	private String investorId;
	@Value("accountId")
	private String accountId;
	@Value("userId")
	private String userId;

	@Value("password")
	private String password;
	@Value("authCode")
	private String authCode;

	@Value("ipAddr")
	private String ipAddr;
	@Value("macAddr")
	private String macAddr;

	@Value("tradingDay")
	private String tradingDay;
	@Value("currencyId")
	private String currencyId;

	@Override
	public String toString() {
		return JsonWrapper.toJson(this);
	}

}
