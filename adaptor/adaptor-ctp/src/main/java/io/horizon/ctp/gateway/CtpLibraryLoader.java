package io.horizon.ctp.gateway;

import static io.mercury.common.sys.SysProperties.JAVA_LIBRARY_PATH;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;

import io.horizon.ctp.exception.NativeLibraryLoadException;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.sys.SysProperties;

public final class CtpLibraryLoader {

	private static final Logger log = CommonLoggerFactory.getLogger(CtpLibraryLoader.class);

	private static final AtomicBoolean isLoaded = new AtomicBoolean(false);

	static void loadLibrary() throws NativeLibraryLoadException {
		if (isLoaded.compareAndSet(false, true)) {
			try {
				log.info("Trying to load library.....");
				// 根据操作系统选择加载不同库文件
				if (SysProperties.OS_NAME.toLowerCase().startsWith("windows")) {
					log.info("Copy win64 library file to java.library.path -> {}", JAVA_LIBRARY_PATH);
					// TODO 复制DLL文件到LIBRARY_PATH目录
					// 加载.dll文件
					System.loadLibrary("thostapi_wrap");
					log.info("System.loadLibrary() -> thostapi_wrap");
					System.loadLibrary("thostmduserapi_se");
					log.info("System.loadLibrary() -> thostmduserapi_se");
					System.loadLibrary("thostmduserapi_se");
					log.info("System.loadLibrary() -> thostmduserapi_se");
				} else {
					log.info("Copy linux64 library file to java.library.path -> {}", JAVA_LIBRARY_PATH);
					// TODO 复制SO文件到LIBRARY_PATH目录
					// 加载.so文件
					System.load("/usr/lib/libthostapi_wrap.so");
					log.info("System.load() -> /usr/lib/libthostapi_wrap.so");
					System.load("/usr/lib/libthostmduserapi_se.so");
					log.info("System.load() -> /usr/lib/libthostmduserapi_se.so");
					System.load("/usr/lib/libthosttraderapi_se.so");
					log.info("System.load() -> /usr/lib/libthosttraderapi_se.so");
				}
				log.info("Load library success...");
			} catch (SecurityException e) {
				throw new NativeLibraryLoadException("Load native library failure, Thrown out SecurityException", e);
			} catch (UnsatisfiedLinkError e) {
				throw new NativeLibraryLoadException("Load native library failure, Thrown out UnsatisfiedLinkError", e);
			} catch (NullPointerException e) {
				throw new NativeLibraryLoadException("Load native library failure, Thrown out NullPointerException", e);
			}
		} else {
			log.warn(
					"Library already loaded, The FtdcLibraryLoader.loadLibrary() function cannot be called repeatedly");
		}
	}

	public static void main(String[] args) {
		System.out.println(JAVA_LIBRARY_PATH);
	}

}
