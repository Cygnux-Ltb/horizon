package io.horizon.trader.adaptor;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;

/**
 * 
 * @author yellow013
 */
public enum AdaptorStatus {

	// 无效
	Invalid(Code.INVALID),
	// 不可用
	Unavailable(Code.UNAVAILABLE),
	// 行情启用
	MdEnable(Code.MD_ENABLE),
	// 交易启用
	TraderEnable(Code.TRADER_ENABLE),
	// 行情禁用
	MdDisable(Code.MD_DISABLE),
	// 交易禁用
	TraderDisable(Code.TRADER_DISABLE),

	;

	private final char code;

	private static final Logger log = CommonLoggerFactory.getLogger(AdaptorStatus.class);

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
		case Code.UNAVAILABLE:
			return Unavailable;
		// 行情启用
		case Code.MD_ENABLE:
			return MdEnable;
		// 交易启用
		case Code.TRADER_ENABLE:
			return TraderEnable;
		// 行情禁用
		case Code.MD_DISABLE:
			return MdDisable;
		// 交易禁用
		case Code.TRADER_DISABLE:
			return TraderDisable;
		// 没有匹配项
		default:
			log.error("AdaptorStatus valueOf error, return AdaptorStatus -> [Invalid], input code==[{}]", code);
			return Invalid;
		}
	}

	public interface Code {
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
