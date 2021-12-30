package io.horizon.ctp.launch;

import java.io.File;

import org.slf4j.Logger;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import io.horizon.ctp.adaptor.CtpAdaptor;
import io.horizon.ctp.adaptor.CtpConfig;
import io.horizon.market.instrument.Instrument;
import io.horizon.market.instrument.InstrumentKeeper;
import io.horizon.trader.account.Account;
import io.mercury.common.datetime.pattern.DateTimePattern;
import io.mercury.common.log.Log4j2Configurator;
import io.mercury.common.log.Log4j2Configurator.LogLevel;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.common.thread.Threads;
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
		System.out.println("Config file path -> " + filePath);
		File file = new File(filePath);
		if (!file.exists()) {
			System.out.println("Config file does not exist");
			throw new IllegalArgumentException("file does not exist");
		}
		Config config = ConfigFactory.parseFile(file);
		String mode = config.getString("adaptor.mode");
		log.info("adaptor run mode == {}", mode);

		String instrumentCodes = config.getString("instrumentCodes");
		log.info("instrument codes == {}", instrumentCodes);

		Instrument[] instruments = InstrumentKeeper.getInstrument(instrumentCodes.split(","));

		// final SubAccount subAccount = new SubAccount(config, account);

		if (mode.equals("zmq")) {
			try (CtpZmqHandler module = new CtpZmqHandler(config);
					CtpAdaptor adaptor = new CtpAdaptor(new Account(config), CtpConfig.with(config), module)) {
				adaptor.startup();
				adaptor.subscribeMarketData(instruments);
				Threads.join();
			} catch (Exception e) {
				log.error("exception message -> {}", e.getMessage(), e);
			}
		} else if (mode.equals("rmq")) {
			// TODO
			throw new UnsupportedOperationException("The current version does not support [rmq] mode");
		}

	}

}
