package io.horizon.adaptor.ctp.converter;

import org.junit.Test;

import io.horizon.trader.order.enums.OrdStatus;
import io.horizon.trader.report.OrderReport;
import io.horizon.trader.report.OrderReport.Builder;
import io.mercury.common.datetime.EpochUtil;
import io.mercury.common.log.Log4j2Configurator;
import io.mercury.common.log.Log4j2Configurator.LogLevel;
import io.mercury.serialization.json.JsonWrapper;

public class OrderReportConverterTest {

	static {
		Log4j2Configurator.setLogLevel(LogLevel.INFO);
	}

	@Test
	public void test() {
		Builder builder = OrderReport.newBuilder();
		// 微秒时间戳
		builder.setEpochMicros(EpochUtil.getEpochMicros());
		// OrdSysId
		builder.setOrdSysId(0L);
		// 交易日
		// 投资者ID
		// 报单引用
		builder.setOrderRef("2221");
		builder.setExchangeCode("").setInstrumentCode("");
		builder.setBrokerSysId("");
		// 报单编号
		// 报单状态
		builder.setStatus(OrdStatus.NewRejected.getCode());
		builder.setDirection(0);
		builder.setAction(0);
		OrderReport report = builder.build();
		System.out.println(JsonWrapper.toJson(report));

	}

}
