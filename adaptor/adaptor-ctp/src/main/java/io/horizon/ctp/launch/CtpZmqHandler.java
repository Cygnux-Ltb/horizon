package io.horizon.ctp.launch;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;

import com.typesafe.config.Config;

import io.horizon.ctp.gateway.FtdcRspMsg;
import io.mercury.common.concurrent.queue.Queue;
import io.mercury.common.concurrent.queue.jct.JctSingleConsumerQueue;
import io.mercury.common.functional.Handler;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.serialization.json.JsonWrapper;
import io.mercury.transport.zmq.ZmqConfigurator;
import io.mercury.transport.zmq.ZmqPublisher;

public class CtpZmqHandler implements Closeable, Handler<FtdcRspMsg> {

	private static final Logger log = Log4j2LoggerFactory.getLogger(CtpZmqHandler.class);

	private final ZmqPublisher<String> publisher;

	private final Queue<FtdcRspMsg> queue;

	public CtpZmqHandler(Config config) {
		this.publisher = ZmqConfigurator.with(config).newPublisherWithString("ctp");
		this.queue = JctSingleConsumerQueue.multiProducer("CtpZmqHandler-Queue").setCapacity(32).build(msg -> {
			String json = JsonWrapper.toJson(msg);
			log.info("Received msg -> {}", json);
			publisher.publish(json);
		});
	}

	@Override
	public void close() throws IOException {
		while (!queue.isEmpty())
			;
		publisher.close();
	}

	@Override
	public void handle(FtdcRspMsg msg) {
		queue.enqueue(msg);
	}

}
