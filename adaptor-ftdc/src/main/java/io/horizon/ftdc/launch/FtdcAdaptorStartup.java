package io.horizon.ftdc.launch;

import java.io.File;
import java.io.IOException;

import com.rabbitmq.client.MessageProperties;

import io.mercury.common.datetime.pattern.spec.DateTimePattern;
import io.mercury.common.log.CommonLogConfigurator;
import io.mercury.common.log.CommonLogConfigurator.LogLevel;
import io.mercury.common.util.Assertor;
import io.mercury.common.util.PropertiesUtil;
import io.mercury.transport.rabbitmq.configurator.RabbitConnection;
import io.mercury.transport.rabbitmq.configurator.RmqPublisherConfigurator;
import io.mercury.transport.rabbitmq.declare.ExchangeDefinition;

public final class FtdcAdaptorStartup {

	static {
		CommonLogConfigurator.setLogLevel(LogLevel.INFO);
		CommonLogConfigurator.setFolder("ftdc");
		CommonLogConfigurator.setFolder(DateTimePattern.YYYYMMDD_HHMMSS.now());
	}

	public static void main(String[] args) {

		Assertor.requiredLength(args, 1, "input args");

		final String propFile = args[0];

		File file = new File(propFile);

		try {
			PropertiesUtil.loadProperties(file);
		} catch (IOException e) {

			e.printStackTrace();
		}

		RabbitConnection connection = RabbitConnection
				.configuration(args[0], Integer.parseInt(args[1]), args[2], args[3]).build();

		ExchangeDefinition exchange = ExchangeDefinition.fanout("");

		RmqPublisherConfigurator configurator = RmqPublisherConfigurator.configuration(connection, exchange)
				.setMsgPropsSupplier(() -> MessageProperties.PERSISTENT_BASIC.builder()
						.correlationId(Long.toString(System.currentTimeMillis())).build())
				.build();

		System.out.println(configurator);

	}

}
