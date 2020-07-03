package sjj.alog;

/**
 * Created by sjj on 2016-10-11.
 */

public class Logger {
    private final LogUtils logUtils;
    private Config config;

    public Logger(Config config) {
        this.logUtils = new LogUtils(config);
        this.config = config;
    }

    //================debug=======================
    public void d(Object object) {
        log(Config.DEBUG, getCallM(), String.valueOf(object), null);
    }

    public void d(Object object, Throwable throwable) {
        log(Config.DEBUG, getCallM(), String.valueOf(object), throwable);
    }

    public void d(int sq, Object object) {
        log(Config.DEBUG, getCallM(sq), String.valueOf(object), null);
    }

    public void d(int sq, Object object, Throwable throwable) {
        log(Config.DEBUG, getCallM(sq), String.valueOf(object), throwable);
    }

    //===============info================
    public void i(Object object) {
        log(Config.INFO, getCallM(), String.valueOf(object), null);
    }

    public void i(Object object, Throwable throwable) {
        log(Config.INFO, getCallM(), String.valueOf(object), throwable);
    }

    public void i(int sq, Object object) {
        log(Config.INFO, getCallM(sq), String.valueOf(object), null);
    }

    public void i(int sq, Object object, Throwable throwable) {
        log(Config.INFO, getCallM(sq), String.valueOf(object), throwable);
    }

    //===============WARN================
    public void w(Object object) {
        log(Config.WARN, getCallM(), String.valueOf(object), null);
    }

    public void w(Object object, Throwable throwable) {
        log(Config.WARN, getCallM(), String.valueOf(object), throwable);
    }

    public void w(int sq, Object object) {
        log(Config.WARN, getCallM(sq), String.valueOf(object), null);
    }

    public void w(int sq, Object object, Throwable throwable) {
        log(Config.WARN, getCallM(sq), String.valueOf(object), throwable);
    }

    //===============ERROR================
    public void e(Object object) {
        log(Config.ERROR, getCallM(), String.valueOf(object), null);
    }

    public void e(Object object, Throwable throwable) {
        log(Config.ERROR, getCallM(), String.valueOf(object), throwable);
    }

    public void e(int sq, Object object) {
        log(Config.ERROR, getCallM(sq), String.valueOf(object), null);
    }

    public void e(int sq, Object object, Throwable throwable) {
        log(Config.ERROR, getCallM(sq), String.valueOf(object), throwable);
    }

    //===========================utils=========================
    private void log(int lev, CallMethodException method, String message, Throwable throwable) {
        logUtils.l(lev, method, message, throwable);
    }

    private CallMethodException getCallM() {
        return getCallM(1);
    }

    private CallMethodException getCallM(int sq) {
        return config.printMethod ? new CallMethodException(sq) : null;
    }

    static class CallMethodException extends Exception {
        private int sq;

        public CallMethodException(int sq) {
            this.sq = sq;
        }

        public String getMethod() {
            StackTraceElement element = getStackTrace()[2 + sq];
            StringBuilder buf = new StringBuilder();

            buf.append(element.getMethodName());

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


}
