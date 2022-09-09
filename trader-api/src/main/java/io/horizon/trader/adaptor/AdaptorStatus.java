package io.horizon.trader.adaptor;

import io.mercury.common.log.Log4j2LoggerFactory;
import org.slf4j.Logger;

import static io.horizon.trader.adaptor.AdaptorStatus.AdaptorStatusCode.*;

/**
 * @author yellow013
 */
public enum AdaptorStatus {

    /**
     * 无效
     */
    Invalid(INVALID),

    /**
     * 不可用
     */
    Unavailable(UNAVAILABLE),

    /**
     * 行情启用
     */
    MdEnable(MD_ENABLE),

    /**
     * 交易启用
     */
    TraderEnable(TRADER_ENABLE),

    /**
     * 行情禁用
     */
    MdDisable(MD_DISABLE),

    /**
     * 交易禁用
     */
    TraderDisable(TRADER_DISABLE),

    ;

    private final char code;

    private static final Logger log = Log4j2LoggerFactory.getLogger(AdaptorStatus.class);

    AdaptorStatus(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    /**
     * @param code int
     * @return AdaptorStatus
     */
    public static AdaptorStatus valueOf(int code) {
        return switch (code) {
            // 不可用
            case UNAVAILABLE -> Unavailable;
            // 行情启用
            case MD_ENABLE -> MdEnable;
            // 交易启用
            case TRADER_ENABLE -> TraderEnable;
            // 行情禁用
            case MD_DISABLE -> MdDisable;
            // 交易禁用
            case TRADER_DISABLE -> TraderDisable;
            // 没有匹配项
            default ->
                // log.error("AdaptorStatus valueOf error, return AdaptorStatus -> [Invalid], input code==[{}]", code);
                    Invalid;
        };
    }

    public interface AdaptorStatusCode {
        // 无效
        char INVALID = 'I';
        // 不可用
        char UNAVAILABLE = 'U';
        // 行情启用
        char MD_ENABLE = 'M';
        // 交易启用
        char TRADER_ENABLE = 'T';
        // 行情禁用
        char MD_DISABLE = 'D';
        // 交易禁用
        char TRADER_DISABLE = 'R';
    }

}
