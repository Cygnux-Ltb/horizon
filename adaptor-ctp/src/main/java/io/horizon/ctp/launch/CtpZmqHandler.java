package io.horizon.ctp.launch;

import com.typesafe.config.Config;
import io.horizon.ctp.gateway.msg.FtdcRspMsg;
import io.mercury.common.collections.queue.Queue;
import io.mercury.common.concurrent.queue.jct.JctSingleConsumerQueue;
import io.mercury.common.functional.Handler;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.common.thread.RunnableComponent.StartMode;
import io.mercury.serialization.json.JsonWrapper;
import io.mercury.transport.zmq.ZmqConfigurator;
import io.mercury.transport.zmq.ZmqPublisher;
import org.slf4j.Logger;

import java.io.Closeable;
import java.io.IOException;

public class CtpZmqHandler implements Closeable, Handler<FtdcRspMsg> {

    private static final Logger log = Log4j2LoggerFactory.getLogger(CtpZmqHandler.class);

    private final ZmqPublisher<String> publisher;

    private final Queue<FtdcRspMsg> queue;

    public CtpZmqHandler(Config config) {
        String topic = config.getString("zmq.topic");
        log.info("config zmq.topic == [{}]", topic);
        this.publisher = ZmqConfigurator.withConfig(config).newPublisherWithString(topic);
        this.queue = JctSingleConsumerQueue.mpscQueue("CtpZmqHandler-Queue").setCapacity(32)
                .setStartMode(StartMode.auto()).process(msg -> {
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
