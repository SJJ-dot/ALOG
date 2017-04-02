package sjj.alog;

import android.util.Log;

/**
 * Created by sjj on 2016-10-11.
 */

public class ALog {
    private static Logger logger;
    private static Logger get() {
        if (logger == null) {
            logger = new Logger(Config.getDefaultConfig());
        }
        return logger;
    }


    public static void d(Object object) {
        log(Config.DEBUG, getCallM(), object + "");
    }

    public static void d(int i, Object o) {
        log(Config.DEBUG, getCallM(i), o + "");
    }

    public static void e(Object object) {
        log(Config.ERROR, getCallM(), object + "");
    }
    public static void e(int sq,Object object) {
        log(Config.ERROR, getCallM(sq), object + "");
    }
    public static void e(Object object, Throwable throwable) {
        log(Config.ERROR, getCallM(), object + "", throwable);
    }

    public static void e(int sq, Object object, Throwable throwable) {
        log(Config.ERROR, getCallM(sq), object + "", throwable);
    }

    public static void i(Object object) {
        log(Config.INFO, getCallM(), object + "");
    }

    public static void i(Object object, Throwable throwable) {
        log(Config.INFO, getCallM(), object + "", throwable);
    }

    public static void i(int sq, Object object) {
        log(Config.INFO, getCallM(sq), object + "");

    }

    public static void w(int sq, Object object, Throwable throwable) {
        log(Config.WARN, getCallM(sq), object + "", throwable);
    }

    public static void w(Object object, Throwable throwable) {
        log(Config.WARN, getCallM(), object + "", throwable);
    }

    public static void w(int sq, Object object) {
        log(Config.WARN, getCallM(sq), object + "");
    }

    public static void w(Object object) {
        log(Config.WARN, getCallM(), object + "");
    }

    private static void log(int lev, String tag, String message) {
        get().l(lev,tag,message);
    }

    private static void log(int lev, String tag, String message, Throwable throwable) {
        get().l(lev, tag, message, throwable);
    }

    private static String getCallM() {
        return getCallM(1);
    }

    private static String getCallM(int sq) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[4 + sq];
        StringBuilder buf = new StringBuilder();

        buf.append("(tag)").append(element.getMethodName());

        if (element.isNativeMethod()) {
            buf.append("(Native Method)");
        } else {
            String fName = element.getFileName();

            if (fName == null) {
                buf.append("(Unknown Source)");
            } else {
                int lineNum = element.getLineNumber();

                buf.append('(');
                buf.append(fName);
                if (lineNum >= 0) {
                    buf.append(':');
                    buf.append(lineNum);
                }
                buf.append(')');
            }
        }

        return buf.toString();
    }
}
