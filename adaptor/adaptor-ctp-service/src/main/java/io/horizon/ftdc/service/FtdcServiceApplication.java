package io.horizon.ftdc.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.mercury.common.log.LogConfigurator;
import io.mercury.common.log.LogConfigurator.LogLevel;

@SpringBootApplication
public class FtdcServiceApplication {

	static {
		LogConfigurator.setLogFolder("ftdc");
		LogConfigurator.setLogFilename("ftdc-service");
		LogConfigurator.setLogLevel(LogLevel.INFO);
	}

	public static void main(String[] args) {
		SpringApplication.run(FtdcServiceApplication.class, args);
	}

}
