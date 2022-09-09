package io.horizon.market.instrument.attr;

/**
 * 
 * 优先平仓类型枚举<br>
 * 
 * 上海期货交易所会调整平今仓和平昨仓的手续费<br>
 * 动态调整优先平仓类型可节约交易成本
 * 
 * @author yellow013
 *
 */
public enum PriorityCloseType {

	// 无
	NONE,

	// 优先平昨仓
	YESTERDAY,

	// 优先平今仓
	TODAY

}