package io.horizon.trader.adaptor;

import org.slf4j.Logger;

import io.mercury.common.log.Log4j2LoggerFactory;

/**
 * 
 * @author yellow013
 */
public enum AdaptorStatus {

	/**
	 * 无效
	 */
	Invalid(AdaptorStatusCode.INVALID),
	/**
	 * 不可用
	 */
	Unavailable(AdaptorStatusCode.UNAVAILABLE),
	/**
	 * 行情启用
	 */
	MdEnable(AdaptorStatusCode.MD_ENABLE),
	/**
	 * 交易启用
	 */
	TraderEnable(AdaptorStatusCode.TRADER_ENABLE),
	/**
	 * 行情禁用
	 */
	MdDisable(AdaptorStatusCode.MD_DISABLE),
	/**
	 * 交易禁用
	 */
	TraderDisable(AdaptorStatusCode.TRADER_DISABLE),

	;

	private final char code;

	private static final Logger log = Log4j2LoggerFactory.getLogger(AdaptorStatus.class);

	private AdaptorStatus(char code) {
		this.code = code;
	}

	public char getCode() {
		return code;
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public static AdaptorStatus valueOf(int code) {
		switch (code) {
		// 不可用
		case AdaptorStatusCode.UNAVAILABLE:
			return Unavailable;
		// 行情启用
		case AdaptorStatusCode.MD_ENABLE:
			return MdEnable;
		// 交易启用
		case AdaptorStatusCode.TRADER_ENABLE:
			return TraderEnable;
		// 行情禁用
		case AdaptorStatusCode.MD_DISABLE:
			return MdDisable;
		// 交易禁用
		case AdaptorStatusCode.TRADER_DISABLE:
			return TraderDisable;
		// 没有匹配项
		default:
			log.error("AdaptorStatus valueOf error, return AdaptorStatus -> [Invalid], input code==[{}]", code);
			return Invalid;
		}
	}

	public static interface AdaptorStatusCode {
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
