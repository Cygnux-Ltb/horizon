package io.horizon.ctp.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.mercury.common.log.Log4j2Configurator;
import io.mercury.common.log.Log4j2Configurator.LogLevel;

@SpringBootApplication
public class CtpServiceApplication {

	static {
		Log4j2Configurator.setLogFolder("ctp");
		Log4j2Configurator.setLogFilename("ctp-service");
		Log4j2Configurator.setLogLevel(LogLevel.INFO);
	}

	public static void main(String[] args) {
		SpringApplication.run(CtpServiceApplication.class, args);
	}

}
