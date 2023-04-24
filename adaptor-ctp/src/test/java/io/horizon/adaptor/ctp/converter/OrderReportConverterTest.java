package io.horizon.adaptor.ctp.converter;

import io.horizon.trader.transport.outbound.TdxOrderReport;
import org.junit.Test;

import io.horizon.trader.order.enums.OrdStatus;
import io.horizon.trader.order.enums.TrdAction;
import io.horizon.trader.order.enums.TrdDirection;
import io.mercury.common.datetime.EpochTime;
import io.mercury.common.log4j2.Log4j2Configurator;
import io.mercury.common.log4j2.Log4j2Configurator.LogLevel;
import io.mercury.serialization.json.JsonWrapper;

public class OrderReportConverterTest {

    static {
        Log4j2Configurator.setLogLevel(LogLevel.INFO);
    }

    @Test
    public void test() {
        var builder = TdxOrderReport.newBuilder();
        // 微秒时间戳
        builder.setEpochMicros(EpochTime.getEpochMicros());
        // OrdSysId
        builder.setOrdSysId(0L);
        // 交易日
        // 投资者ID
        // 报单引用
        builder.setOrderRef("2221");
        builder.setExchangeCode("").setInstrumentCode("");
        builder.setBrokerOrdSysId("");
        // 报单编号
        // 报单状态
        builder.setStatus(OrdStatus.NewRejected.getTdxValue());
        builder.setDirection(TrdDirection.Long.getTdxValue());
        builder.setAction(TrdAction.Open.getTdxValue());
        TdxOrderReport report = builder.build();
        System.out.println(JsonWrapper.toJson(report));

    }

}
