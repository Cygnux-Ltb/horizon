package io.horizon.ctp.launch;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import io.horizon.ctp.gateway.FtdcRspMsg;
import io.mercury.common.sys.SysProperties;
import io.mercury.transport.rabbitmq.AdvancedRabbitMqPublisher;

public class CtpRabbitHandler implements Runnable, Closeable {

	@SuppressWarnings("unused")
	private final Config config;

	private AdvancedRabbitMqPublisher<FtdcRspMsg> publisher;

	public CtpRabbitHandler(Config config) {
		this.config = config;
	}

	@Override
	public void run() {

//		RabbitConnection connection = RabbitConnection
//				.configuration(args[0], Integer.parseInt(args[1]), args[2], args[3]).build();
//
//		ExchangeRelationship exchange = ExchangeRelationship.fanout("");
//
//		RabbitPublisherConfig configurator = RabbitPublisherConfig.configuration(connection, exchange)
//				.setMsgPropsSupplier(() -> MessageProperties.PERSISTENT_BASIC.builder()
//						.correlationId(Long.toString(currentTimeMillis())).build())
//				.build();

	}

	@Override
	public void close() throws IOException {
		publisher.close();
	}

	public static void main(String[] args) {
		File file = new File(SysProperties.USER_HOME_CONFIG_FOLDER, "");
		if (!file.exists()) {
			System.out.println("Config file does not exist");
			throw new IllegalArgumentException("file does not exist");
		}
		Config config = ConfigFactory.parseFile(file);
		@SuppressWarnings("unused")
		String mode = config.getString("mode");
	}

}
