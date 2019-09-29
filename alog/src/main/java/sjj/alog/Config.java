package sjj.alog;

import android.os.Environment;

import java.io.File;

/**
 * Created by SJJ on 2017/3/5.
 */

public class Config {
    public static final int ALL = 0;
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int WARN = 3;
    public static final int ERROR = 4;
    /**
     * 是否要输出调用方法
     */
    public boolean consolePrintMethod = true;
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

    /**
     * 日志存放目录
     */
    public File writeToFileDir;
    public String writeToFileDirName = "log";

    File getWriteToFileDir() {
        if (writeToFileDir != null) {
            return writeToFileDir;
        } else {
            writeToFileDir = new File(Environment.getExternalStorageDirectory(), writeToFileDirName);
        }
        return writeToFileDir;
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
    private static Config defaultConfig;

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
