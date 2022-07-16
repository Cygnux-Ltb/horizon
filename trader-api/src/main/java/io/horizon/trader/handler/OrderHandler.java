package io.horizon.trader.handler;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import io.horizon.trader.order.Order;
import io.mercury.common.log.Log4j2LoggerFactory;

@FunctionalInterface
public interface OrderHandler {

    void onOrder(@Nonnull final Order order);

    /**
     * Logger implements OrderHandler
     *
     * @author yellow013
     */
    class OrderLogger implements OrderHandler {

        private final Logger log;

        public OrderLogger(Logger log) {
            this.log = log == null ? Log4j2LoggerFactory.getLogger(getClass()) : log;
        }

        @Override
        public void onOrder(@Nonnull final Order order) {
            log.info("OrderLogger record -> {}", order);
        }

    }

}
