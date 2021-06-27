package io.horizon.ftdc.adaptor.converter;

import java.util.function.Function;

import org.slf4j.Logger;

import ctp.thostapi.CThostFtdcInputOrderActionField;
import io.horizon.ftdc.adaptor.FtdcAdaptorParamKey;
import io.horizon.ftdc.adaptor.consts.FtdcActionFlag;
import io.horizon.market.instrument.Instrument;
import io.horizon.trader.order.Order;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.param.Params;

/**
 * 
 * @author yellow013
 * 
 *         <pre>
struct CThostFtdcInputOrderActionField
{
   ///经纪公司代码
   TThostFtdcBrokerIDType BrokerID;
   ///投资者代码
   TThostFtdcInvestorIDType InvestorID;
   ///报单操作引用
   TThostFtdcOrderActionRefType OrderActionRef;
   ///报单引用
   TThostFtdcOrderRefType OrderRef;
   ///请求编号
   TThostFtdcRequestIDType RequestID;
   ///前置编号
   TThostFtdcFrontIDType FrontID;
   ///会话编号
   TThostFtdcSessionIDType SessionID;
   ///交易所代码
   TThostFtdcExchangeIDType ExchangeID;
   ///报单编号
   TThostFtdcOrderSysIDType OrderSysID;
   ///操作标志
   TThostFtdcActionFlagType ActionFlag;
   ///价格
   TThostFtdcPriceType LimitPrice;
   ///数量变化
   TThostFtdcVolumeType VolumeChange;
   ///用户代码
   TThostFtdcUserIDType UserID;
   ///合约代码
   TThostFtdcInstrumentIDType InstrumentID;
   ///投资单元代码
   TThostFtdcInvestUnitIDType InvestUnitID;
   ///IP地址
   TThostFtdcIPAddressType IPAddress;
   ///Mac地址
   TThostFtdcMacAddressType MacAddress;
};
 *         </pre>
 * 
 */
public final class ToCThostFtdcInputOrderAction implements Function<Order, CThostFtdcInputOrderActionField> {

	private static final Logger log = CommonLoggerFactory.getLogger(FromFtdcTrade.class);

	// 经纪公司代码
	private final String brokerId;
	// 投资者代码
	private final String investorId;
	// 用户代码
	private final String userId;
	// IP地址
	private final String ipAddress;
	// MAC地址
	private final String macAddress;

	public ToCThostFtdcInputOrderAction(Params<FtdcAdaptorParamKey> params) {
		this.brokerId = params.getString(FtdcAdaptorParamKey.BrokerId);
		this.investorId = params.getString(FtdcAdaptorParamKey.InvestorId);
		this.userId = params.getString(FtdcAdaptorParamKey.UserId);
		this.ipAddress = params.getString(FtdcAdaptorParamKey.IpAddr);
		this.macAddress = params.getString(FtdcAdaptorParamKey.MacAddr);
		log.info("Function -> ToCThostFtdcInputOrderAction initialized, brokerId=={}, investorId=={}, userId=={}",
				brokerId, investorId, userId);
	}

	@Override
	public CThostFtdcInputOrderActionField apply(Order order) {
		Instrument instrument = order.getInstrument();

		// 创建FTDC撤单类型
		CThostFtdcInputOrderActionField field = new CThostFtdcInputOrderActionField();

		// 经纪公司代码
		field.setBrokerID(brokerId);

		// 投资者代码
		field.setInvestorID(investorId);

		// 用户代码
		field.setUserID(userId);

		// IP地址
		field.setIPAddress(ipAddress);

		// MAC地址
		field.setMacAddress(macAddress);

		// 操作标志
		field.setActionFlag(FtdcActionFlag.Delete);

		// 交易所代码
		field.setExchangeID(instrument.getExchangeCode());

		// 合约代码
		field.setInstrumentID(instrument.getInstrumentCode());

		// 价格
		field.setLimitPrice(instrument.getPriceMultiplier().toDouble(order.getPrice().getOfferPrice()));

		// 数量变化
		field.setVolumeChange(order.getQty().getLeavesQty());

		// 返回FTDC撤单对象
		log.info("Create CThostFtdcInputOrderActionField finished");
		return field;
	}

}
