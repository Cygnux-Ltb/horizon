package io.horizon.ctp.adaptor;

import static io.horizon.market.instrument.ChinaFutures.ChinaFuturesInstrument.parseTradingDay;
import static io.mercury.common.datetime.pattern.DatePattern.YYYYMMDD;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

import com.typesafe.config.Config;

import io.mercury.common.config.ConfigDelegate;
import io.mercury.common.net.NetworkProperties;
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

	public static CtpConfig with(Config config) {

		var delegate = new ConfigDelegate<CtpAdaptorParamKey>(config);
		return new CtpConfig()
				// 交易服务器地址
				.setTraderAddr(delegate.getStringOrThrows(CtpAdaptorParamKey.TraderAddr))
				// 行情服务器地址
				.setMdAddr(delegate.getStringOrThrows(CtpAdaptorParamKey.MdAddr))
				// 应用ID
				.setAppId(delegate.getStringOrThrows(CtpAdaptorParamKey.AppId))
				// 经纪商ID
				.setBrokerId(delegate.getStringOrThrows(CtpAdaptorParamKey.BrokerId))
				// 投资者ID
				.setInvestorId(delegate.getStringOrThrows(CtpAdaptorParamKey.InvestorId))
				// 账号ID
				.setAccountId(delegate.getStringOrThrows(CtpAdaptorParamKey.AccountId))
				// 用户ID
				.setUserId(delegate.getStringOrThrows(CtpAdaptorParamKey.UserId))
				// 密码
				.setPassword(delegate.getStringOrThrows(CtpAdaptorParamKey.Password))
				// 认证码
				.setAuthCode(delegate.getStringOrThrows(CtpAdaptorParamKey.AuthCode))
				// 客户端IP地址
				.setIpAddr(delegate.getString(CtpAdaptorParamKey.IpAddr, "127.0.0.1"))
				// 客户端MAC地址
				.setMacAddr(delegate.getString(CtpAdaptorParamKey.MacAddr, NetworkProperties.getLocalMacAddress()))
				// 结算货币
				.setCurrencyId(delegate.getString(CtpAdaptorParamKey.CurrencyId, "CNY"))
				// 交易日
				.setTradingDay(delegate.getString(CtpAdaptorParamKey.TradingDay,
						YYYYMMDD.format(parseTradingDay(LocalDateTime.now()))));
	}

	public static CtpConfig with(Params<CtpAdaptorParamKey> params) {
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
				.setCurrencyId(params.getString(CtpAdaptorParamKey.CurrencyId))
				// 交易日
				.setTradingDay(params.getString(CtpAdaptorParamKey.TradingDay));

	}

}
