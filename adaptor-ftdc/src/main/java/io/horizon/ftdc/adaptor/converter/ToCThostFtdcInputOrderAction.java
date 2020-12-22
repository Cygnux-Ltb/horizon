package io.horizon.ftdc.adaptor.converter;

import java.util.function.Function;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcInputOrderActionField;
import io.horizon.definition.market.instrument.Instrument;
import io.horizon.definition.order.Order;
import io.horizon.ftdc.adaptor.consts.FtdcActionFlag;
import io.mercury.common.log.CommonLoggerFactory;

/**
 * 
 * @author yellow013
 *
 */
public final class ToCThostFtdcInputOrderAction implements Function<Order, CThostFtdcInputOrderActionField> {

	private static final Logger log = CommonLoggerFactory.getLogger(FromFtdcTrade.class);

	@Override
	public CThostFtdcInputOrderActionField apply(Order order) {
		Instrument instrument = order.instrument();
		CThostFtdcInputOrderActionField inputOrderActionField = new CThostFtdcInputOrderActionField();

		// 设置订单操作类型
		inputOrderActionField.setActionFlag(FtdcActionFlag.Delete);

		// 设置交易所ID
		inputOrderActionField.setExchangeID(instrument.exchangeCode());

		// 设置交易标的
		inputOrderActionField.setInstrumentID(instrument.instrumentCode());
		/**
		 * 
		 */
		inputOrderActionField.setLimitPrice(instrument.getPriceMultiplier().toDouble(order.price().offerPrice()));
		/**
		 * 
		 */
		inputOrderActionField.setVolumeChange(order.qty().leavesQty());

		// TODO 补充完整信息

		/**
		 * 返回FTDC撤单对象
		 */
		return inputOrderActionField;
	}

}
