package io.horizon.ctp.adaptor;

import io.horizon.trader.adaptor.AdaptorParamKey;
import io.mercury.common.param.Params.ValueType;

/**
 * 用于读取FTDC配置信息
 * 
 * @author yellow013
 *
 */

public enum CtpAdaptorParamKey implements AdaptorParamKey {

	/**
	 * 交易服务器地址
	 */
	TraderAddr("traderAddr", ValueType.STRING),

	/**
	 * 行情服务器地址
	 */
	MdAddr("mdAddr", ValueType.STRING),

	/**
	 * 应用ID
	 */
	AppId("appId", ValueType.STRING),

	/**
	 * 经纪商ID
	 */
	BrokerId("brokerId", ValueType.STRING),

	/**
	 * 投资者ID
	 */
	InvestorId("investorId", ValueType.STRING),

	/**
	 * 账号ID
	 */
	AccountId("accountId", ValueType.STRING),

	/**
	 * 用户ID
	 */
	UserId("userId", ValueType.STRING),

	/**
	 * 密码
	 */
	Password("password", ValueType.STRING),

	/**
	 * 认证码
	 */
	AuthCode("authCode", ValueType.STRING),

	/**
	 * 客户端IP地址
	 */
	IpAddr("ipAddr", ValueType.STRING),

	/**
	 * 客户端MAC地址
	 */
	MacAddr("macAddr", ValueType.STRING),

	/**
	 * 结算货币
	 */
	CurrencyId("currencyId", ValueType.STRING),

	;

	private final String paramName;

	private final ValueType valueType;

	private CtpAdaptorParamKey(String paramName, ValueType valueType) {
		this.paramName = paramName;
		this.valueType = valueType;
	}

	@Override
	public String getParamName() {
		return paramName;
	}

	@Override
	public ValueType getValueType() {
		return valueType;
	}

	@Override
	public int getParamId() {
		return ordinal();
	}

	@Override
	public String getAdaptorType() {
		return "FtdcAdaptor";
	}

	public static void main(String[] args) {
		for (CtpAdaptorParamKey key : CtpAdaptorParamKey.values()) {
			System.out.println(key + " -> " + key.ordinal());
		}
	}

}
