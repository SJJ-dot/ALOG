package sjj.alog;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Log {
    public static final ConcurrentHashMap<String, Logger> loggerMap = new ConcurrentHashMap<>();

    public static final AtomicReference<Logger> logger = new AtomicReference<>();

    public static Logger get() {
        //凭感觉这么写的 没有道理
        if (logger.get() == null) {
            synchronized (logger) {
                if (logger.get() == null) {
                    logger.set(new Logger(Config.getDefaultConfig()));
                }
            }
        }
        return logger.get();
    }

    public static Logger tag(String tag) {
        Config config = Config.getDefaultConfig().clone();
        config.tag = tag;
        return tag(tag, config);
    }

    public static Logger tag(String tag, Config config) {
        Logger logger = loggerMap.get(tag);
        if (logger == null) {
            loggerMap.put(tag, new Logger(config));
        }
        return loggerMap.get(tag);
    }

    //================debug=======================
    public static void d(Object object) {
        get().d(1, object);
    }

    public static void d(Object object, Throwable throwable) {
        get().d(1, object, throwable);
    }

    public static void d(int sq, Object object) {
        get().d(1 + sq, object);
    }

    public static void d(int sq, Object object, Throwable throwable) {
        get().d(1 + sq, object, throwable);
    }

    //===============info================
    public static void i(Object object) {
        get().i(1, object);
    }

    public static void i(Object object, Throwable throwable) {
        get().i(1, object, throwable);
    }

    public static void i(int sq, Object object) {
        get().i(1 + sq, object);
    }

    public static void i(int sq, Object object, Throwable throwable) {
        get().i(1 + sq, object, throwable);
    }

    //===============WARN================
    public static void w(Object object) {
        get().w(1, object);
    }

    public static void w(Object object, Throwable throwable) {
        get().w(1, object, throwable);
    }

    public static void w(int sq, Object object) {
        get().w(1 + sq, object);
    }

    public static void w(int sq, Object object, Throwable throwable) {
        get().w(1 + sq, object, throwable);
    }

    //===============ERROR================
    public static void e(Object object) {
        get().e(1, object);
    }

    public static void e(Object object, Throwable throwable) {
        get().e(1, object, throwable);
    }

    public static void e(int sq, Object object) {
        get().e(1 + sq, object);
    }

    public static void e(int sq, Object object, Throwable throwable) {
        get().e(1 + sq, object, throwable);
    }
}
