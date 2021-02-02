package io.horizon.ftdc.adaptor.converter;

import java.util.function.Function;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcInputOrderField;
import io.horizon.ftdc.adaptor.consts.FtdcContingentCondition;
import io.horizon.ftdc.adaptor.consts.FtdcDirection;
import io.horizon.ftdc.adaptor.consts.FtdcForceCloseReason;
import io.horizon.ftdc.adaptor.consts.FtdcHedgeFlag;
import io.horizon.ftdc.adaptor.consts.FtdcOffsetFlag;
import io.horizon.ftdc.adaptor.consts.FtdcOrderPriceType;
import io.horizon.ftdc.adaptor.consts.FtdcTimeCondition;
import io.horizon.ftdc.adaptor.consts.FtdcVolumeCondition;
import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.order.Order;
import io.horizon.structure.order.actual.ChildOrder;
import io.mercury.common.log.CommonLoggerFactory;

/**
 * 
 * @author yellow013
 * 
 *         <pre>
 struct CThostFtdcInputOrderField
{
    ///经纪公司代码
    TThostFtdcBrokerIDType BrokerID;
    ///投资者代码
    TThostFtdcInvestorIDType InvestorID;
    ///合约代码
    TThostFtdcInstrumentIDType InstrumentID;
    ///报单引用
    TThostFtdcOrderRefType OrderRef;
    ///用户代码
    TThostFtdcUserIDType UserID;
    ///报单价格条件
    TThostFtdcOrderPriceTypeType OrderPriceType;
    ///买卖方向
    TThostFtdcDirectionType Direction;
    ///组合开平标志
    TThostFtdcCombOffsetFlagType CombOffsetFlag;
    ///组合投机套保标志
    TThostFtdcCombHedgeFlagType CombHedgeFlag;
    ///价格
    TThostFtdcPriceType LimitPrice;
    ///数量
    TThostFtdcVolumeType VolumeTotalOriginal;
    ///有效期类型
    TThostFtdcTimeConditionType TimeCondition;
    ///GTD日期
    TThostFtdcDateType GTDDate;
    ///成交量类型
    TThostFtdcVolumeConditionType VolumeCondition;
    ///最小成交量
    TThostFtdcVolumeType MinVolume;
    ///触发条件
    TThostFtdcContingentConditionType ContingentCondition;
    ///止损价
    TThostFtdcPriceType StopPrice;
    ///强平原因
    TThostFtdcForceCloseReasonType ForceCloseReason;
    ///自动挂起标志
    TThostFtdcBoolType IsAutoSuspend;
    ///业务单元
    TThostFtdcBusinessUnitType BusinessUnit;
    ///请求编号
    TThostFtdcRequestIDType RequestID;
    ///用户强评标志
    TThostFtdcBoolType UserForceClose;
    ///互换单标志
    TThostFtdcBoolType IsSwapOrder;
    ///交易所代码
    TThostFtdcExchangeIDType ExchangeID;
    ///投资单元代码
    TThostFtdcInvestUnitIDType InvestUnitID;
    ///资金账号
    TThostFtdcAccountIDType AccountID;
    ///币种代码
    TThostFtdcCurrencyIDType CurrencyID;
    ///交易编码
    TThostFtdcClientIDType ClientID;
    ///IP地址
    TThostFtdcIPAddressType IPAddress;
    ///MAC地址
    TThostFtdcMacAddressType MacAddress;
};
 *         </pre>
 */
public final class ToCThostFtdcInputOrder implements Function<Order, CThostFtdcInputOrderField> {

	private static final Logger log = CommonLoggerFactory.getLogger(FromFtdcTrade.class);

