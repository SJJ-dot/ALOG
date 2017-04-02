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
     * log输出控制
     */
    public boolean enable = true;
    /**
     * log 输出级别
     */
    public int lev = Config.ALL;
    /**
     * 输出所有级别大于 {@link #lev} 的log
     */
    public boolean multiple = true;
    /**
     * 日志存放目录
     */
    public File dir;

    File getDir() {
        if (dir == null) {
            dir = Environment.getExternalStorageDirectory();
        }
        return dir;
    }

    /**
     * 是否保存日志
     */
    public boolean hold = false;
    /**
     * 保存日志级别
     */
    public int holdLev = ALL;
    /**
     * 记录所有级别大于 {@link #holdLev} 的log
     */
    public boolean holdMultiple = true;
    private static Config defaultConfig;

    public static void init(Config config) {
        defaultConfig = config;
    }

    static Config getDefaultConfig() {
        if (defaultConfig == null) {
            defaultConfig = new Config();
        }
        return defaultConfig;
    }
}
