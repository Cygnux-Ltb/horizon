package io.horizon.ctp.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.mercury.common.log.LogConfigurator;
import io.mercury.common.log.LogConfigurator.LogLevel;

@SpringBootApplication
public class CtpServiceApplication {

	static {
		LogConfigurator.setLogFolder("ctp");
		LogConfigurator.setLogFilename("ctp-service");
		LogConfigurator.setLogLevel(LogLevel.INFO);
	}

	public static void main(String[] args) {
		SpringApplication.run(CtpServiceApplication.class, args);
	}

}
