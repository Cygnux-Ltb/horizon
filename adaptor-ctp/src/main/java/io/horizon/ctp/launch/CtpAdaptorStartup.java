package io.horizon.ctp.launch;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import io.horizon.ctp.adaptor.CtpAdaptor;
import io.horizon.ctp.adaptor.CtpConfig;
import io.horizon.trader.account.Account;
import io.mercury.common.datetime.pattern.DateTimePattern;
import io.mercury.common.log.Log4j2Configurator;
import io.mercury.common.log.Log4j2Configurator.LogLevel;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.common.util.StringSupport;

public final class CtpAdaptorStartup {

	static {
		Log4j2Configurator.setLogLevel(LogLevel.INFO);
		Log4j2Configurator.setLogFolder("ctp");
		Log4j2Configurator.setLogFilename(DateTimePattern.YYYYMMDDHHMMSS.now());
	}

	private static final Logger log = Log4j2LoggerFactory.getLogger(CtpAdaptorStartup.class);

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Config file path must be entere");
			throw new IllegalArgumentException("Input args is empty");
		}
		final String filePath = args[0];
		if (StringSupport.isNullOrEmpty(filePath)) {
			System.out.println("Config file path cannot be empty");
			throw new IllegalArgumentException("File path is empty");
		}
		System.out.println("reading config file path -> " + filePath);
		File file = new File(filePath);
		if (!file.exists()) {
			System.out.println("Config file does not exist");
			throw new IllegalArgumentException("file does not exist");
		}
		Config config = ConfigFactory.parseFile(file);
		String mode = config.getString("mode");

		final Account account = new Account(config);
		
		

		CtpAdaptor adaptor = null;
		try {
			if (mode.equals("zmq")) {
				CtpZmqHandler module = null;
				try {
					module = new CtpZmqHandler(config);
					adaptor = new CtpAdaptor(account, CtpConfig.of(config), module);
				} catch (Exception e) {
					log.error("{}", e.getMessage(), e);
				} finally {
					if (module != null) {
						try {
							module.close();
						} catch (IOException e) {
							log.error("{}", e.getMessage(), e);
						}
					}
				}
			}
			if (mode.equals("rmq")) {
				// TODO
				throw new UnsupportedOperationException("The current version does not support [rmq] mode");
			}
		} catch (Exception e) {
			log.error("{}", e.getMessage(), e);
		} finally {
			if (adaptor != null) {
				try {
					adaptor.close();
				} catch (IOException e) {
					log.error("{}", e.getMessage(), e);
				}
			}
		}
	}

}
