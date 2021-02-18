package io.horizon.ftdc.adaptor;

import io.horizon.structure.adaptor.AdaptorParamKey;
import io.mercury.common.param.Params.ParamType;

/**
 * 用于读取FTDC配置信息
 * 
 * @author yellow013
 *
 */
public enum FtdcAdaptorParamKey implements AdaptorParamKey {
	/*
	 * 交易服务器地址
	 */
	TraderAddr("traderAddr", ParamType.STRING),
	/*
	 * 行情服务器地址
	 */
	MdAddr("mdAddr", ParamType.STRING),
	/*
	 * 应用ID
	 */
	AppId("appId", ParamType.STRING),
	/*
	 * 经纪商ID
	 */
	BrokerId("brokerId", ParamType.STRING),
	/*
	 * 投资者ID
	 */
	InvestorId("investorId", ParamType.STRING),
	/*
	 * 账号ID
	 */
	AccountId("accountId", ParamType.STRING),
	/*
	 * 用户ID
	 */
	UserId("userId", ParamType.STRING),
	/*
	 * 密码
	 */
	Password("password", ParamType.STRING),
	/*
	 * 认证码
	 */
	AuthCode("authCode", ParamType.STRING),
	/*
	 * 客户端IP地址
	 */
	IpAddr("ipAddr", ParamType.STRING),
	/*
	 * 客户端MAC地址
	 */
	MacAddr("macAddr", ParamType.STRING),
	/*
	 * 结算货币
	 */
	CurrencyId("currencyId", ParamType.STRING),

	;

	private final String paramName;
	private final ParamType type;

	private FtdcAdaptorParamKey(String paramName, ParamType type) {
		this.paramName = paramName;
		this.type = type;
	}

	@Override
	public int getId() {
		return ordinal();
	}

	@Override
	public String getParamName() {
		return paramName;
	}

	@Override
	public ParamType getType() {
		return type;
	}

	@Override
	public String getAdaptorName() {
		return "FtdcAdaptor";
	}

	public static void main(String[] args) {
		for (FtdcAdaptorParamKey key : FtdcAdaptorParamKey.values()) {
			System.out.println(key + " -> " + key.ordinal());
		}
	}

}
