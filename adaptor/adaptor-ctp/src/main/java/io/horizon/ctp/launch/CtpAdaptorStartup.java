package io.horizon.ctp.launch;

import static java.lang.System.currentTimeMillis;

import java.io.File;
import java.io.IOException;

import com.rabbitmq.client.MessageProperties;

import io.mercury.common.datetime.pattern.DateTimePattern;
import io.mercury.common.lang.Assertor;
import io.mercury.common.log.LogConfigurator;
import io.mercury.common.log.LogConfigurator.LogLevel;
import io.mercury.common.util.PropertiesUtil;
import io.mercury.transport.rabbitmq.configurator.RabbitConnection;
import io.mercury.transport.rabbitmq.configurator.RabbitPublisherCfg;
import io.mercury.transport.rabbitmq.declare.ExchangeRelationship;

public final class CtpAdaptorStartup {

	static {
		LogConfigurator.setLogLevel(LogLevel.INFO);
		LogConfigurator.setLogFolder("ftdc");
		LogConfigurator.setLogFolder(DateTimePattern.YYYYMMDD_HHMMSS.now());
	}

	public static void main(String[] args) {

		Assertor.requiredLength(args, 1, "input args");

		final String propFile = args[0];

		File file = new File(propFile);

		try {
			PropertiesUtil.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		RabbitConnection connection = RabbitConnection
				.configuration(args[0], Integer.parseInt(args[1]), args[2], args[3]).build();

		ExchangeRelationship exchange = ExchangeRelationship.fanout("");

		RabbitPublisherCfg configurator = RabbitPublisherCfg.configuration(connection, exchange)
				.setMsgPropsSupplier(() -> MessageProperties.PERSISTENT_BASIC.builder()
						.correlationId(Long.toString(currentTimeMillis())).build())
				.build();

		System.out.println(configurator);

	}

}
