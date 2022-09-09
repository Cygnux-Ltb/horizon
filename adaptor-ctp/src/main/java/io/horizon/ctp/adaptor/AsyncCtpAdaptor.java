package io.horizon.ctp.adaptor;

import com.typesafe.config.Config;
import io.horizon.ctp.gateway.msg.FtdcRspMsg;
import io.horizon.market.instrument.Instrument;
import io.horizon.trader.account.Account;
import io.horizon.trader.adaptor.AbstractAdaptor;
import io.horizon.trader.adaptor.AdaptorType;
import io.horizon.trader.transport.inbound.*;
import io.mercury.common.concurrent.queue.jct.JctSingleConsumerQueue;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.serialization.avro.msg.AvroBinaryMsg;
import io.mercury.transport.zmq.ZmqConfigurator;
import io.mercury.transport.zmq.ZmqPublisher;
import io.mercury.transport.zmq.ZmqSubscriber;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * CTP Adaptor, 用于连接上期CTP柜台
 *
 * @author yellow013
 */
public class AsyncCtpAdaptor extends AbstractAdaptor {

    private static final Logger log = Log4j2LoggerFactory.getLogger(AsyncCtpAdaptor.class);

    private final CtpAdaptor adaptor;

    private final ZmqSubscriber source;
    private final ZmqPublisher<FtdcRspMsg> target;

    private static final String ClassName = AsyncCtpAdaptor.class.getSimpleName();

    /**
     * 传入MarketDataHandler, OrderReportHandler, AdaptorReportHandler实现,
     * 由构造函数内部转换为MPSC队列缓冲区
     *
     * @param account Account
     * @param config  Config
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

        this.target = ZmqConfigurator.withConfig("adaptor.target", config).newPublisher("", msg -> null);
        var queue = JctSingleConsumerQueue
                .mpscQueue(ClassName + "-Buf").setCapacity(32)
                .process((FtdcRspMsg msg) ->
                        target.publish(msg)
                );
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
    public boolean newOrder(@Nonnull DtoNewOrder order) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean cancelOrder(@Nonnull DtoCancelOrder order) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean queryOrder(@Nonnull DtoQueryOrder query) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean queryPositions(@Nonnull DtoQueryPositions query) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean queryBalance(@Nonnull DtoQueryBalance query) {
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
