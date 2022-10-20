package io.horizon.ctp.adaptor;

import io.mercury.common.config.ConfigOption;
import io.mercury.common.param.ParamKey;
import io.mercury.common.param.Params.ValueType;

import static io.mercury.common.param.Params.ValueType.STRING;

/**
 * 用于读取FTDC配置信息
 *
 * @author yellow013
 */
public enum CtpAdaptorParamKey implements ParamKey, ConfigOption {

    /**
     * 交易服务器地址
     */
    TraderAddr("traderAddr", STRING),

    /**
     * 行情服务器地址
     */
    MdAddr("mdAddr", STRING),

    /**
     * 应用ID
     */
    AppId("appId", STRING),

    /**
     * 经纪商ID
     */
    BrokerId("brokerId", STRING),

    /**
     * 投资者ID
     */
    InvestorId("investorId", STRING),

    /**
     * 账号ID
     */
    AccountId("accountId", STRING),

    /**
     * 用户ID
     */
    UserId("userId", STRING),

    /**
     * 密码
     */
    Password("password", STRING),

    /**
     * 认证码
     */
    AuthCode("authCode", STRING),

    /**
     * 客户端IP地址
     */
    IpAddr("ipAddr", STRING),

    /**
     * 客户端MAC地址
     */
    MacAddr("macAddr", STRING),

    /**
     * 结算货币
     */
    CurrencyId("currencyId", STRING),

    /**
     * 交易日
     */
    TradingDay("tradingDay", STRING),

    ;

    private final String paramName;

    private final ValueType valueType;

    private final String configName;

    CtpAdaptorParamKey(String paramName, ValueType valueType) {
        this.paramName = paramName;
        this.valueType = valueType;
        this.configName = "ctp." + paramName;
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
    public String getConfigName() {
        return configName;
    }

    public static void main(String[] args) {
        for (CtpAdaptorParamKey key : CtpAdaptorParamKey.values())
            System.out.println(key.getConfigName() + "=");
    }

}
