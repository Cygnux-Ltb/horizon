package io.horizon.ftdc.gateway;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;

import io.horizon.ftdc.gateway.bean.FtdcDepthMarketData;
import io.horizon.ftdc.gateway.bean.FtdcOrder;
import io.horizon.ftdc.gateway.bean.FtdcTrade;
import io.mercury.common.concurrent.queue.jct.JctScQueue;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.thread.Threads;

public class CtpGatewayTest {

	private static final Logger log = CommonLoggerFactory.getLogger(CtpGatewayTest.class);

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

		FtdcConfig simnowUserInfo = new FtdcConfig().setTraderAddr(TradeAddr).setMdAddr(MdAddr).setBrokerId(BrokerId)
				.setInvestorId(InvestorId).setUserId(UserId).setAccountId(AccountId).setPassword(Password)
				.setTradingDay(TradingDay).setCurrencyId(CurrencyId);

		try (FtdcGateway gateway = new FtdcGateway(GatewayId, simnowUserInfo,
				JctScQueue.mpsc("Simnow-Handle-Queue").capacity(128).buildWithProcessor(msg -> {
					switch (msg.getRspType()) {
					case FtdcDepthMarketData:
						FtdcDepthMarketData depthMarketData = msg.getFtdcDepthMarketData();
						log.info(
								"Handle CThostFtdcDepthMarketDataField -> InstrumentID==[{}]  UpdateTime==[{}]  UpdateMillisec==[{}]  AskPrice1==[{}]  BidPrice1==[{}]",
								depthMarketData.getInstrumentID(), depthMarketData.getUpdateTime(),
								depthMarketData.getUpdateMillisec(), depthMarketData.getAskPrice1(),
								depthMarketData.getBidPrice1());
						break;
					case FtdcOrder:
						FtdcOrder order = msg.getFtdcOrder();
						log.info("Handle RtnOrder -> OrderRef==[{}]", order.getOrderRef());
						break;
					case FtdcTrade:
						FtdcTrade trade = msg.getFtdcTrade();
						log.info("Handle RtnTrade -> OrderRef==[{}]", trade.getOrderRef());
						break;
					default:
						break;
					}
				}))) {
			gateway.bootstrap();
			gateway.SubscribeMarketData("rb2010");
			Threads.join();
		} catch (IOException e) {
			log.error("IOException -> {}", e.getMessage(), e);
		}

	}

}
