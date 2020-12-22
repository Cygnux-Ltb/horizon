package io.horizon.ftdc.adaptor.converter;

import java.util.function.Function;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcInputOrderField;
import io.horizon.definition.market.instrument.Instrument;
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
public final class ToCThostFtdcInputOrder implements Function<Order, CThostFtdcInputOrderField> {

	private static final Logger log = CommonLoggerFactory.getLogger(FromFtdcTrade.class);

	@Override
	public CThostFtdcInputOrderField apply(Order order) {
		ChildOrder childOrder = (ChildOrder) order;
		Instrument instrument = order.instrument();
		CThostFtdcInputOrderField inputOrderField = new CThostFtdcInputOrderField();

		// 设置交易所ID
		inputOrderField.setExchangeID(instrument.exchangeCode());
		log.info("Set CThostFtdcInputOrder -> ExchangeID == {}", instrument.exchangeCode());

		// 设置交易标的
		inputOrderField.setInstrumentID(instrument.instrumentCode());
		log.info("Set CThostFtdcInputOrder -> InstrumentID == {}", instrument.instrumentCode());

		// 设置报单价格
		inputOrderField.setOrderPriceType(FtdcOrderPriceType.LimitPrice);
		log.info("Set CThostFtdcInputOrder -> OrderPriceType == LimitPrice");

		// 设置开平标识
		switch (((ChildOrder) order).action()) {
		case Open:
			// 设置为开仓
			inputOrderField.setCombOffsetFlag(FtdcOffsetFlag.OpenString);
			log.info("Set CThostFtdcInputOrder -> CombOffsetFlag == Open");
			break;
		case Close:
			// 设置为平仓
			inputOrderField.setCombOffsetFlag(FtdcOffsetFlag.CloseString);
			log.info("Set CThostFtdcInputOrder -> CombOffsetFlag == Close");
			break;
		case CloseToday:
			// 设置为平今仓
			inputOrderField.setCombOffsetFlag(FtdcOffsetFlag.CloseTodayString);
			log.info("Set CThostFtdcInputOrder -> CombOffsetFlag == CloseToday");
			break;
		case CloseYesterday:
			// 设置为平昨仓
			inputOrderField.setCombOffsetFlag(FtdcOffsetFlag.CloseYesterdayString);
			log.info("Set CThostFtdcInputOrder -> CombOffsetFlag == CloseYesterday");
			break;
		case Invalid:
			// 无效订单动作
			log.error("order.direction() == Invalid");
			throw new IllegalStateException(childOrder.action() + " is invalid");
		}

		// 设置投机标识
		inputOrderField.setCombHedgeFlag(FtdcHedgeFlag.SpeculationString);
		log.info("Set CThostFtdcInputOrder -> CombHedgeFlag == Speculation");

		// 设置买卖方向
		switch (order.direction()) {
		case Long:
			// 设置为买单
			inputOrderField.setDirection(FtdcDirection.Buy);
			log.info("Set CThostFtdcInputOrder -> Direction == Buy");
			break;
		case Short:
			// 设置为卖单
			inputOrderField.setDirection(FtdcDirection.Sell);
			log.info("Set CThostFtdcInputOrder -> Direction == Sell");
			break;
		case Invalid:
			// 无效订单方向
			log.error("order.direction() == Invalid");
			throw new IllegalStateException(order.direction() + " is invalid");
		}

		// 设置价格
		double limitPrice = instrument.getPriceMultiplier().toDouble(order.price().offerPrice());
		inputOrderField.setLimitPrice(limitPrice);
		log.info("Set CThostFtdcInputOrder -> LimitPrice == {}", limitPrice);

		// 设置数量
		int volumeTotalOriginal = order.qty().offerQty();
		inputOrderField.setVolumeTotalOriginal(volumeTotalOriginal);
		log.info("Set CThostFtdcInputOrder -> VolumeTotalOriginal == {}", volumeTotalOriginal);

		// 设置有效期类型
		inputOrderField.setTimeCondition(FtdcTimeCondition.GFD);
		log.info("Set CThostFtdcInputOrder -> TimeCondition == GFD");

		// 设置成交量类型
		inputOrderField.setVolumeCondition(FtdcVolumeCondition.AV);
		log.info("Set CThostFtdcInputOrder -> VolumeCondition == AV");

		// 设置最小成交数量, 默认为1
		inputOrderField.setMinVolume(1);
		log.info("Set CThostFtdcInputOrder -> MinVolume == 1");

		// 设置触发条件
		inputOrderField.setContingentCondition(FtdcContingentCondition.Immediately);
		log.info("Set CThostFtdcInputOrder -> ContingentCondition == Immediately");

		// 设置止损价格
		inputOrderField.setStopPrice(0.0D);
		log.info("Set CThostFtdcInputOrder -> StopPrice == 0.0D");

		// 设置强平原因: 此处固定为非强平
		inputOrderField.setForceCloseReason(FtdcForceCloseReason.NotForceClose);
		log.info("Set CThostFtdcInputOrder -> ForceCloseReason == NotForceClose");

		// 设置自动挂起标识
		inputOrderField.setIsAutoSuspend(0);
		log.info("Set CThostFtdcInputOrder -> IsAutoSuspend == 0");

		// 返回FTDC新订单对象
		log.info("Set CThostFtdcInputOrder finished");
		return inputOrderField;
	}

}
