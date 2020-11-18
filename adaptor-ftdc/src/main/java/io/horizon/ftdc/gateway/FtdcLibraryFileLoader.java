package io.horizon.ftdc.gateway;

import static io.mercury.common.sys.SysProperties.JAVA_LIBRARY_PATH;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.sys.SysProperties;

public final class FtdcLibraryFileLoader {

	private static final Logger log = CommonLoggerFactory.getLogger(FtdcLibraryFileLoader.class);

	private static final AtomicBoolean isLoaded = new AtomicBoolean(false);

	static void loadLibrary() {
		if (isLoaded.compareAndSet(false, true)) {
			try {
				log.info("Trying to load library.....");
				// 根据操作系统选择加载不同库文件
				if (SysProperties.OS_NAME.toLowerCase().startsWith("windows")) {
					log.info("Copy win64 library file to java.library.path -> {}", JAVA_LIBRARY_PATH);
					// TODO 复制DLL文件到LIBRARY_PATH目录
					// 加载.dll文件
					System.loadLibrary("thostapi_wrap");
					log.info("Call System#loadLibrary() -> thostapi_wrap");
					System.loadLibrary("thostmduserapi_se");
					log.info("Call System#loadLibrary() -> thostmduserapi_se");
					System.loadLibrary("thostmduserapi_se");
					log.info("Call System#loadLibrary() -> thostmduserapi_se");
				} else {
					log.info("Copy linux64 library file to java.library.path -> {}", JAVA_LIBRARY_PATH);
					// TODO 复制SO文件到LIBRARY_PATH目录
					// 加载.so文件
					System.load("/usr/lib/libthostapi_wrap.so");
					log.info("Call System#load() -> /usr/lib/libthostapi_wrap.so");
					System.load("/usr/lib/libthostmduserapi_se.so");
					log.info("Call System#load() -> /usr/lib/libthostmduserapi_se.so");
					System.load("/usr/lib/libthosttraderapi_se.so");
					log.info("Call System#load() -> /usr/lib/libthosttraderapi_se.so");
				}
				log.info("Load library success...");
			} catch (SecurityException e) {
				log.error("Load library failure, throw SecurityException", e);
				throw new RuntimeException(e);
			} catch (UnsatisfiedLinkError e) {
				log.error("Load library failure, throw UnsatisfiedLinkError", e);
				throw new RuntimeException(e);
			} catch (NullPointerException e) {
				log.error("Load library failure, throw NullPointerException", e);
				throw new RuntimeException(e);
			}
		} else {
			log.warn(
					"Library already loaded, The FtdcLibraryLoader#loadLibrary() function cannot be called repeatedly");
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(JAVA_LIBRARY_PATH);
	}

}
