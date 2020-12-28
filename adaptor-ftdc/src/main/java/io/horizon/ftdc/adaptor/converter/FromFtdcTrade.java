package io.horizon.ftdc.adaptor.converter;

import static io.mercury.common.util.StringUtil.removeNonDigits;

import java.util.function.Function;

import org.slf4j.Logger;

import io.horizon.ftdc.adaptor.FtdcConstMapper;
import io.horizon.ftdc.adaptor.OrderRefKeeper;
import io.horizon.ftdc.gateway.bean.FtdcTrade;
import io.horizon.structure.market.instrument.Instrument;
import io.horizon.structure.market.instrument.InstrumentManager;
import io.horizon.structure.market.instrument.PriceMultiplier;
import io.horizon.structure.order.OrdReport;
import io.horizon.structure.order.enums.OrdStatus;
import io.horizon.structure.order.enums.TrdAction;
import io.horizon.structure.order.enums.TrdDirection;
import io.mercury.common.log.CommonLoggerFactory;

public final class FromFtdcTrade implements Function<FtdcTrade, OrdReport> {

	private static final Logger log = CommonLoggerFactory.getLogger(FromFtdcTrade.class);

	@Override
	public OrdReport apply(FtdcTrade ftdcTrade) {
		String orderRef = ftdcTrade.getOrderRef();
		long ordId = OrderRefKeeper.getOrdId(orderRef);
		OrdReport report = new OrdReport(ordId);

		// 投资者ID
		report.setInvestorId(ftdcTrade.getInvestorID());

		// 报单引用
		report.setOrderRef(orderRef);

		// 时间戳
		report.setEpochMillis(System.currentTimeMillis());

		// 报单编号
		report.setBrokerUniqueId(ftdcTrade.getOrderSysID());

		// 合约代码
		Instrument instrument = InstrumentManager.getInstrument(ftdcTrade.getInstrumentID());
		report.setInstrument(instrument);

		// 报单状态
		report.setOrdStatus(OrdStatus.Unprovided);

		// 买卖方向
		TrdDirection direction = FtdcConstMapper.fromDirection(ftdcTrade.getDirection());
		report.setDirection(direction);

		// 组合开平标志
		TrdAction action = FtdcConstMapper.fromOffsetFlag(ftdcTrade.getOffsetFlag());
		report.setAction(action);

		// 完成数量
		report.setFilledQty(ftdcTrade.getVolume());

		// 成交价格
		PriceMultiplier multiplier = instrument.getPriceMultiplier();
		report.setTradePrice(multiplier.toLong(ftdcTrade.getPrice()));
		
		// 最后修改时间
		report.setLastUpdateTime(removeNonDigits(ftdcTrade.getTradeDate()) + removeNonDigits(ftdcTrade.getTradeTime()));
		
		log.info("FtdcTrade conversion function return OrdReport -> {}", report);
		return report;
	}

}
