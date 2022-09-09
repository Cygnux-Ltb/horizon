package io.horizon.ctp.gateway.utils;

import io.mercury.common.lang.exception.NativeLibraryLoadException;
import io.mercury.common.log.Log4j2LoggerFactory;
import io.mercury.common.sys.SysProperties;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

import static io.mercury.common.sys.SysProperties.JAVA_LIBRARY_PATH;
import static io.mercury.common.sys.SysProperties.OS_NAME;

public final class CtpLibraryLoader {

    private CtpLibraryLoader() {
    }

    private static final Logger log = Log4j2LoggerFactory.getLogger(CtpLibraryLoader.class);

    private static final AtomicBoolean isLoaded = new AtomicBoolean(false);

    public static void loadLibrary(Class<?> clazz) throws NativeLibraryLoadException {
        if (isLoaded.compareAndSet(false, true)) {
            try {
                log.info("Trying to load library by {}", clazz);
                // 根据操作系统选择加载不同库文件
                if (OS_NAME.toLowerCase().startsWith("windows")) {
                    log.info("Copy win64 library file to java.library.path -> {}", JAVA_LIBRARY_PATH);
                    // TODO 复制DLL文件到LIBRARY_PATH目录
                    // 加载.dll文件
                    System.loadLibrary("thostapi_wrap");
                    log.info("System.loadLibrary() -> thostapi_wrap");
                    System.loadLibrary("thostmduserapi_se");
                    log.info("System.loadLibrary() -> thostmduserapi_se");
                    System.loadLibrary("thostmduserapi_se");
                    log.info("System.loadLibrary() -> thostmduserapi_se");
                } else if (OS_NAME.toLowerCase().startsWith("linux")) {
                    log.info("Copy linux64 library file to java.library.path -> {}", JAVA_LIBRARY_PATH);
                    // TODO 复制SO文件到LIBRARY_PATH目录
                    // 加载.so文件
                    //////////////////////////////// libthostapi_wrap.so
                    System.load("/usr/lib/ctp_6.3.13/libthostapi_wrap.so");
                    log.info("System.load() -> /usr/lib/libthostapi_wrap.so");
                    //////////////////////////////// libthostmduserapi_se.so
                    System.load("/usr/lib/ctp_6.3.13/libthostmduserapi_se.so");
                    log.info("System.load() -> /usr/lib/libthostmduserapi_se.so");
                    //////////////////////////////// libthosttraderapi_se.so
                    System.load("/usr/lib/ctp_6.3.13/libthosttraderapi_se.so");
                    log.info("System.load() -> /usr/lib/libthosttraderapi_se.so");
                } else {
                    log.error("Unsupported operating system : {}", OS_NAME);
                    throw new UnsupportedOperationException("Unsupported operating system : " + OS_NAME);
                }
                log.info("Load library success by {}", clazz);
            } catch (SecurityException | UnsatisfiedLinkError | NullPointerException e) {
                isLoaded.set(false);
                log.info("Load library failure by {}", clazz);
                throw new NativeLibraryLoadException("Load native library failure, Throw by -> " + e.getClass(), e);
            }
        } else {
            log.warn("Library already loaded, CtpLibraryLoader::loadLibrary() func cannot be called repeatedly by {}",
                    clazz);
        }
    }

    public static void main(String[] args) {
        System.out.println(SysProperties.OS_NAME);
        System.out.println(JAVA_LIBRARY_PATH);
    }

}
