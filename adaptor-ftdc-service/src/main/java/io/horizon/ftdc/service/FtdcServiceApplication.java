package io.horizon.ftdc.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.mercury.common.log.CommonLogConfigurator;
import io.mercury.common.log.CommonLogConfigurator.LogLevel;

@SpringBootApplication
public class FtdcServiceApplication {

	static {
		CommonLogConfigurator.setFolder("ftdc");
		CommonLogConfigurator.setFilename("ftdc-service");
		CommonLogConfigurator.setLogLevel(LogLevel.INFO);
	}

	public static void main(String[] args) {
		SpringApplication.run(FtdcServiceApplication.class, args);
	}
	
}
