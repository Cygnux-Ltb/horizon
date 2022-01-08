package io.horizon.ctp.adaptor;

import static io.mercury.common.concurrent.queue.jct.JctSingleConsumerQueue.mpscQueue;

import java.io.IOException;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import com.typesafe.config.Config;

import io.horizon.ctp.gateway.FtdcRspMsg;
import io.horizon.market.instrument.Instrument;
import io.horizon.trader.account.Account;
import io.horizon.trader.adaptor.AbstractAdaptor;
import io.horizon.trader.order.ChildOrder;
import io.mercury.common.concurrent.queue.Queue;
import io.mercury.common.log.Log4j2LoggerFactory;
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
		this.source = ZmqConfigurator.withConfig(config, "adaptor.source").newSubscriber((topic, msg) -> {

		});
		
		this.target = ZmqConfigurator.withConfig(config, "adaptor.target").newPublisher("", msg -> {

			return null;
		});
		this.queue = mpscQueue(ClassName + "-Buf").setCapacity(32).build(msg -> {
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

	/**
	 * 订阅行情实现
	 */
	@Override
	public boolean subscribeMarketData(@Nonnull Instrument[] instruments) {
		return false;
	}

	@Override
	public boolean newOredr(ChildOrder order) {
		return false;
	}

	@Override
	public boolean cancelOrder(ChildOrder order) {
		return false;
	}

	// 查询互斥锁, 保证同时只进行一次查询, 满足监管要求
	private final Object mutex = new Object();

	// 查询间隔, 依据CTP规定限制
	private final long queryInterval = 1100L;

	@Override
	public boolean queryOrder(@Nonnull Instrument instrument) {
		return false;
	}

	@Override
	public boolean queryPositions(@Nonnull Instrument instrument) {
		return false;
	}

	@Override
	public boolean queryBalance() {
		return false;
	}

	@Override
	public void close() throws IOException {
		source.close();
		target.close();
		adaptor.close();
	}

}
