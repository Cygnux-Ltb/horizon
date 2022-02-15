package io.horizon.ctp.adaptor;

import static io.mercury.common.concurrent.queue.jct.JctSingleConsumerQueue.mpscQueue;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import com.typesafe.config.Config;

import io.horizon.ctp.gateway.msg.FtdcRspMsg;
import io.horizon.market.instrument.Instrument;
import io.horizon.trader.account.Account;
import io.horizon.trader.adaptor.AbstractAdaptor;
import io.horizon.trader.adaptor.AdaptorType;
import io.horizon.trader.transport.inbound.CancelOrder;
import io.horizon.trader.transport.inbound.NewOrder;
import io.horizon.trader.transport.inbound.QueryBalance;
import io.horizon.trader.transport.inbound.QueryOrder;
import io.horizon.trader.transport.inbound.QueryPositions;
import io.mercury.common.collections.queue.Queue;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.serialization.avro.msg.AvroBinaryMsg;
import io.mercury.transport.zmq.ZmqConfigurator;
import io.mercury.transport.zmq.ZmqPublisher;
import io.mercury.transport.zmq.ZmqSubscriber;

/**
 * 
 * CTP Adaptor, 用于连接上期CTP柜台
 * 
 * @author yellow013
 *
 */
public class AsyncCtpAdaptor extends AbstractAdaptor {

	private static final Logger log = Log4j2LoggerFactory.getLogger(AsyncCtpAdaptor.class);

	private final CtpAdaptor adaptor;

	private final ZmqSubscriber source;
	private final ZmqPublisher<FtdcRspMsg> target;

	private final Queue<FtdcRspMsg> queue;

	private static final String ClassName = AsyncCtpAdaptor.class.getSimpleName();

	/**
	 * 传入MarketDataHandler, OrderReportHandler, AdaptorReportHandler实现,
	 * 由构造函数内部转换为MPSC队列缓冲区
	 * 
	 * @param account
	 * @param config
	 */
	public AsyncCtpAdaptor(@Nonnull Account account, @Nonnull Config config) {
		super(ClassName, account);
		this.source = ZmqConfigurator.withConfig("adaptor.source", config).newSubscriber((topic, msg) -> {
			try {
				AvroBinaryMsg.fromByteBuffer(ByteBuffer.wrap(msg));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		this.target = ZmqConfigurator.withConfig("adaptor.target", config).newPublisher("", msg -> {

			return null;
		});

		this.queue = mpscQueue(ClassName + "-Buf").setCapacity(32).process(msg -> {
			target.publish(msg);
		});
		this.adaptor = new CtpAdaptor(account, CtpConfig.with(config), queue);
	}

	@Override
	protected boolean startup0() {
		try {
			return adaptor.startup();
		} catch (Exception e) {
			log.error("Gateway exception -> {}", e.getMessage(), e);
			return false;
		}
	}

	@Override
	public AdaptorType getAdaptorType() {
		return CtpAdaptorType.INSTANCE;
	}

	/**
	 * 订阅行情实现
	 */
	@Override
	public boolean subscribeMarketData(@Nonnull Instrument[] instruments) {
		return false;
	}

	@Override
	public boolean newOredr(NewOrder order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancelOrder(CancelOrder order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queryOrder(QueryOrder req) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queryPositions(QueryPositions req) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queryBalance(QueryBalance req) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() throws IOException {
		source.close();
		target.close();
		adaptor.close();
	}

}
