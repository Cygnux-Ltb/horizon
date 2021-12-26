package io.horizon.adaptor.ctp;

import static io.mercury.common.sys.SysProperties.USER_HOME_FILE;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Unit test for simple App.
 */
public class CinfigTest {

	public static void main(String[] args) {

		Config config = ConfigFactory.parseFile(new File(USER_HOME_FILE, ".config/ctp.conf"));

		System.out.println(config.hasPath("test0"));
		
		config.entrySet().forEach(System.out::println);

	}

}
