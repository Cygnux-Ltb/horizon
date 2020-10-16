package io.gemini.ftdc.adaptor.converter;

import static io.mercury.common.util.StringUtil.delNonNumeric;

import java.util.function.Function;

import io.gemini.definition.market.instrument.Instrument;
import io.gemini.definition.market.instrument.InstrumentManager;
import io.gemini.definition.market.instrument.PriceMultiplier;
import io.gemini.definition.order.enums.OrdStatus;
import io.gemini.definition.order.enums.TrdAction;
import io.gemini.definition.order.enums.TrdDirection;
import io.gemini.definition.order.structure.OrdReport;
import io.gemini.ftdc.adaptor.FtdcConstMapper;
import io.gemini.ftdc.adaptor.OrderRefKeeper;
import io.gemini.ftdc.gateway.bean.FtdcOrder;
import io.mercury.common.datetime.EpochTime;

public final class FromFtdcOrder implements Function<FtdcOrder, OrdReport> {

	@Override
	public OrdReport apply(FtdcOrder ftdcOrder) {
		String orderRef = ftdcOrder.getOrderRef();
		long uniqueId = OrderRefKeeper.getUniqueId(orderRef);
		OrdReport report = new OrdReport(uniqueId);
		/**
		 * 投资者ID
		 */
		report.setInvestorId(ftdcOrder.getInvestorID());
		/**
		 * 报单引用
		 */
		report.setOrderRef(orderRef);
		/**
		 * 时间戳
		 */
		report.setEpochMillis(EpochTime.millis());
		/**
		 * 报单编号
		 */
		report.setBrokerUniqueId(ftdcOrder.getOrderSysID());
		/**
		 * 合约代码
		 */
		Instrument instrument = InstrumentManager.getInstrument(ftdcOrder.getInstrumentID());
		report.setInstrument(instrument);
		/**
		 * 报单状态
		 */
		OrdStatus ordStatus = FtdcConstMapper.fromOrderStatus(ftdcOrder.getOrderStatus());
		report.setOrdStatus(ordStatus);
		/**
		 * 买卖方向
		 */
		TrdDirection direction = FtdcConstMapper.fromDirection(ftdcOrder.getDirection());
		report.setDirection(direction);
		/**
		 * 组合开平标志
		 */
		TrdAction action = FtdcConstMapper.fromOffsetFlag(ftdcOrder.getCombOffsetFlag());
		report.setAction(action);
		/**
		 * 委托数量
		 */
		report.setOfferQty(ftdcOrder.getVolumeTotalOriginal());
		/**
		 * 完成数量
		 */
		report.setFilledQty(ftdcOrder.getVolumeTraded());
		/**
		 * 委托价格
		 */
		PriceMultiplier multiplier = instrument.symbol().getPriceMultiplier();
		report.setOfferPrice(multiplier.toLong(ftdcOrder.getLimitPrice()));
		/**
		 * 报单日期 + 委托时间
		 */
		report.setOfferTime(delNonNumeric(ftdcOrder.getInsertDate()) + delNonNumeric(ftdcOrder.getInsertTime()));
		/**
		 * 最后修改时间
		 */
		report.setLastUpdateTime(ftdcOrder.getUpdateTime());
		return report;
	}

}
