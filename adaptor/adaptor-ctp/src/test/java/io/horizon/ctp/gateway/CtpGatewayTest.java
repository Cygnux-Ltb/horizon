package io.horizon.ctp.gateway;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;

import io.horizon.ctp.adaptor.CtpConfig;
import io.horizon.ctp.gateway.rsp.FtdcDepthMarketData;
import io.horizon.ctp.gateway.rsp.FtdcOrder;
import io.horizon.ctp.gateway.rsp.FtdcTrade;
import io.mercury.common.concurrent.queue.Queue;
import io.mercury.common.concurrent.queue.jct.JctSingleConsumerQueue;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.common.thread.Threads;

public class CtpGatewayTest {

	private static final Logger log = Log4j2LoggerFactory.getLogger(CtpGatewayTest.class);

	// 标准CTP
//	private String TradeAddress = "tcp://180.168.146.187:10100";
//	private String MdAddress = "tcp://180.168.146.187:10110";

	// 7*24 CTP连通测试
	private String TradeAddr = "tcp://180.168.146.187:10130";
	private String MdAddr = "tcp://180.168.146.187:10131";

	private String BrokerId = "9999";
	private String InvestorId = "132796";
	private String UserId = "132796";
	private String AccountId = "132796";
	private String Password = "tc311911";

	private String TradingDay = "20200302";
	private String CurrencyId = "CNY";

	private String GatewayId = "simnow_test";

	@Test
	public void test() {

		final CtpConfig config = new CtpConfig().setTraderAddr(TradeAddr).setMdAddr(MdAddr).setBrokerId(BrokerId)
				.setInvestorId(InvestorId).setUserId(UserId).setAccountId(AccountId).setPassword(Password)
				.setTradingDay(TradingDay).setCurrencyId(CurrencyId);

		final Queue<FtdcRspMsg> queue = JctSingleConsumerQueue.multiProducer("Simnow-Handle-Queue").setCapacity(128)
				.build(msg -> {
					switch (msg.getType()) {
					case DepthMarketData:
						FtdcDepthMarketData depthMarketData = msg.getDepthMarketData();
						log.info(
								"Handle CThostFtdcDepthMarketDataField -> InstrumentID==[{}]  UpdateTime==[{}]  UpdateMillisec==[{}]  AskPrice1==[{}]  BidPrice1==[{}]",
								depthMarketData.getInstrumentID(), depthMarketData.getUpdateTime(),
								depthMarketData.getUpdateMillisec(), depthMarketData.getAskPrice1(),
								depthMarketData.getBidPrice1());
						break;
					case Order:
						FtdcOrder order = msg.getOrder();
						log.info("Handle RtnOrder -> OrderRef==[{}]", order.getOrderRef());
						break;
					case Trade:
						FtdcTrade trade = msg.getTrade();
						log.info("Handle RtnTrade -> OrderRef==[{}]", trade.getOrderRef());
						break;
					default:
						break;
					}
				});

		try (CtpGateway gateway = new CtpGateway(GatewayId, config, queue::enqueue)) {
			gateway.bootstrap();
			gateway.SubscribeMarketData(new String[] { "rb2010" });
			Threads.join();
		} catch (IOException e) {
			log.error("IOException -> {}", e.getMessage(), e);
		}

	}

}
