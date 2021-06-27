package io.horizon.ftdc.adaptor.converter;

import static io.mercury.common.util.StringUtil.removeNonDigits;

import java.util.function.Function;

import org.slf4j.Logger;

import io.horizon.ftdc.adaptor.FtdcConstMapper;
import io.horizon.ftdc.adaptor.OrderRefKeeper;
import io.horizon.ftdc.gateway.msg.rsp.FtdcOrder;
import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.InstrumentKeeper;
import io.horizon.market.instrument.PriceMultiplier;
import io.horizon.trader.order.OrderReport;
import io.horizon.trader.order.OrdEnum.OrdStatus;
import io.horizon.trader.order.OrdEnum.TrdAction;
import io.horizon.trader.order.OrdEnum.TrdDirection;
import io.mercury.common.datetime.EpochTime;
import io.mercury.common.log.CommonLoggerFactory;

public final class FromFtdcOrder implements Function<FtdcOrder, OrderReport> {

	private static final Logger log = CommonLoggerFactory.getLogger(FromFtdcOrder.class);

	@Override
	public OrderReport apply(FtdcOrder ftdcOrder) {
		String orderRef = ftdcOrder.getOrderRef();
		long ordSysId = OrderRefKeeper.getOrdSysId(orderRef);
		OrderReport report = new OrderReport(ordSysId);

		// 投资者ID
		report.setInvestorId(ftdcOrder.getInvestorID());

		// 报单引用
		report.setOrderRef(orderRef);

		// 时间戳
		report.setEpochMillis(EpochTime.millis());

		// 报单编号
		report.setBrokerUniqueId(ftdcOrder.getOrderSysID());

		// 合约代码
		Instrument instrument = InstrumentKeeper.getInstrument(ftdcOrder.getInstrumentID());
		report.setInstrument(instrument);

		// 报单状态
		OrdStatus ordStatus = FtdcConstMapper.fromOrderStatus(ftdcOrder.getOrderStatus());
		report.setOrdStatus(ordStatus);

		// 买卖方向
		TrdDirection direction = FtdcConstMapper.fromDirection(ftdcOrder.getDirection());
		report.setDirection(direction);

		// 组合开平标志
		TrdAction action = FtdcConstMapper.fromOffsetFlag(ftdcOrder.getCombOffsetFlag());
		report.setAction(action);

		// 委托数量
		report.setOfferQty(ftdcOrder.getVolumeTotalOriginal());

		// 完成数量
		report.setFilledQty(ftdcOrder.getVolumeTraded());

		// 委托价格
		PriceMultiplier multiplier = instrument.getPriceMultiplier();
		report.setOfferPrice(multiplier.toLong(ftdcOrder.getLimitPrice()));

		// 报单日期 + 委托时间
		report.setOfferTime(removeNonDigits(ftdcOrder.getInsertDate()) + removeNonDigits(ftdcOrder.getInsertTime()));

		// 最后修改时间
		report.setLastUpdateTime(ftdcOrder.getUpdateTime());

		log.info("FtdcTrade conversion function return OrderReport -> {}", report);
		return report;
	}

}
