package io.horizon.ctp.adaptor;

import org.springframework.beans.factory.annotation.Value;

import io.mercury.common.param.Params;
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

	public static CtpConfig ofParams(Params<CtpAdaptorParamKey> params) {
		return new CtpConfig()
				// 交易服务器地址
				.setTraderAddr(params.getString(CtpAdaptorParamKey.TraderAddr))
				// 行情服务器地址
				.setMdAddr(params.getString(CtpAdaptorParamKey.MdAddr))
				// 应用ID
				.setAppId(params.getString(CtpAdaptorParamKey.AppId))
				// 经纪商ID
				.setBrokerId(params.getString(CtpAdaptorParamKey.BrokerId))
				// 投资者ID
				.setInvestorId(params.getString(CtpAdaptorParamKey.InvestorId))
				// 账号ID
				.setAccountId(params.getString(CtpAdaptorParamKey.AccountId))
				// 用户ID
				.setUserId(params.getString(CtpAdaptorParamKey.UserId))
				// 密码
				.setPassword(params.getString(CtpAdaptorParamKey.Password))
				// 认证码
				.setAuthCode(params.getString(CtpAdaptorParamKey.AuthCode))
				// 客户端IP地址
				.setIpAddr(params.getString(CtpAdaptorParamKey.IpAddr))
				// 客户端MAC地址
				.setMacAddr(params.getString(CtpAdaptorParamKey.MacAddr))
				// 结算货币
				.setCurrencyId(params.getString(CtpAdaptorParamKey.CurrencyId));
	}

}
