package io.horizon.ctp.launch;

import java.io.File;

import org.junit.Test;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import io.mercury.common.sys.SysProperties;

public class CtpAdaptorStartupTest {

	@Test
	public void test() {
		Config rootConfig = ConfigFactory.defaultApplication();

		String configFile = rootConfig.getString("config.file");
		System.out.println(configFile);

		File file = new File(SysProperties.USER_HOME_CONFIG_FOLDER, configFile);

		System.out.println(file.getAbsolutePath());

		Config config = ConfigFactory.parseFile(file);

		System.out.println(config.getString("test0"));
		System.out.println(config.getString("test1"));
		System.out.println(config.hasPath("test1"));
		System.out.println(config.hasPath("test2"));

	}

}
