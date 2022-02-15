package io.horizon.ctp.gateway.msg;

import com.lmax.disruptor.EventFactory;

import io.horizon.ctp.gateway.msg.FtdcRspMsg.FtdcRspType;

public final class FtdcRspIntegratedMsg {
	
	public FtdcRspIntegratedMsg() {
	}

	@SuppressWarnings("unused")
	private FtdcRspType type;

	
	
	
	
	
	
	
	
	
	public static final EventFactory<FtdcRspIntegratedMsg> FACTORY = FtdcRspIntegratedMsg::new;
	
	
}