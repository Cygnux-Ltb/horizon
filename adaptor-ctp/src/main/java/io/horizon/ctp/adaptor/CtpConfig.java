package io.horizon.ctp.adaptor;

import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.AccountId;
import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.AppId;
import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.AuthCode;
import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.BrokerId;
import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.CurrencyId;
import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.InvestorId;
import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.IpAddr;
import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.MacAddr;
import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.MdAddr;
import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.Password;
import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.TraderAddr;
import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.TradingDay;
import static io.horizon.ctp.adaptor.CtpAdaptorParamKey.UserId;
import static io.horizon.market.instrument.ChinaFutures.ChinaFuturesUtil.parseTradingDay;
import static io.mercury.common.datetime.pattern.DatePattern.YYYYMMDD;
import static io.mercury.common.net.NetworkProperties.getLocalMacAddress;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

import com.typesafe.config.Config;

import io.mercury.common.config.ConfigDelegate;
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
				.setTraderAddr(delegate.getStringOrThrows(TraderAddr))
				// 行情服务器地址
				.setMdAddr(delegate.getStringOrThrows(MdAddr))
				// 应用ID
				.setAppId(delegate.getStringOrThrows(AppId))
				// 经纪商ID
				.setBrokerId(delegate.getStringOrThrows(BrokerId))
				// 投资者ID
				.setInvestorId(delegate.getStringOrThrows(InvestorId))
				// 账号ID
				.setAccountId(delegate.getStringOrThrows(AccountId))
				// 用户ID
				.setUserId(delegate.getStringOrThrows(UserId))
				// 密码
				.setPassword(delegate.getStringOrThrows(Password))
				// 认证码
				.setAuthCode(delegate.getStringOrThrows(AuthCode))
				// 客户端IP地址
				.setIpAddr(delegate.getString(IpAddr, "127.0.0.1"))
				// 客户端MAC地址
				.setMacAddr(delegate.getString(MacAddr, getLocalMacAddress()))
				// 结算货币
				.setCurrencyId(delegate.getString(CurrencyId, "CNY"))
				// 交易日
				.setTradingDay(delegate.getString(TradingDay,
						YYYYMMDD.format(parseTradingDay(LocalDateTime.now()))));
	}

	public static CtpConfig with(Params<CtpAdaptorParamKey> params) {
		return new CtpConfig()
				// 交易服务器地址
				.setTraderAddr(params.getString(TraderAddr))
				// 行情服务器地址
				.setMdAddr(params.getString(MdAddr))
				// 应用ID
				.setAppId(params.getString(AppId))
				// 经纪商ID
				.setBrokerId(params.getString(BrokerId))
				// 投资者ID
				.setInvestorId(params.getString(InvestorId))
				// 账号ID
				.setAccountId(params.getString(AccountId))
				// 用户ID
				.setUserId(params.getString(UserId))
				// 密码
				.setPassword(params.getString(Password))
				// 认证码
				.setAuthCode(params.getString(AuthCode))
				// 客户端IP地址
				.setIpAddr(params.getString(IpAddr))
				// 客户端MAC地址
				.setMacAddr(params.getString(MacAddr))
				// 结算货币
				.setCurrencyId(params.getString(CurrencyId))
				// 交易日
				.setTradingDay(params.getString(TradingDay));
	}

}
