package io.horizon.ftdc.adaptor.converter;

import java.util.function.Function;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcInputOrderField;
import io.horizon.definition.market.instrument.Instrument;
import io.horizon.definition.market.instrument.PriceMultiplier;
import io.horizon.definition.order.Order;
import io.horizon.definition.order.actual.ChildOrder;
import io.horizon.ftdc.adaptor.consts.FtdcContingentCondition;
import io.horizon.ftdc.adaptor.consts.FtdcDirection;
import io.horizon.ftdc.adaptor.consts.FtdcForceCloseReason;
import io.horizon.ftdc.adaptor.consts.FtdcHedgeFlag;
import io.horizon.ftdc.adaptor.consts.FtdcOffsetFlag;
import io.horizon.ftdc.adaptor.consts.FtdcOrderPriceType;
import io.horizon.ftdc.adaptor.consts.FtdcTimeCondition;
import io.horizon.ftdc.adaptor.consts.FtdcVolumeCondition;
import io.mercury.common.log.CommonLoggerFactory;

/**
 * 
 * @author yellow013
 *
 */
public final class ToFtdcInputOrderFunc implements Function<Order, CThostFtdcInputOrderField> {

	private static final Logger log = CommonLoggerFactory.getLogger(FromFtdcTradeFunc.class);

	@Override
	public CThostFtdcInputOrderField apply(Order order) {
		ChildOrder childOrder = (ChildOrder) order;
		Instrument instrument = order.instrument();
		CThostFtdcInputOrderField inputOrderField = new CThostFtdcInputOrderField();
		/**
		 * 设置交易所ID
		 */
		inputOrderField.setExchangeID(instrument.exchange().code());
		/**
		 * 设置交易标的
		 */
		inputOrderField.setInstrumentID(instrument.code());
		/**
		 * 设置报单价格
		 */
		inputOrderField.setOrderPriceType(FtdcOrderPriceType.LimitPrice);
		/**
		 * 设置开平标识
		 */
		switch (childOrder.action()) {
		case Open:
			inputOrderField.setCombOffsetFlag(FtdcOffsetFlag.OpenString);
			break;
		case Close:
			inputOrderField.setCombOffsetFlag(FtdcOffsetFlag.CloseString);
			break;
		case CloseToday:
			inputOrderField.setCombOffsetFlag(FtdcOffsetFlag.CloseTodayString);
			break;
		case CloseYesterday:
			inputOrderField.setCombOffsetFlag(FtdcOffsetFlag.CloseYesterdayString);
			break;
		default:
			throw new IllegalStateException(childOrder.action() + " is invalid");
		}
		/**
		 * 设置投机标识
		 */
		inputOrderField.setCombHedgeFlag(FtdcHedgeFlag.SpeculationString);
		/**
		 * 设置买卖方向
		 */
		switch (order.direction()) {
		case Long:
			inputOrderField.setDirection(FtdcDirection.Buy);
			break;
		case Short:
			inputOrderField.setDirection(FtdcDirection.Sell);
			break;
		case Invalid:
			throw new IllegalStateException(order.direction() + " is invalid");
		}
		/**
		 * 设置价格
		 */
		PriceMultiplier multiplier = instrument.getPriceMultiplier();
		inputOrderField.setLimitPrice(multiplier.toDouble(order.price().offerPrice()));
		/**
		 * 设置数量
		 */
		inputOrderField.setVolumeTotalOriginal(order.qty().offerQty());
		/**
		 * 设置有效期类型
		 */
		inputOrderField.setTimeCondition(FtdcTimeCondition.GFD);
		/**
		 * 设置成交量类型
		 */
		inputOrderField.setVolumeCondition(FtdcVolumeCondition.AV);
		/**
		 * 设置最小成交数量, 默认为1
		 */
		inputOrderField.setMinVolume(1);
		/**
		 * 设置触发条件
		 */
		inputOrderField.setContingentCondition(FtdcContingentCondition.Immediately);
		/**
		 * 设置止损价格
		 */
		inputOrderField.setStopPrice(0.0D);
		/**
		 * 设置强平原因: 此处固定为非强平
		 */
		inputOrderField.setForceCloseReason(FtdcForceCloseReason.NotForceClose);
		/**
		 * 设置自动挂起标识
		 */
		inputOrderField.setIsAutoSuspend(0);
		/**
		 * 返回FTDC新订单对象
		 */
		return inputOrderField;
	}

}
