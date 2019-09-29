package sjj.alog;

import android.os.Environment;
import android.text.TextUtils;

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
    public boolean enableCallMethod = true;
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
     * 控制台中输出堆栈轨迹的行数 0 不限制
     */
    public int stackTraceLineNum = 0;

    /**
     * 是否需要在控制台中打印所有的log 。开启时，如果日志长度超出最长限制，分段打印
     */
    public boolean printAllLog = false;

    /**
     * 控制台输出 tag
     */
    public String tag = "Logger";

    /**
     * 日志存放目录
     */
    public File dir;
    public String dirName;

    File getDir() {
        if (dir != null) {
            return dir;
        } else if (!TextUtils.isEmpty(dirName)) {
            dir = new File(Environment.getExternalStorageDirectory(), dirName);
        } else {
            dir = new File(Environment.getExternalStorageDirectory(), "log");
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
    public boolean deleteOldLog = false;
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