	@Override
	public CThostFtdcInputOrderField apply(Order order) {
		ChildOrder childOrder = (ChildOrder) order;
		Instrument instrument = order.getInstrument();
		CThostFtdcInputOrderField field = new CThostFtdcInputOrderField();

		// 设置交易所ID
		field.setExchangeID(instrument.exchangeCode());
		log.info("Set CThostFtdcInputOrderField -> ExchangeID == {}", instrument.exchangeCode());

		// 设置交易标的
		field.setInstrumentID(instrument.getInstrumentCode());
		log.info("Set CThostFtdcInputOrderField -> InstrumentID == {}", instrument.getInstrumentCode());

		// 设置报单价格
		field.setOrderPriceType(FtdcOrderPriceType.LimitPrice);
		log.info("Set CThostFtdcInputOrderField -> OrderPriceType == LimitPrice");

		// 设置开平标识
		switch (((ChildOrder) order).getAction()) {
		case Open:
			// 设置为开仓
			field.setCombOffsetFlag(FtdcOffsetFlag.OpenString);
			log.info("Set CThostFtdcInputOrderField -> CombOffsetFlag == Open");
			break;
		case Close:
			// 设置为平仓
			field.setCombOffsetFlag(FtdcOffsetFlag.CloseString);
			log.info("Set CThostFtdcInputOrderField -> CombOffsetFlag == Close");
			break;
		case CloseToday:
			// 设置为平今仓
			field.setCombOffsetFlag(FtdcOffsetFlag.CloseTodayString);
			log.info("Set CThostFtdcInputOrderField -> CombOffsetFlag == CloseToday");
			break;
		case CloseYesterday:
			// 设置为平昨仓
			field.setCombOffsetFlag(FtdcOffsetFlag.CloseYesterdayString);
			log.info("Set CThostFtdcInputOrderField -> CombOffsetFlag == CloseYesterday");
			break;
		case Invalid:
			// 无效订单动作
			log.error("order.direction() == Invalid");
			throw new IllegalStateException(childOrder.getAction() + " is invalid");
		}

		// 设置投机标识
		field.setCombHedgeFlag(FtdcHedgeFlag.SpeculationString);
		log.info("Set CThostFtdcInputOrderField -> CombHedgeFlag == Speculation");

		// 设置买卖方向
		switch (order.getDirection()) {
		case Long:
			// 设置为买单
			field.setDirection(FtdcDirection.Buy);
			log.info("Set CThostFtdcInputOrderField -> Direction == Buy");
			break;
		case Short:
			// 设置为卖单
			field.setDirection(FtdcDirection.Sell);
			log.info("Set CThostFtdcInputOrderField -> Direction == Sell");
			break;
		case Invalid:
			// 无效订单方向
			log.error("order.direction() == Invalid");
			throw new IllegalStateException(order.getDirection() + " is invalid");
		}

		// 设置价格
		double limitPrice = instrument.getPriceMultiplier().toDouble(order.getPrice().getOfferPrice());
		field.setLimitPrice(limitPrice);
		log.info("Set CThostFtdcInputOrderField -> LimitPrice == {}", limitPrice);

		// 设置数量
		int volumeTotalOriginal = order.getQty().getOfferQty();
		field.setVolumeTotalOriginal(volumeTotalOriginal);
		log.info("Set CThostFtdcInputOrderField -> VolumeTotalOriginal == {}", volumeTotalOriginal);

		// 设置有效期类型
		field.setTimeCondition(FtdcTimeCondition.GFD);
		log.info("Set CThostFtdcInputOrderField -> TimeCondition == GFD");

		// 设置成交量类型
		field.setVolumeCondition(FtdcVolumeCondition.AV);
		log.info("Set CThostFtdcInputOrderField -> VolumeCondition == AV");

		// 设置最小成交数量, 默认为1
		field.setMinVolume(1);
		log.info("Set CThostFtdcInputOrderField -> MinVolume == 1");

		// 设置触发条件
		field.setContingentCondition(FtdcContingentCondition.Immediately);
		log.info("Set CThostFtdcInputOrderField -> ContingentCondition == Immediately");

		// 设置止损价格
		field.setStopPrice(0.0D);
		log.info("Set CThostFtdcInputOrderField -> StopPrice == 0.0D");

		// 设置强平原因: 此处固定为非强平
		field.setForceCloseReason(FtdcForceCloseReason.NotForceClose);
		log.info("Set CThostFtdcInputOrderField -> ForceCloseReason == NotForceClose");

		// 设置自动挂起标识
		field.setIsAutoSuspend(0);
		log.info("Set CThostFtdcInputOrderField -> IsAutoSuspend == 0");

		// 返回FTDC新订单对象
		log.info("Set CThostFtdcInputOrderField finished");
		return field;
	}

}
