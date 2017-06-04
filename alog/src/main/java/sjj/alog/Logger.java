package sjj.alog;

import android.util.Log;

import sjj.alog.file.LogFile;

/**
 * Created by SJJ on 2017/3/5.
 */

class Logger {

    private Config config;
    private LogFile logFile;

    Logger(Config config) {
        this.config = config;
        if (config.hold) {
            logFile = new LogFile(config.getDir());
            if (config.deleteOldLog)
            logFile.deleteOldLogFile();
        }
    }

    private boolean isEnable(int lev) {
        return config.enable && (config.multiple && lev >= config.lev || !config.multiple && config.lev == lev);
    }

    void l(int lev, String tag, String msg) {
        writeToFile(lev, tag, msg, null);
        if (!isEnable(lev)) return;
        tag = "Log:" + tag;
        switch (lev) {
            case Config.INFO:
                Log.i(tag, msg);
                break;
            case Config.DEBUG:
                Log.d(tag, msg);
                break;
            case Config.WARN:
                Log.w(tag, msg);
                break;
            case Config.ERROR:
                Log.e(tag, msg);
                break;
        }

    }

    void l(int lev, String tag, String msg, Throwable throwable) {

        writeToFile(lev, tag, msg, throwable);
        if (!isEnable(lev)) return;
        tag = "Log:" + tag;
        switch (lev) {
            case Config.INFO:
                Log.i(tag, msg, throwable);
                break;
            case Config.DEBUG:
                Log.d(tag, msg, throwable);
                break;
            case Config.WARN:
                Log.w(tag, msg, throwable);
                break;
            case Config.ERROR:
                Log.e(tag, msg, throwable);
                break;
        }
    }

    private boolean isHoldLog(int lev) {
        return config.hold && (lev >= config.holdLev && config.holdMultiple || !config.holdMultiple && lev == config.holdLev);
    }

    private void writeToFile(int lev, String tag, String msg, Throwable throwable) {
        if (!isHoldLog(lev)) return;
        StringBuilder sb = new StringBuilder();
        switch (lev) {
            case Config.INFO:
                sb.append("I:");
                break;
            case Config.DEBUG:
                sb.append("D:");
                break;
            case Config.WARN:
                sb.append("W:");
                break;
            case Config.ERROR:
                sb.append("E:");
                break;
        }
        if (logFile != null)
            logFile.push(sb.append(tag).append(":").append(msg).append(throwable(throwable)).toString());
    }

    private String throwable(Throwable throwable) {
        if (throwable == null) return "";
        StringBuilder buffer = new StringBuilder();
        buffer.append(throwable.getClass()).append(", ").append(throwable.getMessage()).append("\n");
        while (throwable != null) {
            for (StackTraceElement element : throwable.getStackTrace()) {
                buffer.append(element.toString()).append("\n");
            }
            throwable = throwable.getCause();
            if (throwable != null)
                buffer.append("caused by ");
        }
        return buffer.toString();
    }
}
