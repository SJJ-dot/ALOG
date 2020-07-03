package sjj.alog;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by SJJ on 2017/3/5.
 */

public class Config implements Cloneable {
    public static final int ALL = 0;
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int WARN = 3;
    public static final int ERROR = 4;
    /**
     * 是否要输出调用方法
     */
    public boolean printMethod = true;
    /**
     * log输出控制
     */
    public boolean consolePrintEnable = true;
    /**
     * log 输出级别
     */
    public int consolePrintLev = Config.ALL;
    /**
     * 输出所有级别大于 {@link #consolePrintLev} 的log
     */
    public boolean consolePrintMultiple = true;

    /**
     * 控制台中输出堆栈轨迹的行数 0 不限制
     */
    public int consolePrintStackTraceLineNum = 0;

    /**
     * 是否需要在控制台中打印所有的log 。开启时，如果日志长度超出最长限制，分段打印
     */
    public boolean consolePrintAllLog = false;

    /**
     * 控制台输出 tag
     */
    public String tag = "Logger";

    public ExecutorService logExecutorService;

    public synchronized ExecutorService getLogExecutorService() {
        if (logExecutorService == null) {
            if (this == getDefaultConfig()) {
                logExecutorService = Executors.newSingleThreadExecutor();
            } else {
                logExecutorService = getDefaultConfig().getLogExecutorService();
            }
        }
        return logExecutorService;
    }


    /**
     * 是否保存日志
     */
    public boolean writeToFile = false;
    /**
     * 保存日志级别
     */
    public int writeToFileLev = ALL;
    /**
     * 记录所有级别大于 {@link #writeToFileLev} 的log
     */
    public boolean writeToFileMultiple = true;
    public boolean deleteOldLogFile = false;
    /**
     * 日志存放目录
     */
    public File writeToFileDir;
    public String writeToFileDirName;

    public File getWriteToFileDir() {
        if (writeToFileDir != null) {
            return writeToFileDir;
        } else {
            String fileName = TextUtils.isEmpty(writeToFileDirName) ? tag : writeToFileDirName;
            writeToFileDir = new File(Environment.getExternalStorageDirectory(), "ALog/" + fileName);
        }
        return writeToFileDir;
    }

    public ScheduledExecutorService logFileExecutorService;

    public synchronized ScheduledExecutorService getLogFileExecutorService() {
        if (logFileExecutorService == null) {
            if (this == getDefaultConfig()) {
                logFileExecutorService = Executors.newSingleThreadScheduledExecutor();
            } else {
                logFileExecutorService = getDefaultConfig().getLogFileExecutorService();
            }
        }
        return logFileExecutorService;
    }

    @Override
    public Config clone() {
        try {
            return (Config) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Config();
        }
    }

    public static Config defaultConfig;

    public static void init(Config config) {
        defaultConfig = config;
    }

    public static Config getDefaultConfig() {
        if (defaultConfig == null) {
            defaultConfig = new Config();
        }
        return defaultConfig;
    }
}
