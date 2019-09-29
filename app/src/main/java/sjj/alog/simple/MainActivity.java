package sjj.alog.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

import sjj.alog.Config;
import sjj.alog.Log;
import sjj.alog.Logger;

import static sjj.alog.Config.ERROR;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printLog();
        privateConfig();
    }

    public void printLog() {

        Config config = Config.getDefaultConfig();
        config.tag = "def global config";

        Log.i("log");
        //输出：2019-09-29 16:52:36.035 10325-10325/sjj.alog.simple I/def global config: printLog(MainActivity.java:30) log

        Log.i(1, "log");
        //输出：2019-09-29 16:52:36.035 10325-10325/sjj.alog.simple I/def global config: onCreate(MainActivity.java:21) log

    }

    public void privateConfig() {
        // 如果写入存储卡需要 WRITE_EXTERNAL_STORAGE
        Config config = new Config();
        //保存到文件 更多配置查看 Config 类
        config.hold = true;
        config.dir = new File(getExternalCacheDir(), "LogFile");
        config.holdLev = ERROR;
        //只保存指定级别日志
        config.holdMultiple = false;
        Logger logger = new Logger(config);
        logger.e("write file");
        //输出：2019-09-29 16:52:36.049 10325-10325/sjj.alog.simple E/Logger: privateConfig(MainActivity.java:48) write file
        logger.i("小于指定级别的日志不会写入文件");
        //输出：2019-09-29 16:52:36.049 10325-10325/sjj.alog.simple I/Logger: privateConfig(MainActivity.java:50) 小于指定级别的日志不会写入文件
    }

}
